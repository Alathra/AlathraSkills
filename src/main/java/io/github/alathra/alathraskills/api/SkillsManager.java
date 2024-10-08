package io.github.alathra.alathraskills.api;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.Reloadable;
import io.github.alathra.alathraskills.skills.Skill;
import io.github.alathra.alathraskills.skills.SkillCategory;
import io.github.alathra.alathraskills.skills.categories.FarmingSkillCategory;
import io.github.alathra.alathraskills.skills.categories.FishingSkillCategory;
import io.github.alathra.alathraskills.skills.categories.MiningSkillCategory;
import io.github.alathra.alathraskills.skills.categories.WoodcuttingSkillCategory;
import io.github.alathra.alathraskills.skills.farming.fastharvest.*;
import io.github.alathra.alathraskills.skills.farming.greenthumb.*;
import io.github.alathra.alathraskills.skills.farming.qualitycrops.*;
import io.github.alathra.alathraskills.skills.farming.readytoeat.ReadyToEatOne;
import io.github.alathra.alathraskills.skills.farming.readytoeat.ReadyToEatTwo;
import io.github.alathra.alathraskills.skills.farming.widespread.*;
import io.github.alathra.alathraskills.skills.fishing.alongfight.*;
import io.github.alathra.alathraskills.skills.fishing.bigcatch.*;
import io.github.alathra.alathraskills.skills.fishing.highqualitycatch.*;
import io.github.alathra.alathraskills.skills.fishing.onemanstrash.OneMansTrashFour;
import io.github.alathra.alathraskills.skills.fishing.onemanstrash.OneMansTrashOne;
import io.github.alathra.alathraskills.skills.fishing.onemanstrash.OneMansTrashThree;
import io.github.alathra.alathraskills.skills.fishing.onemanstrash.OneMansTrashTwo;
import io.github.alathra.alathraskills.skills.fishing.quicktobite.*;
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

import java.util.HashMap;

public class SkillsManager implements Reloadable {

    public static final int FARMING_SKILL_ID = 1;
    public static final int MINING_SKILL_ID = 2;
    public static final int WOODCUTTING_SKILL_ID = 3;
    public static final int FISHING_SKILL_ID = 4;

    // Farming IDs
    public static final int[] QUALITY_CROP_IDS = new int[]{1218, 1216, 1214, 1211, 106, 101};
    public static final int[] FAST_HARVEST_IDS = new int[]{1219, 1217, 1215, 1212, 109, 104, 102};
    public static final int[] GREEN_THUMB_IDS = new int[]{1118, 1116, 1114, 1111, 108, 103};
    public static final int[] WIDE_SPREAD_IDS = new int[]{1119, 1117, 1115, 1112, 110, 107, 105};
    public static final int[] READY_TO_EAT_IDS = new int[]{1220, 1120, 1213, 1113};

    // Mining IDs
    public static final int[] SPELUNKER_IDS = new int[]{2213, 2113, 209, 206, 201};
    public static final int[] VEIN_BREAKER_IDS = new int[]{2218, 2216, 2214, 2211, 207, 204, 202};
    public static final int[] PROUD_PROSPECTOR_IDS = new int[]{2220, 2219, 2217, 2215, 2212, 208, 203};
    public static final int[] EASY_PICKING_IDS = new int[]{2120, 2118, 2115, 2112, 210, 205};
    public static final int[] ORE_IN_THE_ROUGH_IDS = new int[]{2119, 2117, 2116, 2114, 2111};

    // Woodcutting IDs
    public static final int SAVE_THE_TREES_ID = 301;
    public static final int[] PRECISE_CHOP_IDS = new int[]{3218, 3217, 3214, 3212, 3211, 310, 303, 302};
    public static final int[] TRIMMER_IDS = new int[]{3220, 3219, 3216, 3215, 3213, 307, 304};
    public static final int[] ONE_SWING_IDS = new int[]{3120, 3117, 3115, 3114, 3112, 309, 305};
    public static final int[] ONE_WITH_THE_FOREST_IDS = new int[]{3119, 3118, 3116, 3113, 3111, 308, 306};

    // Fishing IDs
    public static final int[] A_LONG_FIGHT_IDS = new int[]{4120, 4117, 4112, 4111, 404, 401};
    public static final int[] HIGH_QUALITY_CATCH_IDS = new int[]{4220, 4218, 4213, 4211, 408, 402};
    public static final int[] QUICK_TO_BITE_IDS = new int[]{4119, 4118, 4115, 4113, 407, 403};
    public static final int[] ONE_MANS_TRASH_IDS = new int[]{4216, 4116, 4214, 4114, 410, 405};
    public static final int[] BIG_CATCH_IDS = new int[]{4219, 4217, 4215, 4212, 409, 406};

    private final AlathraSkills plugin;

    // Id, SkillCategory
    public final HashMap<Integer, SkillCategory> skillCategories = new HashMap<>();

    // Id, Skill
    public final HashMap<Integer, Skill> skills = new HashMap<>();


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
        skillCategories.put(4, new FishingSkillCategory(4));
    }

    public void loadSkills() {
        loadWoodcuttingSkills();
        loadMiningSkills();
        loadFarmingSkills();
    }

    private void loadWoodcuttingSkills() {
        skills.put(301, new SaveTheTreesOne         (301, 5));
        skills.put(302, new PreciseChopOne          (302, 5));
        skills.put(303, new PreciseChopTwo          (303, 5));
        skills.put(304, new TrimmerOne              (304, 5));
        skills.put(305, new OneSwingOne             (305, 5));
        skills.put(306, new OneWithTheForestOne     (306, 6));
        skills.put(307, new TrimmerTwo              (307, 7));
        skills.put(308, new OneWithTheForestTwo     (308, 8));
        skills.put(309, new OneSwingTwo             (309, 9));
        skills.put(310, new PreciseChopThree        (310, 10));

        // Left branch
        skills.put(3111, new OneWithTheForestThree  (3111, 10));
        skills.put(3112, new OneSwingThree          (3112, 10));
        skills.put(3113, new OneWithTheForestFour   (3113, 10));
        skills.put(3114, new OneSwingFour           (3114, 10));
        skills.put(3115, new OneSwingFive           (3115, 10));
        skills.put(3116, new OneWithTheForestFive   (3116, 10));
        skills.put(3117, new OneSwingSix            (3117, 10));
        skills.put(3118, new OneWithTheForestSix    (3118, 10));
        skills.put(3119, new OneWithTheForestSeven  (3119, 10));
        skills.put(3120, new OneSwingSeven          (3120, 10));

        // Right branch
        skills.put(3211, new PreciseChopFour        (3211, 10));
        skills.put(3212, new PreciseChopFive        (3212, 10));
        skills.put(3213, new TrimmerThree           (3213, 10));
        skills.put(3214, new PreciseChopSix         (3214, 10));
        skills.put(3215, new TrimmerFour            (3215, 10));
        skills.put(3216, new TrimmerFive            (3216, 10));
        skills.put(3217, new PreciseChopSeven       (3217, 10));
        skills.put(3218, new PreciseChopEight       (3218, 10));
        skills.put(3219, new TrimmerSix             (3219, 10));
        skills.put(3220, new TrimmerSeven           (3220, 10));
    }

    private void loadMiningSkills() {
        skills.put(201, new SpelunkerOne            (201, 5));
        skills.put(202, new VeinBreakerOne          (202, 5));
        skills.put(203, new ProudProspectorOne      (203, 5));
        skills.put(204, new VeinBreakerTwo          (204, 5));
        skills.put(205, new EasyPickingOne          (205, 5));
        skills.put(206, new SpelunkerTwo            (206, 6));
        skills.put(207, new VeinBreakerThree        (207, 7));
        skills.put(208, new ProudProspectorTwo      (208, 8));
        skills.put(209, new SpelunkerThree          (209, 9));
        skills.put(210, new EasyPickingTwo          (210, 10));

        // Left branch
        skills.put(2111, new OreInTheRoughOne       (2111, 10));
        skills.put(2112, new EasyPickingThree       (2112, 10));
        skills.put(2113, new SpelunkerFour          (2113, 10));
        skills.put(2114, new OreInTheRoughTwo       (2114, 10));
        skills.put(2115, new EasyPickingFour        (2115, 10));
        skills.put(2116, new OreInTheRoughThree     (2116, 10));
        skills.put(2117, new OreInTheRoughFour      (2117, 10));
        skills.put(2118, new EasyPickingFive        (2118, 10));
        skills.put(2119, new OreInTheRoughFive      (2119, 10));
        skills.put(2120, new EasyPickingSix         (2120, 10));

        // Right branch
        skills.put(2211, new VeinBreakerFour        (2211, 10));
        skills.put(2212, new ProudProspectorThree   (2212, 10));
        skills.put(2213, new SpelunkerFour          (2213, 10));
        skills.put(2214, new VeinBreakerFive        (2214, 10));
        skills.put(2215, new ProudProspectorFour    (2215, 10));
        skills.put(2216, new VeinBreakerSix         (2216, 10));
        skills.put(2217, new ProudProspectorFive    (2217, 10));
        skills.put(2218, new VeinBreakerSeven       (2218, 10));
        skills.put(2219, new ProudProspectorSix     (2219, 10));
        skills.put(2220, new ProudProspectorSeven   (2220, 10));
    }

    private void loadFarmingSkills() {
        skills.put(101, new QualityCropsOne         (101, 5));
        skills.put(102, new FastHarvestOne          (102, 5));
        skills.put(103, new GreenThumbOne           (103, 5));
        skills.put(104, new FastHarvestTwo          (104, 5));
        skills.put(105, new WideSpreadOne           (105, 5));
        skills.put(106, new QualityCropsTwo         (106, 6));
        skills.put(107, new WideSpreadTwo           (107, 7));
        skills.put(108, new GreenThumbTwo           (108, 8));
        skills.put(109, new FastHarvestThree        (109, 9));
        skills.put(110, new WideSpreadThree         (110, 10));

        // Left branch
        skills.put(1111, new GreenThumbThree        (1111, 10));
        skills.put(1112, new WideSpreadFour         (1112, 10));
        skills.put(1113, new ReadyToEatOne          (1113, 10));
        skills.put(1114, new GreenThumbFour         (1114, 10));
        skills.put(1115, new WideSpreadFive         (1115, 10));
        skills.put(1116, new GreenThumbFive         (1116, 10));
        skills.put(1117, new WideSpreadSix          (1117, 10));
        skills.put(1118, new GreenThumbSix          (1118, 10));
        skills.put(1119, new WideSpreadSeven        (1119, 10));
        skills.put(1120, new ReadyToEatTwo          (1120, 10));

        // Right branch
        skills.put(1211, new QualityCropsThree      (1211, 10));
        skills.put(1212, new FastHarvestFour        (1212, 10));
        skills.put(1213, new ReadyToEatOne          (1213, 10));
        skills.put(1214, new QualityCropsFour       (1214, 10));
        skills.put(1215, new FastHarvestFive        (1215, 10));
        skills.put(1216, new QualityCropsFive       (1216, 10));
        skills.put(1217, new FastHarvestSix         (1217, 10));
        skills.put(1218, new QualityCropsSix        (1218, 10));
        skills.put(1219, new FastHarvestSeven       (1219, 10));
        skills.put(1220, new ReadyToEatTwo          (1220, 10));

    }

    private void loadFishingSkills() {
        skills.put(401, new ALongFightOne           (401, 5));
        skills.put(402, new HighQualityCatchOne     (402, 5));
        skills.put(403, new QuickToBiteOne          (403, 5));
        skills.put(404, new ALongFightTwo           (404, 5));
        skills.put(405, new OneMansTrashOne         (405, 5));
        skills.put(406, new BigCatchOne             (406, 6));
        skills.put(407, new QuickToBiteTwo          (407, 7));
        skills.put(408, new HighQualityCatchTwo     (408, 8));
        skills.put(409, new BigCatchTwo             (409, 9));
        skills.put(410, new OneMansTrashTwo         (410, 10));

        // Left branch
        skills.put(4111, new ALongFightThree        (4111, 10));
        skills.put(4112, new ALongFightFour         (4112, 10));
        skills.put(4113, new QuickToBiteThree       (4113, 10));
        skills.put(4114, new OneMansTrashThree      (4114, 10));
        skills.put(4115, new QuickToBiteFour        (4115, 10));
        skills.put(4116, new OneMansTrashFour       (4116, 10));
        skills.put(4117, new ALongFightFive         (4117, 10));
        skills.put(4118, new QuickToBiteFive        (4118, 10));
        skills.put(4119, new QuickToBiteSix         (4119, 10));
        skills.put(4120, new ALongFightSix          (4120, 10));

        // Right branch
        skills.put(4211, new HighQualityCatchThree  (4211, 10));
        skills.put(4212, new BigCatchThree          (4212, 10));
        skills.put(4213, new HighQualityCatchFour   (4213, 10));
        skills.put(4214, new OneMansTrashThree      (4214, 10));
        skills.put(4215, new BigCatchFour           (4215, 10));
        skills.put(4216, new OneMansTrashFour       (4216, 10));
        skills.put(4217, new BigCatchFive           (4217, 10));
        skills.put(4218, new HighQualityCatchFive   (4218, 10));
        skills.put(4219, new BigCatchSix            (4219, 10));
        skills.put(4220, new HighQualityCatchSix    (4220, 10));
    }

    public Skill getSkill(Integer skillID) {
        return skills.get(skillID);
    }

}
