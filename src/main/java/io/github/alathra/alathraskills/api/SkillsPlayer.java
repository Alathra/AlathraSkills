package io.github.alathra.alathraskills.api;

import java.time.Instant;
import java.util.HashMap;
import java.util.Map.Entry;
import java.util.stream.Stream;

import org.bukkit.OfflinePlayer;

public class SkillsPlayer {
	
	private OfflinePlayer p;
	private HashMap<Integer, SkillDetails> playerSkills;
	private HashMap<Integer, Float> playerExperienceValues;
	private Integer usedSkillPoints;

    private int totalSkillsUnlocked;

    private int latestSkillUnlocked;

    private Instant cooldown;
	
	public SkillsPlayer(OfflinePlayer p, HashMap<Integer, SkillDetails> playerSkills,
			HashMap<Integer, Float> playerExperienceValues, Integer usedSkillPoints, int latestSkillUnlocked, Instant cooldown) {
		this.p = p;
		this.playerSkills = playerSkills;
		this.playerExperienceValues = playerExperienceValues;
		this.usedSkillPoints = usedSkillPoints;
        this.latestSkillUnlocked = latestSkillUnlocked;
        this.cooldown = cooldown;

        for (SkillDetails skillDetails : playerSkills.values())
            if (skillDetails.isSelected()) this.totalSkillsUnlocked++;
	}
	
	public OfflinePlayer getPlayer() {
		return p;
	}
	
	public HashMap<Integer, SkillDetails> getPlayerSkills() {
		return playerSkills;
	}

    public boolean playerHasSkill(int id) {
        return playerSkills.get(id).isSelected();
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

	public Integer getUsedSkillPoints() {
		return usedSkillPoints;
	}

	public void setUsedSkillPoints(Integer usedSkillPoints) {
		this.usedSkillPoints = usedSkillPoints;
	}

    public void addUsedSkillPoints(Integer addedPoints) {
        this.usedSkillPoints += addedPoints;
    }

    public int getTotalSkillsUnlocked() {
        return totalSkillsUnlocked;
    }

    public void setTotalSkillsUnlocked(int totalSkillsUnlocked) {
        this.totalSkillsUnlocked = totalSkillsUnlocked;
    }

    public void addOneSkillUnlocked() {
        this.totalSkillsUnlocked++;
    }

    public int getLatestSkillUnlocked() {
        return latestSkillUnlocked;
    }

    public void clearLatestSkillUnlocked() {
        this.latestSkillUnlocked = -1;
    }

    public void setLatestSkillUnlocked(int latestSkillUnlocked) {
        this.latestSkillUnlocked = latestSkillUnlocked;
    }

    public Instant getCooldown() {
        return cooldown;
    }

    public void setCooldown(Instant cooldown) {
        this.cooldown = cooldown;
    }

    public boolean isOnCooldown() {
        return this.cooldown.isAfter(Instant.now());
    }
}
