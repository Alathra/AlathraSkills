package io.github.alathra.alathraskills.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.Reloadable;
import io.github.alathra.alathraskills.skills.Skill;
import io.github.alathra.alathraskills.skills.SkillCategory;
import io.github.alathra.alathraskills.skills.categories.FarmingSkillCategory;
import io.github.alathra.alathraskills.skills.categories.MiningSkillCategory;
import io.github.alathra.alathraskills.skills.categories.WoodcuttingSkillCategory;
import io.github.alathra.alathraskills.skills.woodcutting.*;
import org.bukkit.entity.Player;

public class SkillsManager implements Reloadable {
	
	public static int FARMING_SKILL_ID = 1;
	public static int MINING_SKILL_ID = 2;
	public static int WOODCUTTING_SKILL_ID = 3;
	
	private final AlathraSkills plugin;
	
	// Id, SkillCategory
	public HashMap<Integer, SkillCategory> skillCategories = new HashMap<>();

    // Id, Skill
    public HashMap<Integer, Skill> woodcuttingSkills = new HashMap<>();

    // List of UUIDs that have One Swing ready
    private List<UUID> activeOneSwing = new ArrayList<>();

    // List of UUIDs that have One Swing running
    private List<UUID> runningOneSwing = new ArrayList<>();
	
	public SkillsManager(AlathraSkills plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnable() {
		loadSkillCategories();
        loadSkills();
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}
	
	public void loadSkillCategories() {
		skillCategories.put(1, new FarmingSkillCategory(1));
		skillCategories.put(2, new MiningSkillCategory(2));
		skillCategories.put(3, new WoodcuttingSkillCategory(3));
	}

    public void loadSkills() {
        loadWoodcuttingSkills();
    }

    public void loadWoodcuttingSkills() {
        woodcuttingSkills.put(301, new SaveTheTreesSkill(301));
        woodcuttingSkills.put(302, new PreciseChopOneSkill(302));
        woodcuttingSkills.put(303, new PreciseChopTwoSkill(302));
        woodcuttingSkills.put(304, new TrimmerOneSkill(304));
        woodcuttingSkills.put(305, new OneSwingOneSkill(305));
    }

    public void setOneSwingActive(UUID uuid) {
        activeOneSwing.add(uuid);
    }

    public void setOneSwingActive(Player player) {
        setOneSwingActive(player.getUniqueId());
    }

    public boolean oneSwingActive(UUID uuid) {
        return activeOneSwing.contains(uuid);
    }

    public boolean oneSwingActive(Player player) {
        return oneSwingActive(player.getUniqueId());
    }

    public void setOneSwingNotActive(UUID uuid) {
        activeOneSwing.remove(uuid);
    }

    public void setOneSwingNotActive(Player player) {
        setOneSwingNotActive(player.getUniqueId());
    }

    public void setOneSwingRunning(UUID uuid) {
        runningOneSwing.add(uuid);
    }

    public void setOneSwingRunning(Player player) {
        setOneSwingRunning(player.getUniqueId());
    }

    public boolean oneSwingRunning(UUID uuid) {
        return runningOneSwing.contains(uuid);
    }

    public boolean oneSwingRunning(Player player) {
        return oneSwingRunning(player.getUniqueId());
    }

    public void setOneSwingNotRunning(UUID uuid) {
        runningOneSwing.remove(uuid);
    }

    public void setOneSwingNotRunning(Player player) {
        setOneSwingNotRunning(player.getUniqueId());
    }

}
