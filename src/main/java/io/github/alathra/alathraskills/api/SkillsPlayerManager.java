package io.github.alathra.alathraskills.api;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.Reloadable;
import io.github.alathra.alathraskills.api.events.SkillsPlayerLoadedEvent;
import io.github.alathra.alathraskills.api.events.SkillsPlayerUnloadedEvent;
import io.github.alathra.alathraskills.db.DatabaseQueries;
import io.github.alathra.alathraskills.db.schema.Tables;
import io.github.alathra.alathraskills.db.schema.tables.records.PlayerSkillinfoRecord;
import io.github.alathra.alathraskills.skills.Skill;
import io.github.alathra.alathraskills.utility.Cfg;
import io.github.alathra.alathraskills.utility.Logger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jetbrains.annotations.Nullable;
import org.jooq.Record1;
import org.jooq.Record2;
import org.jooq.Record5;
import org.jooq.Result;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

public class SkillsPlayerManager implements Reloadable {
    private static HashMap<UUID, SkillsPlayer> skillPlayers = new HashMap<>();
    private final AlathraSkills instance;
    private BukkitTask storePlayerSkillInfoTask;

    public SkillsPlayerManager(AlathraSkills instance) {
        this.instance = instance;
        SkillsPlayerManager.skillPlayers = new HashMap<>();
    }

    public CompletableFuture<SkillsPlayer> handlePlayerJoin(Player p) {
        return CompletableFuture.supplyAsync(() -> {
            HashMap<Integer, SkillDetails> playerSkills = new HashMap<>();
            HashMap<Integer, Float> playerExperienceValues = new HashMap<>();

            // TODO: test if this needs to stay or not.
//            if (!p.hasPlayedBefore()) {
//                DatabaseQueries.saveAllSkillCategoryExperience(p, 0.f, 0.f, 0.f);
//                DatabaseQueries.savePlayerData(p, 0, 0, 0.f, 0, Instant.now());
//            }

            Result<PlayerSkillinfoRecord> skillsDBReturn = DatabaseQueries.fetchPlayerSkills(p);
            if (skillsDBReturn != null) {
                for (PlayerSkillinfoRecord playerSkillinfoRecord : skillsDBReturn) {
                    playerSkills.put(playerSkillinfoRecord.getSkillid(), new SkillDetails(true, true));
                }
            }

            Result<Record5<Integer, Integer, Double, Integer, LocalDateTime>> playerDataRecord = DatabaseQueries.fetchPlayerData(p);

            int totalSkillpoints = 0;
            int usedSkillpoints = 0;
            float nextSkillpointProgress = 0.f;
            int latestSkillUnlocked = 0;
            Instant cooldown = null;
            for (Record5<Integer, Integer, Double, Integer, LocalDateTime> record : playerDataRecord) {
                totalSkillpoints = record.getValue(Tables.PLAYER_PLAYERDATA.TOTAL_SKILLPOINTS);
                usedSkillpoints = record.getValue(Tables.PLAYER_PLAYERDATA.USED_SKILLPOINTS);

                nextSkillpointProgress = record.getValue(Tables.PLAYER_PLAYERDATA.EXP_PROGRESS).floatValue();

                latestSkillUnlocked = record.getValue(Tables.PLAYER_PLAYERDATA.LATEST_UNLOCKED_SKILL);

                cooldown = record.getValue(Tables.PLAYER_PLAYERDATA.COOLDOWN).toInstant(ZoneOffset.UTC);
            }

            if (playerDataRecord.isEmpty()) {
                usedSkillpoints = 0;
                latestSkillUnlocked = 0;
                cooldown = null;
            }

            List<Integer> disabledSkills = new ArrayList<>();

            Result<Record1<Integer>> disabledSkillsResult = DatabaseQueries.fetchDisabledSkills(p);
            for (Record1<Integer> record : disabledSkillsResult) {
                disabledSkills.add(record.getValue(Tables.PLAYER_DISABLED_SKILLS.SKILLID));
            }

            // Running this query last so that we can overwrite skillpoints later on, without them being overwritten again.
            Result<Record2<Integer, Double>> experienceResult = DatabaseQueries.fetchAllSkillCategoryExperience(p);
            for (Record2<Integer, Double> record : experienceResult) {
                playerExperienceValues.put(record.getValue(Tables.PLAYER_SKILLCATEGORYINFO.SKILLCATEGORYID), record.getValue(Tables.PLAYER_SKILLCATEGORYINFO.EXPERIENCE).floatValue());
            }

            // If player has played before but DB is empty, set all to 0. Also set skillpoints to config value.
            if (experienceResult.isEmpty()) {
                playerExperienceValues.put(1, 0.f);
                playerExperienceValues.put(2, 0.f);
                playerExperienceValues.put(3, 0.f);
                totalSkillpoints = Cfg.get().getInt("skills.startingSkillpoints");
            }

            return new SkillsPlayer(p, totalSkillpoints, usedSkillpoints, nextSkillpointProgress, latestSkillUnlocked, playerSkills, playerExperienceValues, cooldown, disabledSkills);
        });
    }

    public CompletableFuture<SkillsPlayer> handlePlayerLeave(Player p) {
        return CompletableFuture.supplyAsync(() -> {
            SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());

            // Runs queries to clean up skills in DB
            DatabaseQueries.saveFilteredPlayerSkills(p, currentPlayer.getSkillsToDeleteFromDB(), currentPlayer.getSkillsToInsertToDB());

            // Save Experience Info
            DatabaseQueries.saveAllSkillCategoryExperience(p,
                currentPlayer.getSkillCategoryExperience(1),
                currentPlayer.getSkillCategoryExperience(2),
                currentPlayer.getSkillCategoryExperience(3));



            int totalSkillpoints = currentPlayer.getTotalSkillpoints();
            int usedSkillpoints = currentPlayer.getUsedSkillpoints();

            float nextSkillpointProgress = currentPlayer.getNextSkillpointProgress();

            int latestSkillUnlocked = currentPlayer.getLatestSkillUnlocked();

            Instant cooldown = Instant.now();
            if (currentPlayer.getResetCooldown() != null)
                cooldown = currentPlayer.getResetCooldown();

            DatabaseQueries.savePlayerData(p, totalSkillpoints, usedSkillpoints, nextSkillpointProgress, latestSkillUnlocked, cooldown);

            DatabaseQueries.saveDisabledSkills(p, currentPlayer.getDisabledSkills());

            return currentPlayer;
        });
    }

    public void setPlayerExperience(Player p, Integer skillCategory, Float experienceValue) {
        SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
        currentPlayer.setExperience(skillCategory, experienceValue);
    }

    public void addPlayerExperience(Player p, Integer skillCategory, Float experienceValue) {
        SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
        float currentExp = currentPlayer.getSkillCategoryExperience(skillCategory);
        currentPlayer.setExperience(skillCategory, currentExp + experienceValue);
    }

    public void setPlayerUsedSkillPoints(Player p, Integer usedSkillPoints) {
        SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
        currentPlayer.setUsedSkillpoints(usedSkillPoints);
    }

    public boolean buySkill(Player p, Integer skill) {
        SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
        float totalExp = currentPlayer.getSkillCategoryExperience(1);
        totalExp += currentPlayer.getSkillCategoryExperience(2);
        totalExp += currentPlayer.getSkillCategoryExperience(3);

        // Gets remaining exp to next skill
        float remainingExp = totalExp % Cfg.get().getFloat("experience.perLevel");

        // Calculates total skill points based on exp and exp required for points
        int skillPointsAvailable = (int) ((totalExp - remainingExp) / Cfg.get().getFloat("experience.perLevel"));

        // Subtracts used skill points
        skillPointsAvailable -= skillPlayers.get(p.getUniqueId()).getUsedSkillpoints();

        if (skillPointsAvailable < 1)
            return false;
        addPlayerSkill(p, skill);

        Skill skillObject = AlathraSkills.getSkillsManager().getSkill(skill);
        currentPlayer.addUsedSkillpoints(skillObject.getCost());
        currentPlayer.setLatestSkillUnlocked(skill);
        currentPlayer.addOneSkillUnlocked();

        return true;
    }

    public boolean canSkillBeUnlocked(Player p, int skillCategoryId, int skill) {
        if (playerHasMaxSkills(p)) return false;

        if (skill == 101 || skill == 201 || skill == 301)
            return true;

        if (skill < 1000)
            return playerHasSkill(p, skill - 1);

        switch (skillCategoryId) {
            case 1 -> {
                if (skill == 1111)
                    return playerHasSkill(p, 110) && !playerHasSkill(p, 1211);

                if (skill == 1211)
                    return playerHasSkill(p, 110) && !playerHasSkill(p, 1111);

                if (skill > 1200)
                    return playerHasSkill(p, skill - 1) && !playerHasSkill(p, 1111);
                else
                    return playerHasSkill(p, skill - 1) && !playerHasSkill(p, 1211);

            }
            case 2 -> {
                if (skill == 2111)
                    return playerHasSkill(p, 210) && !playerHasSkill(p, 2211);

                if (skill == 2211)
                    return playerHasSkill(p, 210) && !playerHasSkill(p, 2111);

                if (skill > 2200)
                    return playerHasSkill(p, skill - 1) && !playerHasSkill(p, 2111);
                else
                    return playerHasSkill(p, skill - 1) && !playerHasSkill(p, 2211);

            }
            case 3 -> {
                if (skill == 3111)
                    return playerHasSkill(p, 310) && !playerHasSkill(p, 3211);

                if (skill == 3211)
                    return playerHasSkill(p, 310) && !playerHasSkill(p, 3111);

                if (skill > 3200)
                    return playerHasSkill(p, skill - 1) && !playerHasSkill(p, 3111);
                else
                    return playerHasSkill(p, skill - 1) && !playerHasSkill(p, 3211);

            }
            default -> {
                return false;
            }
        }
    }

    public void addPlayerSkill(Player p, Integer skill) {
        SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
        currentPlayer.addSkill(skill);
    }

    public void removePlayerSkill(Player p, Integer skill) {
        SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
        currentPlayer.removeSkill(skill);
    }

    public boolean playerHasSkill(Player p, Integer skill) {
        SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
        SkillDetails skillDetails = currentPlayer.getPlayerSkills().get(skill);
        if (skillDetails == null)
            return false;
        return currentPlayer.getPlayerSkills().get(skill).isSelected();
    }

    public boolean playerHasMaxSkills(Player p) {
        SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
        return currentPlayer.getTotalSkillsUnlocked() >= Cfg.get().getInt("skills.maximumSkills");
    }

    private SkillsPlayer fetchCurrPlayer(OfflinePlayer p) {
        UUID playerId = p.getUniqueId();
        SkillsPlayer currPlayer = skillPlayers.get(playerId);
        if (currPlayer != null) {
            return currPlayer;
        } else {
            throw new Error("Player is not in memory");
        }

    }

    public void setPlayerExperience(OfflinePlayer p,
                                           Integer skillCategory, Float experience) {
        SkillsPlayer currPlayer = fetchCurrPlayer(p);
        currPlayer.setExperience(skillCategory, experience);
    }

    public void addPlayerSkill(OfflinePlayer p,
                                      Integer skill) {
        SkillsPlayer currPlayer = fetchCurrPlayer(p);
        currPlayer.addSkill(skill);
    }

    public void deletePlayerSkill(OfflinePlayer p,
                                         Integer skill) {
        SkillsPlayer currPlayer = fetchCurrPlayer(p);
        currPlayer.removeSkill(skill);
    }

    public Stream<Entry<Integer, SkillDetails>> getAllSkills(
        OfflinePlayer p) {
        SkillsPlayer currPlayer = fetchCurrPlayer(p);
        return currPlayer.getPlayerActiveSkills();
    }

    public float getSkillCategoryExperience(
        OfflinePlayer p, Integer skillCategory) {
        SkillsPlayer currPlayer = fetchCurrPlayer(p);
        return currPlayer.getSkillCategoryExperience(skillCategory);
    }

    public float getTotalExperience(OfflinePlayer p) {
        float farmingExp = getSkillCategoryExperience(p, 1);
        float miningExp = getSkillCategoryExperience(p, 2);
        float woodcuttingExp = getSkillCategoryExperience(p, 3);

        return farmingExp + miningExp + woodcuttingExp;
    }

    public Integer getUsedSkillPoints(OfflinePlayer p) {
        SkillsPlayer currPlayer = fetchCurrPlayer(p);
        return currPlayer.getUsedSkillpoints();
    }

    private int getAvailableSkillPoints(OfflinePlayer p) {

        float totalExp = getTotalExperience(p);

        float expPerLevel = Cfg.get().getFloat("experience.perLevel");

        float remainingExp = totalExp % expPerLevel;
        if (totalExp < expPerLevel) {
            remainingExp = expPerLevel - totalExp;
        }

        int skillPointsAvailable = (int) ((totalExp - remainingExp) / expPerLevel);
        int unlockedSkills = getUsedSkillPoints(p);
        skillPointsAvailable -= unlockedSkills;

        if (skillPointsAvailable < 0) return 0;

        return skillPointsAvailable;
    }

    private int getAvailableSkillPoints(OfflinePlayer p, float expGain) {

        float totalExpAfterGain = getTotalExperience(p) + expGain;

        float expPerLevel = Cfg.get().getFloat("experience.perLevel");

        float remainingExp = totalExpAfterGain % expPerLevel;
        if (totalExpAfterGain < expPerLevel) {
            remainingExp = expPerLevel - totalExpAfterGain;
        }

        int skillPointsAvailable = (int) ((totalExpAfterGain - remainingExp) / expPerLevel);
        int unlockedSkills = getUsedSkillPoints(p);
        skillPointsAvailable -= unlockedSkills;

        if (skillPointsAvailable < 0) return 0;

        return skillPointsAvailable;
    }

    public boolean isSkillPointGained(Player p, float expGain) {
        // if available skill points is greater after exp gain
        return getAvailableSkillPoints(p, expGain) > getAvailableSkillPoints(p);
    }

    @Nullable
    public SkillsPlayer getSkillsPlayer(UUID uuid) {
        return skillPlayers.get(uuid);
    }

    @Nullable
    public SkillsPlayer getSkillsPlayer(Player p) {
        return getSkillsPlayer(p.getUniqueId());
    }

    @Override
    public void onLoad() {
        // Handle populating player data on server reload
        Bukkit.getOnlinePlayers().forEach((Player p) -> {
            CompletableFuture<SkillsPlayer> future = handlePlayerJoin(p);
            SkillsPlayer skillsPlayer = null;
            try {
                skillsPlayer = future.get();
            } catch (InterruptedException | ExecutionException e) {
                Logger.get().error("Something went wrong: " + e);
            }

            SkillsPlayerLoadedEvent firedEvent = new SkillsPlayerLoadedEvent(p.getUniqueId(), skillsPlayer);
            Bukkit.getPluginManager().callEvent(firedEvent);
        });
    }

    @Override
    public void onEnable() {
        final HashMap<UUID, SkillsPlayer> skillPlayers = SkillsPlayerManager.skillPlayers;
        storePlayerSkillInfoTask = this.instance.getServer().getScheduler().runTaskTimerAsynchronously(instance,
            () -> saveAllPlayerInformation(skillPlayers), 12000L, 12000L
        );
    }

    @Override
    public void onDisable() {
        Bukkit.getOnlinePlayers().forEach((Player p) -> {
            CompletableFuture<SkillsPlayer> future = handlePlayerLeave(p);
            SkillsPlayer skillsPlayer = null;
            try {
                skillsPlayer = future.get();
            } catch (InterruptedException | ExecutionException e) {
                Logger.get().error("Something went wrong: " + e);
            }

            SkillsPlayerUnloadedEvent firedEvent = new SkillsPlayerUnloadedEvent(p.getUniqueId(), skillsPlayer);
            Bukkit.getPluginManager().callEvent(firedEvent);
        });

        this.instance.getServer().getScheduler()
            .cancelTask(storePlayerSkillInfoTask.getTaskId());
    }

    public void registerSkillsPlayer(UUID uuid, SkillsPlayer skillsPlayer) {
        skillPlayers.put(uuid, skillsPlayer);
    }

    public void unregisterSkillsPlayer(UUID uuid) {
        skillPlayers.remove(uuid);
    }

    /**
     * This method should be run asynchronously and only when saving player all loaded players to db
     *
     * @param skillPlayers
     */
    private void saveAllPlayerInformation(final HashMap<UUID, SkillsPlayer> skillPlayers) {
        skillPlayers.keySet().forEach(uuid -> {
            SkillsPlayer currentPlayer = skillPlayers.get(uuid);

            // Runs queries to clean up skills in DB
            DatabaseQueries.saveFilteredPlayerSkills(uuid, currentPlayer.getSkillsToDeleteFromDB(), currentPlayer.getSkillsToInsertToDB());

            // Save Experience Info
            DatabaseQueries.saveAllSkillCategoryExperience(uuid,
                currentPlayer.getSkillCategoryExperience(1),
                currentPlayer.getSkillCategoryExperience(2),
                currentPlayer.getSkillCategoryExperience(3));


            int totalSkillpoints = currentPlayer.getTotalSkillpoints();
            int usedSkillPoints = currentPlayer.getUsedSkillpoints();

            float nextSkillpointProgress = currentPlayer.getNextSkillpointProgress();

            int latestSkillUnlocked = currentPlayer.getLatestSkillUnlocked();

            Instant cooldown = Instant.now();
            if (currentPlayer.getResetCooldown() != null)
                cooldown = currentPlayer.getResetCooldown();

            DatabaseQueries.savePlayerData(uuid, totalSkillpoints, usedSkillPoints, nextSkillpointProgress, latestSkillUnlocked, cooldown);

            DatabaseQueries.saveDisabledSkills(uuid, currentPlayer.getDisabledSkills());
        });
    }

    @Deprecated
    private void savePlayerValues(SkillsPlayer sp) {
        HashMap<Integer, SkillDetails> playerSkills = sp.getPlayerSkills();
        HashMap<Integer, Float> playerExperienceValues = sp.getPlayerExperienceValues();


        // TODO Make Async
        // TODO Delete set of skills from players
        DatabaseQueries.deletePlayerSkills(sp.getPlayer().getUniqueId());

        // TODO Eventually make the inserts a single insert/update command
        // TODO Make Async
        playerSkills
            .entrySet()
            .stream()
            .filter(e -> e.getValue().isSelected() && !e.getValue().isExistingSkill())
            .forEach(e -> DatabaseQueries.saveSkillInfo(sp.getPlayer().getUniqueId(), e.getKey()));

        // TODO Make Async and eventually make this a single DBQuerries command
        playerExperienceValues
            .entrySet()
            .stream()
            .forEach(e -> DatabaseQueries.saveSkillCategoryExperience(
                sp.getPlayer().getUniqueId(), e.getKey(), e.getValue()
            ));
    }

    public final HashMap<UUID, SkillsPlayer> getSkillsPlayers() {
        return skillPlayers;
    }

}
