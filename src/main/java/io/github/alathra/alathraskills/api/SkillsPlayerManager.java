package io.github.alathra.alathraskills.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jooq.Result;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.Reloadable;
import io.github.alathra.alathraskills.db.DatabaseQueries;
import io.github.alathra.alathraskills.db.schema.tables.records.PlayerSkillinfoRecord;

public class SkillsPlayerManager implements Reloadable {
	
	private final AlathraSkills plugin;
	private static HashMap<UUID, SkillsPlayer> skillPlayers = new HashMap<UUID, SkillsPlayer>();
	private BukkitTask storePlayerSkillInfoTask;
	
	public SkillsPlayerManager(AlathraSkills plugin) {
		this.plugin = plugin;
		SkillsPlayerManager.skillPlayers = new HashMap<UUID, SkillsPlayer>();
	}
	
	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnable() {
		// TODO Auto-generated method stub
		storePlayerSkillInfoTask = this.plugin.getServer().getScheduler()
				.runTaskTimerAsynchronously(plugin, new Runnable() {
					public void run() {
						saveAllPlayerInformation();
					}
				}, 0, 200L);
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		this.plugin.getServer().getScheduler()
			.cancelTask(storePlayerSkillInfoTask.getTaskId());
	}
	
	public void handlePlayerJoin(Player p) {
		
		HashMap<Integer, SkillDetails> playerSkills = new HashMap<Integer, SkillDetails>();
		HashMap<Integer, Float> playerExperienceValues = new HashMap<Integer, Float>();

		if (!p.hasPlayedBefore()) {
			// TODO Make this a single DB command that initializes all skills
			DatabaseQueries.saveSkillCategoryExperience(p, 1, 0);
			DatabaseQueries.saveSkillCategoryExperience(p, 2, 0);
			DatabaseQueries.saveSkillCategoryExperience(p, 3, 0);
		}
		//TODO Make Async
		Result<PlayerSkillinfoRecord> skillsDBReturn = DatabaseQueries.fetchPlayerSkills(p);
		
        if (skillsDBReturn != null) {
        	for (Iterator<PlayerSkillinfoRecord> iterator = skillsDBReturn.iterator(); iterator.hasNext();) {
				PlayerSkillinfoRecord playerSkillinfoRecord = iterator.next();
				playerSkills.put(playerSkillinfoRecord.getSkillid(), new SkillDetails(true, true));
			}
        }
        
        // TODO Make Async and make this a single command that uses a loop similar to above
        float dbExperienceReturnValue1 = DatabaseQueries.getSkillCategoryExperienceFloat(
        		p, 1);
        float dbExperienceReturnValue2 = DatabaseQueries.getSkillCategoryExperienceFloat(
        		p, 2);
        float dbExperienceReturnValue3 = DatabaseQueries.getSkillCategoryExperienceFloat(
        		p, 3);
        
        playerExperienceValues.put(1, dbExperienceReturnValue1);
        playerExperienceValues.put(2, dbExperienceReturnValue2);
        playerExperienceValues.put(3, dbExperienceReturnValue3);

		SkillsPlayer newPlayer = new SkillsPlayer(p, playerSkills, playerExperienceValues);
		skillPlayers.put(p.getUniqueId(), newPlayer);
	}

	public void handlePlayerLeave(Player p) {
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
		
		skillPlayers.remove(p.getUniqueId());
	}
	
	public static void setPlayerExperience(Player p, Integer skillCategory, Float experienceValue) {
		SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
		currentPlayer.setExperience(skillCategory, experienceValue);
	}
	
	public static void addPlayerSkill(Player p, Integer skill) {
		SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
		currentPlayer.addSkill(skill);
	}
	
	public static void removePlayerSkill(Player p, Integer skill) {
		SkillsPlayer currentPlayer = skillPlayers.get(p.getUniqueId());
		currentPlayer.removeSkill(skill);
	}
	
	private void saveAllPlayerInformation() {
//		skillPlayers.values().forEach((SkillsPlayer sp) -> savePlayerValues(sp));

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
	

	//TODO
	// https://github.com/Rumsfield/konquest/blob/main/api/src/main/java/com/github/rumsfield/konquest/api/manager/KonquestPlayerManager.java
	
}
