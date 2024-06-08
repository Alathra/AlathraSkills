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
import io.github.alathra.alathraskills.skills.farming.widespread.*;
import io.github.alathra.alathraskills.skills.mining.easypicking.*;
import io.github.alathra.alathraskills.skills.mining.oreintherough.*;
import io.github.alathra.alathraskills.skills.mining.proudprospector.*;
import io.github.alathra.alathraskills.skills.mining.spelunker.SpelunkerFour;
import io.github.alathra.alathraskills.skills.mining.spelunker.SpelunkerOne;
import io.github.alathra.alathraskills.skills.mining.spelunker.SpelunkerThree;
import io.github.alathra.alathraskills.skills.mining.spelunker.SpelunkerTwo;
import io.github.alathra.alathraskills.skills.mining.veinbreaker.*;
import io.github.alathra.alathraskills.skills.woodcutting.oneswing.*;
import io.github.alathra.alathraskills.skills.woodcutting.onewiththeforest.*;
import io.github.alathra.alathraskills.skills.woodcutting.precisechop.*;
import io.github.alathra.alathraskills.skills.woodcutting.savethetrees.SaveTheTreesOne;
import io.github.alathra.alathraskills.skills.woodcutting.trimmer.*;

public class SkillsManager implements Reloadable {
	
	public static int FARMING_SKILL_ID = 1;
	public static int MINING_SKILL_ID = 2;
	public static int WOODCUTTING_SKILL_ID = 3;

    public static int saveTheTreesId = 301;
    public static int[] preciseChopIds = new int[]{3218, 3217, 3214, 3212, 3211, 310, 303, 302};
    public static int[] trimmerIds = new int[]{3220, 3219, 3216, 3215, 3213, 307, 304};
    public static int[] oneSwingIds = new int[]{3120, 3117, 3115, 3114, 3112, 309, 305};
    public static int[] oneWithTheForestIds = new int[]{3119, 3118, 3116, 3113, 3111, 308, 306};
	
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

	}

	@Override
	public void onEnable() {
		loadSkillCategories();
        loadSkills();
	}

	@Override
	public void onDisable() {

	}
	
	public void loadSkillCategories() {
		skillCategories.put(1, new FarmingSkillCategory(1));
		skillCategories.put(2, new MiningSkillCategory(2));
		skillCategories.put(3, new WoodcuttingSkillCategory(3));
	}

    public void loadSkills() {
        loadWoodcuttingSkills();
        loadMiningSkills();
        loadFarmingSkills();
    }

    public void loadWoodcuttingSkills() {
        woodcuttingSkills.put(301, new SaveTheTreesOne(301));
        woodcuttingSkills.put(302, new PreciseChopOne(302));
        woodcuttingSkills.put(303, new PreciseChopTwo(303));
        woodcuttingSkills.put(304, new TrimmerOne(304));
        woodcuttingSkills.put(305, new OneSwingOne(305));
        woodcuttingSkills.put(306, new OneWithTheForestOne(306));
        woodcuttingSkills.put(307, new TrimmerTwo(307));
        woodcuttingSkills.put(308, new OneWithTheForestTwo(308));
        woodcuttingSkills.put(309, new OneSwingTwo(309));
        woodcuttingSkills.put(310, new PreciseChopThree(310));

        // Left branch
        woodcuttingSkills.put(3111, new OneWithTheForestThree(3111));
        woodcuttingSkills.put(3112, new OneSwingThree(3112));
        woodcuttingSkills.put(3113, new OneWithTheForestFour(3113));
        woodcuttingSkills.put(3114, new OneSwingFour(3114));
        woodcuttingSkills.put(3115, new OneSwingFive(3115));
        woodcuttingSkills.put(3116, new OneWithTheForestFive(3116));
        woodcuttingSkills.put(3117, new OneSwingSix(3117));
        woodcuttingSkills.put(3118, new OneWithTheForestSix(3118));
        woodcuttingSkills.put(3119, new OneWithTheForestSeven(3119));
        woodcuttingSkills.put(3120, new OneSwingSeven(3120));

        // Right branch
        woodcuttingSkills.put(3211, new PreciseChopFour(3211));
        woodcuttingSkills.put(3212, new PreciseChopFive(3212));
        woodcuttingSkills.put(3213, new TrimmerThree(3213));
        woodcuttingSkills.put(3214, new PreciseChopSix(3214));
        woodcuttingSkills.put(3215, new TrimmerFour(3215));
        woodcuttingSkills.put(3216, new TrimmerFive(3216));
        woodcuttingSkills.put(3217, new PreciseChopSeven(3217));
        woodcuttingSkills.put(3218, new PreciseChopEight(3218));
        woodcuttingSkills.put(3219, new TrimmerSix(3219));
        woodcuttingSkills.put(3220, new TrimmerSeven(3220));
    }

    private void loadMiningSkills() {
        miningSkills.put(201, new SpelunkerOne(201));
        miningSkills.put(202, new VeinBreakerOne(202));
        miningSkills.put(203, new ProudProspectorOne(202));
        miningSkills.put(204, new VeinBreakerTwo(204));
        miningSkills.put(205, new EasyPickingOne(205));
        miningSkills.put(206, new SpelunkerTwo(206));
        miningSkills.put(207, new VeinBreakerThree(207));
        miningSkills.put(208, new ProudProspectorTwo(208));
        miningSkills.put(209, new SpelunkerThree(209));
        miningSkills.put(210, new EasyPickingTwo(210));

        // Left branch
        miningSkills.put(2111, new OreInTheRoughOne(2111));
        miningSkills.put(2112, new ProudProspectorThree(2112));
        miningSkills.put(2113, new SpelunkerFour(2113));
        miningSkills.put(2114, new OreInTheRoughTwo(2114));
        miningSkills.put(2115, new ProudProspectorFour(2115));
        miningSkills.put(2116, new OreInTheRoughThree(2116));
        miningSkills.put(2117, new OreInTheRoughFour(2117));
        miningSkills.put(2118, new ProudProspectorFive(2118));
        miningSkills.put(2119, new OreInTheRoughFive(2119));
        miningSkills.put(2120, new ProudProspectorSix(2120));

        // Right branch
        miningSkills.put(2211, new VeinBreakerFour(2211));
        miningSkills.put(2212, new EasyPickingThree(2212));
        miningSkills.put(2213, new SpelunkerFour(2213));
        miningSkills.put(2214, new VeinBreakerFive(2214));
        miningSkills.put(2215, new EasyPickingFour(2215));
        miningSkills.put(2216, new EasyPickingFive(2216));
        miningSkills.put(2217, new VeinBreakerSix(2217));
        miningSkills.put(2218, new VeinBreakerSeven(2218));
        miningSkills.put(2219, new EasyPickingSix(2219));
        miningSkills.put(2220, new EasyPickingSeven(2220));
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

    public Skill getSkill(Integer skillID) {
        switch (skillID) {
            case 101, 102, 103, 104, 105, 106, 107, 108, 109, 110,
                1111, 1112, 1113, 1114, 1115, 1116, 1117, 1118, 1119, 1120,
                1211, 1212, 1213, 1214, 1215, 1216, 1217, 1218, 1219, 1220 -> {
                return farmingSkills.get(skillID);
            }
            case 201, 202, 203, 204, 205, 206, 207, 208, 209, 210,
                2111, 2112, 2113, 2114, 2115, 2116, 2117, 2118, 2119, 2120,
                2211, 2212, 2213, 2214, 2215, 2216, 2217, 2218, 2219, 2220 -> {
                return miningSkills.get(skillID);
            }
            case 301, 302, 303, 304, 305, 306, 307, 308, 309, 310,
                3111, 3112, 3113, 3114, 3115, 3116, 3117, 3118, 3119, 3120,
                3211, 3212, 3213, 3214, 3215, 3216, 3217, 3218, 3219, 3220 -> {
                return woodcuttingSkills.get(skillID);
            }
            default -> {
                return null;
            }
        }
    }

}
