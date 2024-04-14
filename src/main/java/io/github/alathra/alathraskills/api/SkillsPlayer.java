package io.github.alathra.alathraskills.api;

import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.bukkit.OfflinePlayer;

public class SkillsPlayer {
	
	private OfflinePlayer p;
	private HashMap<Integer, SkillDetails> playerSkills;
	private HashMap<Integer, Float> playerExperienceValues;
	
	public SkillsPlayer(OfflinePlayer p, HashMap<Integer, SkillDetails> playerSkills,
			HashMap<Integer, Float> playerExperienceValues) {
		this.p = p;
		this.playerSkills = playerSkills;
		this.playerExperienceValues = playerExperienceValues;
	}
	
	public OfflinePlayer getPlayer() {
		return p;
	}
	
	public HashMap<Integer, SkillDetails> getPlayerSkills() {
		return playerSkills;
	}

	public Stream<Entry<Integer, SkillDetails>> getPlayerActiveSkills() {
		return playerSkills
				.entrySet()
				.stream()
				.filter(e -> e.getValue().isSelected());
	}
	
	public Float getSkillCategoryExperience(Integer skillCategory) {
		return this.playerExperienceValues.get(skillCategory);
	}

	public HashMap<Integer, Float> getPlayerExperienceValues() {
		return playerExperienceValues;
	}
	
	public void setExperience(Integer skillCategory, Float experience) {
		this.playerExperienceValues.put(skillCategory, experience);
	}

	public void addSkill(Integer skill) {
		SkillDetails currSkillDetails = playerSkills.get(skill);
		if (currSkillDetails == null) {
			// Skill does not exist in context
			this.playerSkills.put(skill, new SkillDetails(false, true));
		} else {
			// Skill initially existed, override the current record
			this.playerSkills.put(skill, new SkillDetails(
					currSkillDetails.isExistingSkill(), true));
		}
	}

	public void removeSkill(Integer skill) {
		SkillDetails currSkillDetails = playerSkills.get(skill);
		if (currSkillDetails != null) {
			this.playerSkills.put(skill, new SkillDetails(
					currSkillDetails.isExistingSkill(), false));
		}
	}
	
	public Stream<Entry<Integer, SkillDetails>> getSkillsToDeleteFromDB() {
		return playerSkills
			.entrySet()
			.stream()
			.filter(e -> !e.getValue().isSelected() && e.getValue().isExistingSkill());
	}
	
	public void cleanUpDeletedSkills() {
		getSkillsToDeleteFromDB().forEach(skill -> {
			playerSkills.remove(skill.getKey());
		});
	}

	public Stream<Entry<Integer, SkillDetails>> getSkillsToInsertToDB() {
		return playerSkills
			.entrySet()
			.stream()
			.filter(e -> e.getValue().isSelected() && !e.getValue().isExistingSkill());
	}
	
	public void cleanUpInsertedSkills() {
		getSkillsToInsertToDB().forEach(sd -> playerSkills.put(
				sd.getKey(), new SkillDetails(true, true)));
	}
}
