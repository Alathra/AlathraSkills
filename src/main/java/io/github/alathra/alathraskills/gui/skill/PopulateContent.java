package io.github.alathra.alathraskills.gui.skill;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.gui.GuiHelper;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;

public class PopulateContent {

    private static SkillsManager skillsManager = AlathraSkills.getSkillsManager();
    private static HashMap<Integer, Boolean> hasSkill = new HashMap<>();

    public static void populateContent(Gui gui, Player player, int skillCategoryId, int page) {


        switch (skillCategoryId) {
            case 1 -> {
                switch (page) {
                    case 1 -> populateSkillContent(gui, player, skillCategoryId, page, new int[]{101, 102, 103, 104, 105});
                    case 2 -> populateSkillContent(gui, player, skillCategoryId, page, new int[]{106, 107, 108, 109, 110});
                    case 3 -> populateSkillContent(gui, player, skillCategoryId, page, new int[]{1111, 1112, 1113, 1114, 1115, 1211, 1212, 1213, 1214, 1215});
                    case 4 -> populateSkillContent(gui, player, skillCategoryId, page, new int[]{1116, 1117, 1118, 1119, 1120, 1216, 1217, 1218, 1219, 1220});
                }
            }
            case 2 -> {
                switch (page) {
                    case 1 -> populateSkillContent(gui, player, skillCategoryId, page, new int[]{201, 202, 203, 204, 205});
                    case 2 -> populateSkillContent(gui, player, skillCategoryId, page, new int[]{206, 207, 208, 209, 210});
                    case 3 -> populateSkillContent(gui, player, skillCategoryId, page, new int[]{2111, 2112, 2113, 2114, 2115, 2211, 2212, 2213, 2214, 2215});
                    case 4 -> populateSkillContent(gui, player, skillCategoryId, page, new int[]{2116, 2117, 2118, 2119, 2120, 2216, 2217, 2218, 2219, 2220});
                }
            }
            case 3 -> {
                switch (page) {
                    case 1 -> populateSkillContent(gui, player, skillCategoryId, page, new int[]{301, 302, 303, 304, 305});
                    case 2 -> populateSkillContent(gui, player, skillCategoryId, page, new int[]{306, 307, 308, 309, 310});
                    case 3 -> populateSkillContent(gui, player, skillCategoryId, page, new int[]{3111, 3112, 3113, 3114, 3115, 3211, 3212, 3213, 3214, 3215});
                    case 4 -> populateSkillContent(gui, player, skillCategoryId, page, new int[]{3116, 3117, 3118, 3119, 3120, 3216, 3217, 3218, 3219, 3220});
                }
            }
            default -> {
                return;
            }
        }
    }

    public static void populateSkillContent(Gui gui, Player player, int skillCategoryId, int page, int[] ids) {
        switch (page) {
            case 1, 2 -> {
                hasSkill.clear();
                for (int i : ids) {
                    hasSkill.put(i, SkillsPlayerManager.playerHasSkill(player, i));
                }

                gui.setItem(3, 3, ItemBuilder.from(skillsManager.skills.get(ids[0]).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, ids[0], skillCategoryId, page)));
                gui.setItem(3, 4, ItemBuilder.from(skillsManager.skills.get(ids[1]).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, ids[1], skillCategoryId, page)));
                gui.setItem(3, 5, ItemBuilder.from(skillsManager.skills.get(ids[2]).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, ids[2], skillCategoryId, page)));
                gui.setItem(3, 6, ItemBuilder.from(skillsManager.skills.get(ids[3]).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, ids[3], skillCategoryId, page)));
                gui.setItem(3, 7, ItemBuilder.from(skillsManager.skills.get(ids[4]).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, ids[4], skillCategoryId, page)));

                replaceSkillRow(gui, ids, hasSkill);
            }
            case 3, 4 -> {
                hasSkill.clear();
                for (int i : ids) {
                    hasSkill.put(i, SkillsPlayerManager.playerHasSkill(player, i));
                }

                gui.setItem(2, 3, ItemBuilder.from(skillsManager.skills.get(ids[0]).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, ids[0], skillCategoryId, page)));
                gui.setItem(2, 4, ItemBuilder.from(skillsManager.skills.get(ids[1]).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, ids[1], skillCategoryId, page)));
                gui.setItem(2, 5, ItemBuilder.from(skillsManager.skills.get(ids[2]).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, ids[2], skillCategoryId, page)));
                gui.setItem(2, 6, ItemBuilder.from(skillsManager.skills.get(ids[3]).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, ids[3], skillCategoryId, page)));
                gui.setItem(2, 7, ItemBuilder.from(skillsManager.skills.get(ids[4]).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, ids[4], skillCategoryId, page)));

                gui.setItem(4, 3, ItemBuilder.from(skillsManager.skills.get(ids[5]).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, ids[5], skillCategoryId, page)));
                gui.setItem(4, 4, ItemBuilder.from(skillsManager.skills.get(ids[6]).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, ids[6], skillCategoryId, page)));
                gui.setItem(4, 5, ItemBuilder.from(skillsManager.skills.get(ids[7]).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, ids[7], skillCategoryId, page)));
                gui.setItem(4, 6, ItemBuilder.from(skillsManager.skills.get(ids[8]).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, ids[8], skillCategoryId, page)));
                gui.setItem(4, 7, ItemBuilder.from(skillsManager.skills.get(ids[9]).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, ids[9], skillCategoryId, page)));

                switch (skillCategoryId) {
                    case 1 -> replaceSkillRowAndColumn(gui, ids, hasSkill, 1200);
                    case 2 -> replaceSkillRowAndColumn(gui, ids, hasSkill, 2200);
                    case 3 -> replaceSkillRowAndColumn(gui, ids, hasSkill, 3200);
                }
            }
            default -> {
                return;
            }
        }
    }

    private static void replaceSkillRow(Gui gui, int[] ids, HashMap<Integer, Boolean> hasSkill) {
        int col = 3;
        for (int i : ids) {
            if (hasSkill.get(i)) {
                ItemStack originalIcon = skillsManager.skills.get(i).getIcon();
                ItemMeta originalIconMeta = originalIcon.getItemMeta();

                ItemStack icon = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
                ItemMeta iconMeta = icon.getItemMeta();

                iconMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                iconMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                iconMeta.displayName(ColorParser.of("<green><bold>Unlocked: ").build().append(originalIconMeta.displayName()));
                iconMeta.lore(originalIconMeta.lore());
                icon.setItemMeta(iconMeta);
                gui.setItem(3, col, ItemBuilder.from(icon).asGuiItem());
            }
            col++;
        }
    }

    private static void replaceSkillRowAndColumn(Gui gui, int[] ids, HashMap<Integer, Boolean> hasSkill, int limit) {
        int col = 3;
        for (int i : ids) {
            if (hasSkill.get(i)) {
                ItemStack originalIcon = skillsManager.skills.get(i).getIcon();
                ItemMeta originalIconMeta = originalIcon.getItemMeta();

                ItemStack icon = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
                ItemMeta iconMeta = icon.getItemMeta();

                iconMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                iconMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                iconMeta.displayName(ColorParser.of("<aqua><bold>Unlocked: ").build().append(originalIconMeta.displayName()));
                iconMeta.lore(originalIconMeta.lore());
                icon.setItemMeta(iconMeta);

                int row;
                if (i > limit) {
                    row = 4;
                } else {
                    row = 2;
                }

                gui.setItem(row, col, ItemBuilder.from(icon).asGuiItem());
            }
            if (i > limit) {
                col++;
            }
        }
    }
}
