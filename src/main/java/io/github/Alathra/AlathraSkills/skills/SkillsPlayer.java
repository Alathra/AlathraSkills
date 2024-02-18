package io.github.Alathra.AlathraSkills.skills;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import io.github.Alathra.AlathraSkills.AlathraSkills;
import io.github.Alathra.AlathraSkills.api.SkillsManager;

public class SkillsPlayer {

	// Bukkit
	private OfflinePlayer offlinePlayer;

	// Internal
	private SkillsManager skillsManager;
	private HashMap<Integer, Float> expMap = new HashMap<>();

	public SkillsPlayer(UUID playerUUID) {
		skillsManager = AlathraSkills.getSkillsManager();
		setOfflinePlayer(Bukkit.getOfflinePlayer(playerUUID));

		// Defaulting skill category exp
		for (SkillCategory category : skillsManager.skillCategories.values()) {
			expMap.put(category.getId(), 0.0f);
		}
	}

	public void setExp(int categoryID, float amount) {
		expMap.put(categoryID, amount);
	}

	public float getExp(int categoryID) {
		return expMap.get(categoryID);
	}

	public void addExp(int categoryID, float amount) {
		setExp(categoryID, getExp(categoryID) + amount);
	}

	public void removeExp(int categoryID, float amount) {
		setExp(categoryID, getExp(categoryID) - amount);
	}

	public void clearExp(int categoryID) {
		setExp(categoryID, 0.0f);
	}

	public void clearAllExp() {
		for (SkillCategory category : skillsManager.skillCategories.values()) {
			expMap.put(category.getId(), 0.0f);
		}
	}

	public float getTotalExp() {
		float total = 0;
		for (float exp : expMap.values()) {
			total += exp;
		}
		return total;
	}

	public OfflinePlayer getOfflinePlayer() {
		return offlinePlayer;
	}

	public void setOfflinePlayer(OfflinePlayer offlinePlayer) {
		this.offlinePlayer = offlinePlayer;
	}

}
