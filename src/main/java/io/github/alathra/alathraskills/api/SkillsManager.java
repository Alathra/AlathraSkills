package io.github.alathra.alathraskills.api;

import java.util.HashMap;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.Reloadable;
import io.github.alathra.alathraskills.skills.Skill;
import io.github.alathra.alathraskills.skills.SkillCategory;
import io.github.alathra.alathraskills.skills.categories.FarmingSkillCategory;
import io.github.alathra.alathraskills.skills.categories.MiningSkillCategory;
import io.github.alathra.alathraskills.skills.categories.WoodcuttingSkillCategory;
import io.github.alathra.alathraskills.skills.farming.fastharvest.*;
import io.github.alathra.alathraskills.skills.farming.greenthumb.*;
import io.github.alathra.alathraskills.skills.farming.qualitycrops.*;
import io.github.alathra.alathraskills.skills.farming.readytoeat.ReadyToEatOne;
import io.github.alathra.alathraskills.skills.farming.readytoeat.ReadyToEatTwo;
import io.github.alathra.alathraskills.skills.farming.util.FastHarvest;
import io.github.alathra.alathraskills.skills.farming.widespread.*;
import io.github.alathra.alathraskills.skills.woodcutting.*;
import io.github.alathra.alathraskills.skills.woodcutting.groundskeeper.*;
import io.github.alathra.alathraskills.skills.woodcutting.oneswing.*;
import io.github.alathra.alathraskills.skills.woodcutting.precisechop.PreciseChopOneSkill;
import io.github.alathra.alathraskills.skills.woodcutting.precisechop.PreciseChopThreeSkill;
import io.github.alathra.alathraskills.skills.woodcutting.precisechop.PreciseChopTwoSkill;
import io.github.alathra.alathraskills.skills.woodcutting.trimmer.TrimmerOneSkill;
import io.github.alathra.alathraskills.skills.woodcutting.trimmer.TrimmerTwoSkill;
import io.github.alathra.alathraskills.skills.farming.widespread.*;
import io.github.alathra.alathraskills.skills.woodcutting.groundskeeper.*;
import io.github.alathra.alathraskills.skills.woodcutting.oneswing.*;
import io.github.alathra.alathraskills.skills.woodcutting.precisechop.PreciseChopOne;
import io.github.alathra.alathraskills.skills.woodcutting.precisechop.PreciseChopThree;
import io.github.alathra.alathraskills.skills.woodcutting.precisechop.PreciseChopTwo;
import io.github.alathra.alathraskills.skills.woodcutting.trimmer.TrimmerOne;
import io.github.alathra.alathraskills.skills.woodcutting.trimmer.TrimmerTwo;

public class SkillsManager implements Reloadable {
	
	public static int FARMING_SKILL_ID = 1;
	public static int MINING_SKILL_ID = 2;
	public static int WOODCUTTING_SKILL_ID = 3;
	
	private final AlathraSkills plugin;
	
	// Id, SkillCategory
	public HashMap<Integer, SkillCategory> skillCategories = new HashMap<>();

    // Id, Skill
    public HashMap<Integer, Skill> woodcuttingSkills = new HashMap<>();
    public HashMap<Integer, Skill> farmingSkills = new HashMap<>();
    public HashMap<Integer, Skill> miningSkills = new HashMap<>();


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
        loadFarmingSkills();
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

    }

    public void loadFarmingSkills() {
        farmingSkills.put(101, new QualityCropsOne(101));
        farmingSkills.put(102, new FastHarvestOne(102));
        farmingSkills.put(103, new GreenThumbOne(103));
        farmingSkills.put(104, new FastHarvestTwo(104));
        farmingSkills.put(105, new WideSpreadOne(105));
        farmingSkills.put(106, new QualityCropsTwo(106));
        farmingSkills.put(107, new WideSpreadTwo(107));
        farmingSkills.put(108, new GreenThumbTwo(108));
        farmingSkills.put(109, new FastHarvestThree(109));
        farmingSkills.put(110, new WideSpreadThree(110));

        // Left branch
        farmingSkills.put(1111, new GreenThumbThree(1111));
        farmingSkills.put(1112, new WideSpreadFour(1112));
        farmingSkills.put(1113, new ReadyToEatOne(1113));
        farmingSkills.put(1114, new GreenThumbFour(1114));
        farmingSkills.put(1115, new WideSpreadFive(1115));
        farmingSkills.put(1116, new GreenThumbFive(1116));
        farmingSkills.put(1117, new WideSpreadSix(1117));
        farmingSkills.put(1118, new GreenThumbSix(1118));
        farmingSkills.put(1119, new WideSpreadSeven(1119));
        farmingSkills.put(1120, new ReadyToEatTwo(1120));

        // Right branch
        farmingSkills.put(1211, new QualityCropsThree(1211));
        farmingSkills.put(1212, new FastHarvestFour(1212));
        farmingSkills.put(1213, new ReadyToEatOne(1213));
        farmingSkills.put(1214, new QualityCropsFour(1214));
        farmingSkills.put(1215, new FastHarvestFive(1215));
        farmingSkills.put(1216, new QualityCropsFive(1216));
        farmingSkills.put(1217, new FastHarvestSix(1217));
        farmingSkills.put(1218, new QualityCropsSix(1218));
        farmingSkills.put(1219, new FastHarvestSeven(1219));
        farmingSkills.put(1220, new ReadyToEatTwo(1220));

    }
	
}
