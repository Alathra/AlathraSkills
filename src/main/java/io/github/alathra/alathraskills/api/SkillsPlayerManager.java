package io.github.alathra.alathraskills.api;

import java.util.HashMap;
import java.util.Iterator;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;
import org.jooq.Result;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.Reloadable;
import io.github.alathra.alathraskills.db.DatabaseQueries;
import io.github.alathra.alathraskills.db.schema.tables.records.PlayerSkillinfoRecord;

public class SkillsPlayerManager implements Reloadable {
	
	private final AlathraSkills plugin;
	private final HashMap<UUID, SkillsPlayer> skillPlayers;
	private BukkitTask storePlayerSkillInfoTask;
	
	public SkillsPlayerManager(AlathraSkills plugin) {
		this.plugin = plugin;
		this.skillPlayers = new HashMap<UUID, SkillsPlayer>();
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
				}, 0, 6000L);
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		this.plugin.getServer().getScheduler()
			.cancelTask(storePlayerSkillInfoTask.getTaskId());
	}
	
	public void handlePlayerJoin(Player p) {
		
		HashMap<Integer, Boolean> playerSkills = new HashMap<Integer, Boolean>();
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
				playerSkills.put(playerSkillinfoRecord.getSkillid(), true);
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
		
		savePlayerValues(currentPlayer);
		
		skillPlayers.remove(p.getUniqueId());
	}
	
	private void saveAllPlayerInformation() {
		skillPlayers.values().forEach((SkillsPlayer sp) -> savePlayerValues(sp));
	}
	
	private void savePlayerValues(SkillsPlayer sp) {
		HashMap<Integer, Boolean> playerSkills = sp.getPlayerSkills();
		HashMap<Integer, Float> playerExperienceValues = sp.getPlayerExperienceValues();
		
		// TODO Make Async
		DatabaseQueries.deletePlayerSkills(sp.getPlayer().getUniqueId());
		
		// TODO Eventually make the inserts a single insert/update command
		// TODO Make Async
		playerSkills
			.entrySet()
			.stream()
			.filter(e -> e.getValue())
			.forEach(e -> DatabaseQueries.saveSkillInfo(sp.getPlayer().getUniqueId(), e.getKey()));
			
		// TODO Make Async and eventually make this a single DBQuerries command
		playerExperienceValues
			.entrySet()
			.stream()
			.forEach(e ->DatabaseQueries.saveSkillCategoryExperience(
					sp.getPlayer().getUniqueId(), e.getKey(), e.getValue()
					));
	}

	//TODO
	// https://github.com/Rumsfield/konquest/blob/main/api/src/main/java/com/github/rumsfield/konquest/api/manager/KonquestPlayerManager.java
	
}
