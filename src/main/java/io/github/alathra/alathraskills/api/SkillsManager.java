package io.github.alathra.alathraskills.api;

import java.util.HashMap;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.Reloadable;
import io.github.alathra.alathraskills.skills.Skill;
import io.github.alathra.alathraskills.skills.SkillCategory;
import io.github.alathra.alathraskills.skills.categories.FarmingSkillCategory;
import io.github.alathra.alathraskills.skills.categories.MiningSkillCategory;
import io.github.alathra.alathraskills.skills.categories.WoodcuttingSkillCategory;
import io.github.alathra.alathraskills.skills.woodcutting.*;
import io.github.alathra.alathraskills.skills.woodcutting.groundskeeper.*;
import io.github.alathra.alathraskills.skills.woodcutting.oneswing.*;
import io.github.alathra.alathraskills.skills.woodcutting.precisechop.*;
import io.github.alathra.alathraskills.skills.woodcutting.trimmer.*;

public class SkillsManager implements Reloadable {
	
	public static int FARMING_SKILL_ID = 1;
	public static int MINING_SKILL_ID = 2;
	public static int WOODCUTTING_SKILL_ID = 3;
	
	private final AlathraSkills plugin;
	
	// Id, SkillCategory
	public HashMap<Integer, SkillCategory> skillCategories = new HashMap<>();

    // Id, Skill
    public HashMap<Integer, Skill> woodcuttingSkills = new HashMap<>();
	
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
        woodcuttingSkills.put(306, new GroundskeeperOneSkill(306));
        woodcuttingSkills.put(307, new TrimmerTwoSkill(307));
        woodcuttingSkills.put(308, new GroundskeeperTwoSkill(308));
        woodcuttingSkills.put(309, new OneSwingTwoSkill(309));
        woodcuttingSkills.put(310, new PreciseChopThreeSkill(310));

        // Left branch
        woodcuttingSkills.put(3111, new GroundskeeperThreeSkill(3111));
        woodcuttingSkills.put(3112, new OneSwingThreeSkill(3112));
        woodcuttingSkills.put(3113, new GroundskeeperFourSkill(3113));
        woodcuttingSkills.put(3114, new OneSwingFourSkill(3114));
        woodcuttingSkills.put(3115, new OneSwingFiveSkill(3115));
        woodcuttingSkills.put(3116, new GroundskeeperFiveSkill(3116));
        woodcuttingSkills.put(3117, new OneSwingSixSkill(3117));
        woodcuttingSkills.put(3118, new GroundskeeperSixSkill(3118));
        woodcuttingSkills.put(3119, new GroundskeeperSevenSkill(3119));
        woodcuttingSkills.put(3120, new OneSwingSevenSkill(3120));

        // Right branch
        woodcuttingSkills.put(3211, new PreciseChopFourSkill(3211));
        woodcuttingSkills.put(3212, new PreciseChopFiveSkill(3212));
        woodcuttingSkills.put(3213, new TrimmerThreeSkill(3213));
        woodcuttingSkills.put(3214, new PreciseChopSixSkill(3214));
        woodcuttingSkills.put(3215, new TrimmerFourSkill(3215));
        woodcuttingSkills.put(3216, new TrimmerFiveSkill(3216));
        woodcuttingSkills.put(3217, new PreciseChopSevenSkill(3217));
        woodcuttingSkills.put(3218, new PreciseChopEightSkill(3218));
        woodcuttingSkills.put(3219, new TrimmerSixSkill(3219));
        woodcuttingSkills.put(3220, new TrimmerSevenSkill(3220));
    }

}
