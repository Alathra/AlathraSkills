package io.github.alathra.alathraskills.api;

import java.time.Instant;
import java.util.*;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.stream.Stream;

import io.github.alathra.alathraskills.api.events.SkillsPlayerLoadedEvent;
import io.github.alathra.alathraskills.api.events.SkillsPlayerUnloadedEvent;
import io.github.alathra.alathraskills.skills.Skill;
import io.github.alathra.alathraskills.utility.Logger;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jooq.Result;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.Reloadable;
import io.github.alathra.alathraskills.db.DatabaseQueries;
import io.github.alathra.alathraskills.db.schema.tables.records.PlayerSkillinfoRecord;
import io.github.alathra.alathraskills.utility.Cfg;

public class SkillsPlayerManager implements Reloadable {
	
	private final AlathraSkills instance;
	private static HashMap<UUID, SkillsPlayer> skillPlayers = new HashMap<UUID, SkillsPlayer>();
	private BukkitTask storePlayerSkillInfoTask;
	
	public SkillsPlayerManager(AlathraSkills instance) {
		this.instance = instance;
		SkillsPlayerManager.skillPlayers = new HashMap<UUID, SkillsPlayer>();
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
		storePlayerSkillInfoTask = this.instance.getServer().getScheduler()
				.runTaskTimerAsynchronously(instance, new Runnable() {
					public void run() {
						saveAllPlayerInformation();
					}
				}, 0, 12000L);
	}

	@Override
	public void onDisable() {
		Bukkit.getOnlinePlayers().forEach((Player p) -> {
            CompletableFuture<SkillsPlayer> future = SkillsPlayerManager.handlePlayerLeave(p);
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
	
	public static CompletableFuture<SkillsPlayer> handlePlayerJoin(Player p) {
        return CompletableFuture.supplyAsync(() -> {
            HashMap<Integer, SkillDetails> playerSkills = new HashMap<Integer, SkillDetails>();
            HashMap<Integer, Float> playerExperienceValues = new HashMap<Integer, Float>();

            if (!p.hasPlayedBefore()) {
                // TODO Make this a single DB command that initializes all skills
                DatabaseQueries.saveSkillCategoryExperience(p, 1, 0);
                DatabaseQueries.saveSkillCategoryExperience(p, 2, 0);
                DatabaseQueries.saveSkillCategoryExperience(p, 3, 0);
                DatabaseQueries.setUsedSkillPoints(p, 0);
            }

            Result<PlayerSkillinfoRecord> skillsDBReturn = DatabaseQueries.fetchPlayerSkills(p);

            if (skillsDBReturn != null) {
                for (Iterator<PlayerSkillinfoRecord> iterator = skillsDBReturn.iterator(); iterator.hasNext();) {
                    PlayerSkillinfoRecord playerSkillinfoRecord = iterator.next();
                    playerSkills.put(playerSkillinfoRecord.getSkillid(), new SkillDetails(true, true));
                }
            }

            float[] dbExperienceReturnValues = new float[3];
            for (int i = 0; i <= 2; i++) {
                int iterate = i;
                dbExperienceReturnValues[iterate] = DatabaseQueries.getSkillCategoryExperienceFloat(p, iterate + 1);
            }

            playerExperienceValues.put(1, dbExperienceReturnValues[0]);
            playerExperienceValues.put(2, dbExperienceReturnValues[1]);
            playerExperienceValues.put(3, dbExperienceReturnValues[2]);

            int usedSkillPoints = DatabaseQueries.getUsedSkillPoints(p);

            int latestSkillUnlocked = DatabaseQueries.getLatestSkillUnlocked(p);

            Instant cooldown = DatabaseQueries.getResetCooldown(p);
            return new SkillsPlayer(p, playerSkills, playerExperienceValues, usedSkillPoints, latestSkillUnlocked, cooldown);
        });
	}

    public void registerSkillsPlayer(UUID uuid, SkillsPlayer skillsPlayer) {
        skillPlayers.put(uuid, skillsPlayer);
    }

	public static CompletableFuture<SkillsPlayer> handlePlayerLeave(Player p) {
		return CompletableFuture.supplyAsync(() -> {
            SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());

            // Delete Skill Info
            Stream<PlayerSkillDetails> skillsToDeleteInfo = Stream.of(new PlayerSkillDetails(
                currentPlayer.getPlayer(), currentPlayer.getSkillsToDeleteFromDB()));
            DatabaseQueries.deletePlayerSkillsRecordSet(skillsToDeleteInfo);
            skillPlayers.values().forEach(sp -> sp.cleanUpDeletedSkills());

            // Insert New Skill Info
            Stream<PlayerSkillDetails> skillsToInsertInfo = Stream.of(new PlayerSkillDetails(
                currentPlayer.getPlayer(), currentPlayer.getSkillsToInsertToDB()));
            DatabaseQueries.insertPlayerSkillsRecordSet(skillsToInsertInfo);
            skillPlayers.values().forEach(sp -> sp.cleanUpInsertedSkills());

            // Save Experience Info
            Stream<PlayerExperience> experienceValues = Stream.of(new PlayerExperience(
                currentPlayer.getPlayer(), currentPlayer.getPlayerExperienceValues()));
            DatabaseQueries.updatePlayerExperienceRecordSet(experienceValues);

            Integer usedSkillPoints = currentPlayer.getUsedSkillPoints();
            DatabaseQueries.setUsedSkillPoints(p, usedSkillPoints);

            DatabaseQueries.setLatestSkillUnlocked(p, currentPlayer.getLatestSkillUnlocked());

            if (currentPlayer.getCooldown() != null)
                DatabaseQueries.saveResetCooldown(p, currentPlayer.getCooldown());

            return currentPlayer;
            });
	}

    public void unregisterSkillsPlayer(UUID uuid) {
        skillPlayers.remove(uuid);
    }
	
	public static void setPlayerExperience(Player p, Integer skillCategory, Float experienceValue) {
		SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
		currentPlayer.setExperience(skillCategory, experienceValue);
	}

    public static void addPlayerExperience(Player p, Integer skillCategory, Float experienceValue) {
        SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
        float currentExp = currentPlayer.getSkillCategoryExperience(skillCategory);
        currentPlayer.setExperience(skillCategory, currentExp + experienceValue);
    }

	public static void setPlayerUsedSkillPoints(Player p, Integer usedSkillPoints) {
		SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
		currentPlayer.setUsedSkillPoints(usedSkillPoints);
	}

    public static boolean buySkill(Player p, Integer skill) {
        SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
        float totalExp = currentPlayer.getSkillCategoryExperience(1);
        totalExp += currentPlayer.getSkillCategoryExperience(2);
        totalExp += currentPlayer.getSkillCategoryExperience(3);

        // Gets remaining exp to next skill
        float remainingExp = totalExp % Float.parseFloat(Cfg.getValue("experience.perLevel").toString());

        // Calculates total skill points based on exp and exp required for points
        int skillPointsAvailable = (int) ((totalExp - remainingExp) / Float.parseFloat(Cfg.getValue("experience.perLevel").toString()));

        // Subtracts used skill points
        skillPointsAvailable -= skillPlayers.get(p.getUniqueId()).getUsedSkillPoints();

        if (skillPointsAvailable < 1)
            return false;
        addPlayerSkill(p, skill);

        Skill skillObject = AlathraSkills.getSkillsManager().getSkill(skill);
        currentPlayer.addUsedSkillPoints(skillObject.getCost());
        currentPlayer.setLatestSkillUnlocked(skill);
        currentPlayer.addOneSkillUnlocked();

        return true;
    }

    public static boolean canSkillBeUnlocked(Player p, int skillCategoryId, int skill) {
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
	
	public static void addPlayerSkill(Player p, Integer skill) {
		SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
		currentPlayer.addSkill(skill);
	}
	
	public static void removePlayerSkill(Player p, Integer skill) {
		SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
		currentPlayer.removeSkill(skill);
	}

    public static boolean playerHasSkill(Player p, Integer skill) {
        SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
        SkillDetails skillDetails = currentPlayer.getPlayerSkills().get(skill);
        if (skillDetails == null)
            return false;
        return currentPlayer.getPlayerSkills().get(skill).isSelected();
    }

    public static boolean playerHasMaxSkills(Player p) {
        SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
        return currentPlayer.getTotalSkillsUnlocked() >= Cfg.get().getInt("skills.maximumSkills");
    }
	
	private void saveAllPlayerInformation() {
		// Delete Skill Info
		Stream<PlayerSkillDetails> skillsToDeleteInfo = skillPlayers
			.values()
			.stream()
			.map((SkillsPlayer sp) -> new PlayerSkillDetails(
					sp.getPlayer(), sp.getSkillsToDeleteFromDB()));
		DatabaseQueries.deletePlayerSkillsRecordSet(skillsToDeleteInfo);
		skillPlayers.values().forEach(sp -> sp.cleanUpDeletedSkills());
		
		// Insert New Skill Info
		Stream<PlayerSkillDetails> skillsToInsertInfo = skillPlayers
			.values()
			.stream()
			.map((SkillsPlayer sp) -> new PlayerSkillDetails(
					sp.getPlayer(), sp.getSkillsToInsertToDB()));
		DatabaseQueries.insertPlayerSkillsRecordSet(skillsToInsertInfo);
		skillPlayers.values().forEach(sp -> sp.cleanUpInsertedSkills());

		// Save Experience Info
		Stream<PlayerExperience> experienceValues = skillPlayers
			.values()
			.stream()
			.map((SkillsPlayer sp) -> new PlayerExperience(
					sp.getPlayer(), sp.getPlayerExperienceValues()));
		DatabaseQueries.updatePlayerExperienceRecordSet(experienceValues);

        Map<UUID, Integer> latestSkillsUnlocked = new HashMap<>();
        Map<UUID, Instant> playerCooldowns = new HashMap<>();

        skillPlayers
            .values()
            .forEach(sp ->  {
                latestSkillsUnlocked.put(sp.getPlayer().getUniqueId(), sp.getLatestSkillUnlocked());
                playerCooldowns.put(sp.getPlayer().getUniqueId(), sp.getCooldown());
            });

        latestSkillsUnlocked.keySet().forEach(uuid ->
            Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
                if (latestSkillsUnlocked.get(uuid) != null) DatabaseQueries.setLatestSkillUnlocked(uuid, latestSkillsUnlocked.get(uuid));
                }));

        playerCooldowns.keySet().forEach(uuid ->
            Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
                if (playerCooldowns.get(uuid) != null) DatabaseQueries.saveResetCooldown(uuid, playerCooldowns.get(uuid));
            }));
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
	
	private static SkillsPlayer fetchCurrPlayer(OfflinePlayer p) {
		UUID playerId = p.getUniqueId();
		if (playerId != null) {
			SkillsPlayer currPlayer = skillPlayers.get(playerId);
			if (currPlayer != null) {
				return currPlayer;
			} else {
				throw new Error("Player is not in memory");
			}						
		} else {
			throw new Error("Player doesn't have a valid ID");
		}
		
	}
	
	public static void setPlayerExperience(OfflinePlayer p,
			Integer skillCategory, Float experience) {
		SkillsPlayer currPlayer = fetchCurrPlayer(p);
		currPlayer.setExperience(skillCategory, experience);
	}
	
	public static void addPlayerSkill(OfflinePlayer p,
			Integer skill) {
		SkillsPlayer currPlayer = fetchCurrPlayer(p);
		currPlayer.addSkill(skill);
	}
	
	public static void deletePlayerSkill(OfflinePlayer p,
			Integer skill) {
		SkillsPlayer currPlayer = fetchCurrPlayer(p);
		currPlayer.removeSkill(skill);
	}
	
	public static Stream<Entry<Integer, SkillDetails>> getAllSkills(
			OfflinePlayer p) {
		SkillsPlayer currPlayer = fetchCurrPlayer(p);
		return currPlayer.getPlayerActiveSkills();
	}
	
	public static float getSkillCategoryExperience(
			OfflinePlayer p, Integer skillCategory) {
		SkillsPlayer currPlayer = fetchCurrPlayer(p);
		return currPlayer.getSkillCategoryExperience(skillCategory);
	}

    public static float getTotalExperience(OfflinePlayer p) {
        float farmingExp = getSkillCategoryExperience(p, 1);
        float miningExp = getSkillCategoryExperience(p, 2);
        float woodcuttingExp = getSkillCategoryExperience(p, 3);

        return farmingExp + miningExp + woodcuttingExp;
    }
	
	public static Integer getUsedSkillPoints(OfflinePlayer p) {
		SkillsPlayer currPlayer = fetchCurrPlayer(p);
		return currPlayer.getUsedSkillPoints();
	}
	
    public final HashMap<UUID, SkillsPlayer> getSkillsPlayers() {
        return skillPlayers;
    }

    public static SkillsPlayer getSkillsPlayer(UUID uuid){
        return skillPlayers.get(uuid);
    }

    public static SkillsPlayer getSkillsPlayer(Player p) {
        return getSkillsPlayer(p.getUniqueId());
    }

}
