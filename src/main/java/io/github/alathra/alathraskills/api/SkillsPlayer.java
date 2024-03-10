package io.github.alathra.alathraskills.api;

import java.util.HashMap;

import org.bukkit.OfflinePlayer;

public class SkillsPlayer {
	
	private OfflinePlayer p;
	private HashMap<Integer, Boolean> playerSkills;
	private HashMap<Integer, Float> playerExperienceValues;
	
	public SkillsPlayer(OfflinePlayer p, HashMap<Integer, Boolean> playerSkills,
			HashMap<Integer, Float> playerExperienceValues) {
		this.p = p;
		this.playerSkills = playerSkills;
		this.playerExperienceValues = playerExperienceValues;
	}
	
	public OfflinePlayer getPlayer() {
		return p;
	}
	
	public HashMap<Integer, Boolean> getPlayerSkills() {
		return playerSkills;
	}

	public HashMap<Integer, Float> getPlayerExperienceValues() {
		return playerExperienceValues;
	}

}
