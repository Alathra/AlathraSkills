package io.github.alathra.alathraskills.api;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.skills.Skill;
import io.github.alathra.alathraskills.utility.Cfg;
import org.bukkit.OfflinePlayer;

import java.time.Instant;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;
import java.util.stream.Stream;

public class SkillsPlayer {

    private OfflinePlayer p;
    private HashMap<Integer, SkillDetails> playerSkills;
    private HashMap<Integer, Float> playerExperienceValues;
    private Integer usedSkillPoints;

    private int totalSkillsUnlocked;

    private int latestSkillUnlocked;

    private Instant cooldown;
    private List<Integer> disabledSkills;

    private SkillsManager skillsManager;

    public SkillsPlayer(OfflinePlayer p,
                        HashMap<Integer, SkillDetails> playerSkills,
                        HashMap<Integer, Float> playerExperienceValues,
                        Integer usedSkillPoints,
                        int latestSkillUnlocked,
                        Instant cooldown,
                        List<Integer> disabledSkills) {
        this.p = p;
        this.playerSkills = playerSkills;
        this.playerExperienceValues = playerExperienceValues;
        this.usedSkillPoints = usedSkillPoints;
        this.latestSkillUnlocked = latestSkillUnlocked;
        this.cooldown = cooldown;
        this.skillsManager = AlathraSkills.getSkillsManager();
        this.disabledSkills = disabledSkills;

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
        if (playerSkills.get(id) == null) return false;
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

    public List<Integer> getSkillsToDeleteFromDB() {
        return playerSkills
            .keySet()
            .stream()
            .filter(key -> !playerSkills.get(key).isSelected())
            .toList();
    }

    @Deprecated
    public void cleanUpDeletedSkills() {
        getSkillsToDeleteFromDB().forEach(skill -> {
            playerSkills.remove(skill);
        });
    }

    public List<Integer> getSkillsToInsertToDB() {
        return playerSkills
            .keySet()
            .stream()
            .filter(key -> playerSkills.get(key).isSelected())
            .toList();
    }

    @Deprecated
    public void cleanUpInsertedSkills() {
        getSkillsToInsertToDB().forEach(sd -> playerSkills.put(
            sd, new SkillDetails(true, true)));
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

    public void clearUsedSkillPoints() {
        this.usedSkillPoints = 0;
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

    public void setLatestSkillUnlocked(int latestSkillUnlocked) {
        this.latestSkillUnlocked = latestSkillUnlocked;
    }

    public void clearLatestSkillUnlocked() {
        this.latestSkillUnlocked = -1;
    }

    public Instant getCooldown() {
        return cooldown;
    }

    public void setCooldown(Instant cooldown) {
        this.cooldown = cooldown;
    }

    public boolean isOnCooldown() {
        if (this.cooldown == null) return false;
        return this.cooldown.isAfter(Instant.now());
    }

    public List<Integer> getDisabledSkills() {
        return disabledSkills;
    }

    public void setDisabledSkills(List<Integer> disabledSkills) {
        this.disabledSkills = disabledSkills;
    }

    public void disableSkill(Integer id) {
        disabledSkills.add(id);
    }

    public void enableSkill(Integer id) {
        disabledSkills.remove(id);
    }

    public boolean isSkillEnabled(Integer id) {
        return !disabledSkills.contains(id);
    }

    public void clearDisabledSkills() {
        disabledSkills.clear();
    }

    public void refundLatestSkill() {
        Skill skill = skillsManager.getSkill(getLatestSkillUnlocked());
        this.clearLatestSkillUnlocked();
        this.setTotalSkillsUnlocked(this.getTotalSkillsUnlocked() - 1);
        this.addUsedSkillPoints(skill.getCost() * -1);
        this.removeSkill(skill.getId());
        this.enableSkill(skill.getId());
    }

    public boolean resetProgress(int cost, float expRetained) {
        if (AlathraSkills.getVaultHook().isVaultLoaded())
            if (cost > 0 && AlathraSkills.getVaultHook().getEconomy().getBalance(this.p) < cost)
                return false;

        playerSkills.keySet().forEach(this::removeSkill);
        this.setExperience(1, this.getSkillCategoryExperience(1) * expRetained);
        this.setExperience(2, this.getSkillCategoryExperience(2) * expRetained);
        this.setExperience(3, this.getSkillCategoryExperience(3) * expRetained);
        this.clearLatestSkillUnlocked();
        this.clearUsedSkillPoints();
        this.clearDisabledSkills();
        this.setCooldown(Instant.now().plusSeconds(Cfg.get().getLong("skills.resetCooldown")));
        if (AlathraSkills.getVaultHook().isVaultLoaded() && cost > 0)
            AlathraSkills.getVaultHook().getEconomy().withdrawPlayer(this.p, cost);
        return true;
    }
}
