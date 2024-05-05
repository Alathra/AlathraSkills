package io.github.alathra.alathraskills.gui.skill;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import org.bukkit.entity.Player;

public class PopulateContent {

    static SkillsManager skillsManager = AlathraSkills.getSkillsManager();

    public static void populateContent(Gui gui, Player player, int skillCategoryId, int page) {


        switch (skillCategoryId) {
            case 1 -> {
                populateFarmingContent(gui, player, page);
            }
            case 2 -> {
                populateMiningContent(gui, player, page);
            }
            case 3 -> {
                populateWoodcuttingContent(gui, player, page);
            }
            default -> {
                return;
            }
        }
    }

    private static void populateWoodcuttingContent(Gui gui, Player player, int page) {
        switch (page) {
            case 1 -> {
                gui.setItem(1, 5, ItemBuilder.from(skillsManager.woodcuttingSkills.get(301).getIcon()).asGuiItem());
                gui.setItem(2, 5, ItemBuilder.from(skillsManager.woodcuttingSkills.get(302).getIcon()).asGuiItem());
                gui.setItem(3, 5, ItemBuilder.from(skillsManager.woodcuttingSkills.get(303).getIcon()).asGuiItem());
                gui.setItem(4, 5, ItemBuilder.from(skillsManager.woodcuttingSkills.get(304).getIcon()).asGuiItem());
                gui.setItem(5, 5, ItemBuilder.from(skillsManager.woodcuttingSkills.get(305).getIcon()).asGuiItem());
                gui.setItem(6, 5, ItemBuilder.from(skillsManager.woodcuttingSkills.get(306).getIcon()).asGuiItem());
            }
            case 2 -> {
                gui.setItem(1, 5, ItemBuilder.from(skillsManager.woodcuttingSkills.get(306).getIcon()).asGuiItem());
                gui.setItem(2, 5, ItemBuilder.from(skillsManager.woodcuttingSkills.get(307).getIcon()).asGuiItem());
                gui.setItem(3, 5, ItemBuilder.from(skillsManager.woodcuttingSkills.get(308).getIcon()).asGuiItem());
                gui.setItem(4, 5, ItemBuilder.from(skillsManager.woodcuttingSkills.get(309).getIcon()).asGuiItem());
                gui.setItem(5, 5, ItemBuilder.from(skillsManager.woodcuttingSkills.get(310).getIcon()).asGuiItem());
                gui.setItem(6, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3111).getIcon()).asGuiItem());
                gui.setItem(6, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3211).getIcon()).asGuiItem());
            }
            case 3 -> {
                gui.setItem(1, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3111).getIcon()).asGuiItem());
                gui.setItem(1, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3211).getIcon()).asGuiItem());
                gui.setItem(2, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3112).getIcon()).asGuiItem());
                gui.setItem(2, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3212).getIcon()).asGuiItem());
                gui.setItem(3, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3113).getIcon()).asGuiItem());
                gui.setItem(3, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3213).getIcon()).asGuiItem());
                gui.setItem(4, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3114).getIcon()).asGuiItem());
                gui.setItem(4, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3214).getIcon()).asGuiItem());
                gui.setItem(5, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3115).getIcon()).asGuiItem());
                gui.setItem(5, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3215).getIcon()).asGuiItem());
                gui.setItem(6, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3116).getIcon()).asGuiItem());
                gui.setItem(6, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3216).getIcon()).asGuiItem());
            }
            case 4 -> {
                gui.setItem(1, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3115).getIcon()).asGuiItem());
                gui.setItem(1, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3215).getIcon()).asGuiItem());
                gui.setItem(2, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3116).getIcon()).asGuiItem());
                gui.setItem(2, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3216).getIcon()).asGuiItem());
                gui.setItem(3, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3117).getIcon()).asGuiItem());
                gui.setItem(3, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3217).getIcon()).asGuiItem());
                gui.setItem(4, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3118).getIcon()).asGuiItem());
                gui.setItem(4, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3218).getIcon()).asGuiItem());
                gui.setItem(5, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3119).getIcon()).asGuiItem());
                gui.setItem(5, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3219).getIcon()).asGuiItem());
                gui.setItem(6, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3120).getIcon()).asGuiItem());
                gui.setItem(6, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3220).getIcon()).asGuiItem());
            }
            default -> {
                return;
            }
        }
    }

    private static void populateMiningContent(Gui gui, Player player, int page) {
        switch (page) {
            case 1 -> {
                gui.setItem(1, 5, ItemBuilder.from(skillsManager.miningSkills.get(201).getIcon()).asGuiItem());
                gui.setItem(2, 5, ItemBuilder.from(skillsManager.miningSkills.get(202).getIcon()).asGuiItem());
                gui.setItem(3, 5, ItemBuilder.from(skillsManager.miningSkills.get(203).getIcon()).asGuiItem());
                gui.setItem(4, 5, ItemBuilder.from(skillsManager.miningSkills.get(204).getIcon()).asGuiItem());
                gui.setItem(5, 5, ItemBuilder.from(skillsManager.miningSkills.get(205).getIcon()).asGuiItem());
                gui.setItem(6, 5, ItemBuilder.from(skillsManager.miningSkills.get(206).getIcon()).asGuiItem());
            }
            case 2 -> {
                gui.setItem(1, 5, ItemBuilder.from(skillsManager.miningSkills.get(206).getIcon()).asGuiItem());
                gui.setItem(2, 5, ItemBuilder.from(skillsManager.miningSkills.get(207).getIcon()).asGuiItem());
                gui.setItem(3, 5, ItemBuilder.from(skillsManager.miningSkills.get(208).getIcon()).asGuiItem());
                gui.setItem(4, 5, ItemBuilder.from(skillsManager.miningSkills.get(209).getIcon()).asGuiItem());
                gui.setItem(5, 5, ItemBuilder.from(skillsManager.miningSkills.get(210).getIcon()).asGuiItem());
                gui.setItem(6, 4, ItemBuilder.from(skillsManager.miningSkills.get(2111).getIcon()).asGuiItem());
                gui.setItem(6, 6, ItemBuilder.from(skillsManager.miningSkills.get(2211).getIcon()).asGuiItem());
            }
            case 3 -> {
                gui.setItem(1, 4, ItemBuilder.from(skillsManager.miningSkills.get(2111).getIcon()).asGuiItem());
                gui.setItem(1, 6, ItemBuilder.from(skillsManager.miningSkills.get(2211).getIcon()).asGuiItem());
                gui.setItem(2, 4, ItemBuilder.from(skillsManager.miningSkills.get(2112).getIcon()).asGuiItem());
                gui.setItem(2, 6, ItemBuilder.from(skillsManager.miningSkills.get(2212).getIcon()).asGuiItem());
                gui.setItem(3, 4, ItemBuilder.from(skillsManager.miningSkills.get(2113).getIcon()).asGuiItem());
                gui.setItem(3, 6, ItemBuilder.from(skillsManager.miningSkills.get(2213).getIcon()).asGuiItem());
                gui.setItem(4, 4, ItemBuilder.from(skillsManager.miningSkills.get(2114).getIcon()).asGuiItem());
                gui.setItem(4, 6, ItemBuilder.from(skillsManager.miningSkills.get(2214).getIcon()).asGuiItem());
                gui.setItem(5, 4, ItemBuilder.from(skillsManager.miningSkills.get(2115).getIcon()).asGuiItem());
                gui.setItem(5, 6, ItemBuilder.from(skillsManager.miningSkills.get(2215).getIcon()).asGuiItem());
                gui.setItem(6, 4, ItemBuilder.from(skillsManager.miningSkills.get(2116).getIcon()).asGuiItem());
                gui.setItem(6, 6, ItemBuilder.from(skillsManager.miningSkills.get(2216).getIcon()).asGuiItem());
            }
            case 4 -> {
                gui.setItem(1, 4, ItemBuilder.from(skillsManager.miningSkills.get(2115).getIcon()).asGuiItem());
                gui.setItem(1, 6, ItemBuilder.from(skillsManager.miningSkills.get(2215).getIcon()).asGuiItem());
                gui.setItem(2, 4, ItemBuilder.from(skillsManager.miningSkills.get(2116).getIcon()).asGuiItem());
                gui.setItem(2, 6, ItemBuilder.from(skillsManager.miningSkills.get(2216).getIcon()).asGuiItem());
                gui.setItem(3, 4, ItemBuilder.from(skillsManager.miningSkills.get(2117).getIcon()).asGuiItem());
                gui.setItem(3, 6, ItemBuilder.from(skillsManager.miningSkills.get(2217).getIcon()).asGuiItem());
                gui.setItem(4, 4, ItemBuilder.from(skillsManager.miningSkills.get(2118).getIcon()).asGuiItem());
                gui.setItem(4, 6, ItemBuilder.from(skillsManager.miningSkills.get(2218).getIcon()).asGuiItem());
                gui.setItem(5, 4, ItemBuilder.from(skillsManager.miningSkills.get(2119).getIcon()).asGuiItem());
                gui.setItem(5, 6, ItemBuilder.from(skillsManager.miningSkills.get(2219).getIcon()).asGuiItem());
                gui.setItem(6, 4, ItemBuilder.from(skillsManager.miningSkills.get(2120).getIcon()).asGuiItem());
                gui.setItem(6, 6, ItemBuilder.from(skillsManager.miningSkills.get(2220).getIcon()).asGuiItem());
            }
            default -> {
                return;
            }
        }
    }

    private static void populateFarmingContent(Gui gui, Player player, int page) {
        switch (page) {
            case 1 -> {
                gui.setItem(1, 5, ItemBuilder.from(skillsManager.farmingSkills.get(101).getIcon()).asGuiItem());
                gui.setItem(2, 5, ItemBuilder.from(skillsManager.farmingSkills.get(102).getIcon()).asGuiItem());
                gui.setItem(3, 5, ItemBuilder.from(skillsManager.farmingSkills.get(103).getIcon()).asGuiItem());
                gui.setItem(4, 5, ItemBuilder.from(skillsManager.farmingSkills.get(104).getIcon()).asGuiItem());
                gui.setItem(5, 5, ItemBuilder.from(skillsManager.farmingSkills.get(105).getIcon()).asGuiItem());
                gui.setItem(6, 5, ItemBuilder.from(skillsManager.farmingSkills.get(106).getIcon()).asGuiItem());
            }
            case 2 -> {
                gui.setItem(1, 5, ItemBuilder.from(skillsManager.farmingSkills.get(106).getIcon()).asGuiItem());
                gui.setItem(2, 5, ItemBuilder.from(skillsManager.farmingSkills.get(107).getIcon()).asGuiItem());
                gui.setItem(3, 5, ItemBuilder.from(skillsManager.farmingSkills.get(108).getIcon()).asGuiItem());
                gui.setItem(4, 5, ItemBuilder.from(skillsManager.farmingSkills.get(109).getIcon()).asGuiItem());
                gui.setItem(5, 5, ItemBuilder.from(skillsManager.farmingSkills.get(110).getIcon()).asGuiItem());
                gui.setItem(6, 4, ItemBuilder.from(skillsManager.farmingSkills.get(1111).getIcon()).asGuiItem());
                gui.setItem(6, 6, ItemBuilder.from(skillsManager.farmingSkills.get(1211).getIcon()).asGuiItem());
            }
            case 3 -> {
                gui.setItem(1, 4, ItemBuilder.from(skillsManager.farmingSkills.get(1111).getIcon()).asGuiItem());
                gui.setItem(1, 6, ItemBuilder.from(skillsManager.farmingSkills.get(1211).getIcon()).asGuiItem());
                gui.setItem(2, 4, ItemBuilder.from(skillsManager.farmingSkills.get(1112).getIcon()).asGuiItem());
                gui.setItem(2, 6, ItemBuilder.from(skillsManager.farmingSkills.get(1212).getIcon()).asGuiItem());
                gui.setItem(3, 4, ItemBuilder.from(skillsManager.farmingSkills.get(1113).getIcon()).asGuiItem());
                gui.setItem(3, 6, ItemBuilder.from(skillsManager.farmingSkills.get(1213).getIcon()).asGuiItem());
                gui.setItem(4, 4, ItemBuilder.from(skillsManager.farmingSkills.get(1114).getIcon()).asGuiItem());
                gui.setItem(4, 6, ItemBuilder.from(skillsManager.farmingSkills.get(1214).getIcon()).asGuiItem());
                gui.setItem(5, 4, ItemBuilder.from(skillsManager.farmingSkills.get(1115).getIcon()).asGuiItem());
                gui.setItem(5, 6, ItemBuilder.from(skillsManager.farmingSkills.get(1215).getIcon()).asGuiItem());
                gui.setItem(6, 4, ItemBuilder.from(skillsManager.farmingSkills.get(1116).getIcon()).asGuiItem());
                gui.setItem(6, 6, ItemBuilder.from(skillsManager.farmingSkills.get(1216).getIcon()).asGuiItem());
            }
            case 4 -> {
                gui.setItem(1, 4, ItemBuilder.from(skillsManager.farmingSkills.get(1115).getIcon()).asGuiItem());
                gui.setItem(1, 6, ItemBuilder.from(skillsManager.farmingSkills.get(1215).getIcon()).asGuiItem());
                gui.setItem(2, 4, ItemBuilder.from(skillsManager.farmingSkills.get(1116).getIcon()).asGuiItem());
                gui.setItem(2, 6, ItemBuilder.from(skillsManager.farmingSkills.get(1216).getIcon()).asGuiItem());
                gui.setItem(3, 4, ItemBuilder.from(skillsManager.farmingSkills.get(1117).getIcon()).asGuiItem());
                gui.setItem(3, 6, ItemBuilder.from(skillsManager.farmingSkills.get(1217).getIcon()).asGuiItem());
                gui.setItem(4, 4, ItemBuilder.from(skillsManager.farmingSkills.get(1118).getIcon()).asGuiItem());
                gui.setItem(4, 6, ItemBuilder.from(skillsManager.farmingSkills.get(1218).getIcon()).asGuiItem());
                gui.setItem(5, 4, ItemBuilder.from(skillsManager.farmingSkills.get(1119).getIcon()).asGuiItem());
                gui.setItem(5, 6, ItemBuilder.from(skillsManager.farmingSkills.get(1219).getIcon()).asGuiItem());
                gui.setItem(6, 4, ItemBuilder.from(skillsManager.farmingSkills.get(1120).getIcon()).asGuiItem());
                gui.setItem(6, 6, ItemBuilder.from(skillsManager.farmingSkills.get(1220).getIcon()).asGuiItem());
            }
            default -> {
                return;
            }
        }
    }

}
