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

    private final OfflinePlayer p;
    private int totalSkillpoints;
    private Integer usedSkillpoints;
    private int totalSkillsUnlocked;
    private int latestSkillUnlocked;
    private float nextSkillpointProgress;

    private final HashMap<Integer, SkillDetails> playerSkills;
    private final HashMap<Integer, Float> playerExperienceValues;

    private Instant resetCooldown;
    private List<Integer> disabledSkills;

    private final SkillsManager skillsManager;

    public SkillsPlayer(OfflinePlayer p,
                        int totalSkillpoints,
                        Integer usedSkillpoints,
                        float nextSkillpointProgress,
                        int latestSkillUnlocked,
                        HashMap<Integer, SkillDetails> playerSkills,
                        HashMap<Integer, Float> playerExperienceValues,
                        Instant resetCooldown,
                        List<Integer> disabledSkills) {
        this.p = p;

        this.totalSkillpoints = totalSkillpoints;
        this.usedSkillpoints = usedSkillpoints;
        this.latestSkillUnlocked = latestSkillUnlocked;

        this.playerSkills = playerSkills;
        this.playerExperienceValues = playerExperienceValues;
        this.nextSkillpointProgress = nextSkillpointProgress;

        this.resetCooldown = resetCooldown;

        this.disabledSkills = disabledSkills;

        for (SkillDetails skillDetails : playerSkills.values())
            if (skillDetails.isSelected()) this.totalSkillsUnlocked++;

        this.skillsManager = AlathraSkills.getSkillsManager();
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
        getSkillsToDeleteFromDB().forEach(skill -> playerSkills.remove(skill));
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

    public int getTotalSkillpoints() {
        return totalSkillpoints;
    }

    public void setTotalSkillpoints(int totalSkillpoints) {
        this.totalSkillpoints = totalSkillpoints;
    }

    public void addTotalSkillpoints(int addedSkillpoints) {
        this.totalSkillpoints += addedSkillpoints;
    }

    public void clearTotalSkillpoints() {
        this.totalSkillpoints = 0;
    }

    public Integer getUsedSkillpoints() {
        return usedSkillpoints;
    }

    public void setUsedSkillpoints(Integer usedSkillpoints) {
        this.usedSkillpoints = usedSkillpoints;
    }

    public void addUsedSkillpoints(Integer addedSkillpoints) {
        this.usedSkillpoints += addedSkillpoints;
    }

    public void clearUsedSkillpoints() {
        this.usedSkillpoints = 0;
    }

    public float getNextSkillpointProgress() {
        return nextSkillpointProgress;
    }

    public void setNextSkillpointProgress(float nextSkillpointProgress) {
        this.nextSkillpointProgress = nextSkillpointProgress;
    }

    public void addNextSkillpointsProgress(float addedExp) {
        this.nextSkillpointProgress += addedExp;
    }

    public void clearNextSkillpointProgress() {
        this.nextSkillpointProgress = 0.f;
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

    public Instant getResetCooldown() {
        return resetCooldown;
    }

    public void setResetCooldown(Instant resetCooldown) {
        this.resetCooldown = resetCooldown;
    }

    public boolean isOnCooldown() {
        if (this.resetCooldown == null) return false;
        return this.resetCooldown.isAfter(Instant.now());
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
        this.addUsedSkillpoints(skill.getCost() * -1);
        this.removeSkill(skill.getId());
        this.enableSkill(skill.getId());
    }

    public boolean resetProgress(int cost, double expRetained) {
        if (AlathraSkills.getVaultHook().isVaultLoaded())
            if (cost > 0 && AlathraSkills.getVaultHook().getEconomy().getBalance(this.p) < cost)
                return false;

        playerSkills.keySet().forEach(this::removeSkill);
        this.setExperience(1, (float) (this.getSkillCategoryExperience(1) * expRetained));
        this.setExperience(2, (float) (this.getSkillCategoryExperience(2) * expRetained));
        this.setExperience(3, (float) (this.getSkillCategoryExperience(3) * expRetained));
        this.setTotalSkillpoints(Math.round(this.getTotalSkillpoints() * (float) expRetained));

        this.clearUsedSkillpoints();
        this.clearLatestSkillUnlocked();
        this.clearDisabledSkills();
        this.setResetCooldown(Instant.now().plusSeconds(Cfg.get().getLong("skills.resetCooldown")));
        if (AlathraSkills.getVaultHook().isVaultLoaded() && cost > 0)
            AlathraSkills.getVaultHook().getEconomy().withdrawPlayer(this.p, cost);
        return true;
    }
}
