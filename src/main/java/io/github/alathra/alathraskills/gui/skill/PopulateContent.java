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
                int[] ids = new int[]{301, 302, 303, 304, 305};
                HashMap<Integer, Boolean> hasSkill = new HashMap<>();

                for (int i : ids) {
                    hasSkill.put(i, SkillsPlayerManager.playerHasSkill(player, i));
                }

                gui.setItem(3, 3, ItemBuilder.from(skillsManager.woodcuttingSkills.get(301).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 301, 3, page)));
                gui.setItem(3, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(302).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 302, 3, page)));
                gui.setItem(3, 5, ItemBuilder.from(skillsManager.woodcuttingSkills.get(303).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 303, 3, page)));
                gui.setItem(3, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(304).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 304, 3, page)));
                gui.setItem(3, 7, ItemBuilder.from(skillsManager.woodcuttingSkills.get(305).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 305, 3, page)));

                int col = 3;
                for (int i : ids) {
                    if (hasSkill.get(i)) {
                        ItemStack originalIcon = skillsManager.woodcuttingSkills.get(i).getIcon();
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
            case 2 -> {
                int[] ids = new int[]{306, 307, 308, 309, 310};
                HashMap<Integer, Boolean> hasSkill = new HashMap<>();

                for (int i : ids) {
                    hasSkill.put(i, SkillsPlayerManager.playerHasSkill(player, i));
                }

                gui.setItem(3, 3, ItemBuilder.from(skillsManager.woodcuttingSkills.get(306).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 306, 3, page)));
                gui.setItem(3, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(307).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 307, 3, page)));
                gui.setItem(3, 5, ItemBuilder.from(skillsManager.woodcuttingSkills.get(308).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 308, 3, page)));
                gui.setItem(3, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(309).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 309, 3, page)));
                gui.setItem(3, 7, ItemBuilder.from(skillsManager.woodcuttingSkills.get(310).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 310, 3, page)));

                int col = 3;
                for (int i : ids) {
                    if (hasSkill.get(i)) {
                        ItemStack originalIcon = skillsManager.woodcuttingSkills.get(i).getIcon();
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
            case 3 -> {
                int[] ids = new int[]{3111, 3211, 3112, 3212, 3113, 3213, 3114, 3214, 3115, 3215};
                HashMap<Integer, Boolean> hasSkill = new HashMap<>();

                for (int i : ids) {
                    hasSkill.put(i, SkillsPlayerManager.playerHasSkill(player, i));
                }

                gui.setItem(2, 3, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3111).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3111, 3, page)));
                gui.setItem(2, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3112).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3112, 3, page)));
                gui.setItem(2, 5, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3113).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3113, 3, page)));
                gui.setItem(2, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3114).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3114, 3, page)));
                gui.setItem(2, 7, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3115).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3115, 3, page)));

                gui.setItem(4, 3, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3211).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3211, 3, page)));
                gui.setItem(4, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3212).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3212, 3, page)));
                gui.setItem(4, 5, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3213).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3213, 3, page)));
                gui.setItem(4, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3214).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3214, 3, page)));
                gui.setItem(4, 7, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3215).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3215, 3, page)));

                int col = 3;
                for (int i : ids) {
                    if (hasSkill.get(i)) {
                        ItemStack originalIcon = skillsManager.woodcuttingSkills.get(i).getIcon();
                        ItemMeta originalIconMeta = originalIcon.getItemMeta();

                        ItemStack icon = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
                        ItemMeta iconMeta = icon.getItemMeta();

                        iconMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                        iconMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        iconMeta.displayName(ColorParser.of("<green><bold>Unlocked: ").build().append(originalIconMeta.displayName()));
                        iconMeta.lore(originalIconMeta.lore());
                        icon.setItemMeta(iconMeta);

                        int row;
                        if (i > 3200) {
                            row = 4;
                        } else {
                            row = 2;
                        }

                        gui.setItem(row, col, ItemBuilder.from(icon).asGuiItem());
                    }
                    if (i > 3200) {
                        col++;
                    }
                }
            }
            case 4 -> {
                int[] ids = new int[]{3116, 3216, 3117, 3217, 3118, 3218, 3119, 3219, 3120, 3220};
                HashMap<Integer, Boolean> hasSkill = new HashMap<>();

                for (int i : ids) {
                    hasSkill.put(i, SkillsPlayerManager.playerHasSkill(player, i));
                }

                gui.setItem(2, 3, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3116).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3116, 3, page)));
                gui.setItem(2, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3117).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3117, 3, page)));
                gui.setItem(2, 5, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3118).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3118, 3, page)));
                gui.setItem(2, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3119).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3119, 3, page)));
                gui.setItem(2, 7, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3120).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3120, 3, page)));

                gui.setItem(4, 3, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3216).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3216, 3, page)));
                gui.setItem(4, 4, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3217).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3217, 3, page)));
                gui.setItem(4, 5, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3218).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3218, 3, page)));
                gui.setItem(4, 6, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3219).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3219, 3, page)));
                gui.setItem(4, 7, ItemBuilder.from(skillsManager.woodcuttingSkills.get(3220).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 3220, 3, page)));


                int col = 3;
                for (int i : ids) {
                    if (hasSkill.get(i)) {
                        ItemStack originalIcon = skillsManager.woodcuttingSkills.get(i).getIcon();
                        ItemMeta originalIconMeta = originalIcon.getItemMeta();

                        ItemStack icon = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
                        ItemMeta iconMeta = icon.getItemMeta();

                        iconMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                        iconMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        iconMeta.displayName(ColorParser.of("<green><bold>Unlocked: ").build().append(originalIconMeta.displayName()));
                        iconMeta.lore(originalIconMeta.lore());
                        icon.setItemMeta(iconMeta);

                        int row;
                        if (i > 3200) {
                            row = 4;
                        } else {
                            row = 2;
                        }

                        gui.setItem(row, col, ItemBuilder.from(icon).asGuiItem());
                    }
                    if (i > 3200) {
                        col++;
                    }
                }
            }
            default -> {
                return;
            }
        }
    }

    private static void populateMiningContent(Gui gui, Player player, int page) {
        switch (page) {
            case 1 -> {
                int[] ids = new int[]{201, 202, 203, 204, 205};
                HashMap<Integer, Boolean> hasSkill = new HashMap<>();

                for (int i : ids) {
                    hasSkill.put(i, SkillsPlayerManager.playerHasSkill(player, i));
                }

                gui.setItem(3, 3, ItemBuilder.from(skillsManager.miningSkills.get(201).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 201, 2, page)));
                gui.setItem(3, 4, ItemBuilder.from(skillsManager.miningSkills.get(202).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 202, 2, page)));
                gui.setItem(3, 5, ItemBuilder.from(skillsManager.miningSkills.get(203).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 203, 2, page)));
                gui.setItem(3, 6, ItemBuilder.from(skillsManager.miningSkills.get(204).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 204, 2, page)));
                gui.setItem(3, 7, ItemBuilder.from(skillsManager.miningSkills.get(205).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 205, 2, page)));

                int col = 3;
                for (int i : ids) {
                    if (hasSkill.get(i)) {
                        ItemStack originalIcon = skillsManager.miningSkills.get(i).getIcon();
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
            case 2 -> {
                int[] ids = new int[]{206, 207, 208, 209, 210};
                HashMap<Integer, Boolean> hasSkill = new HashMap<>();

                for (int i : ids) {
                    hasSkill.put(i, SkillsPlayerManager.playerHasSkill(player, i));
                }

                gui.setItem(3, 3, ItemBuilder.from(skillsManager.miningSkills.get(206).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 206, 2, page)));
                gui.setItem(3, 4, ItemBuilder.from(skillsManager.miningSkills.get(207).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 207, 2, page)));
                gui.setItem(3, 5, ItemBuilder.from(skillsManager.miningSkills.get(208).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 208, 2, page)));
                gui.setItem(3, 6, ItemBuilder.from(skillsManager.miningSkills.get(209).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 209, 2, page)));
                gui.setItem(3, 7, ItemBuilder.from(skillsManager.miningSkills.get(210).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 210, 2, page)));

                int col = 3;
                for (int i : ids) {
                    if (hasSkill.get(i)) {
                        ItemStack originalIcon = skillsManager.miningSkills.get(i).getIcon();
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
            case 3 -> {
                int[] ids = new int[]{2111, 2211, 2112, 2212, 2113, 2213, 2114, 2214, 2115, 2215};
                HashMap<Integer, Boolean> hasSkill = new HashMap<>();

                for (int i : ids) {
                    hasSkill.put(i, SkillsPlayerManager.playerHasSkill(player, i));
                }

                gui.setItem(2, 3, ItemBuilder.from(skillsManager.miningSkills.get(2111).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2111, 2, page)));
                gui.setItem(2, 4, ItemBuilder.from(skillsManager.miningSkills.get(2112).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2112, 2, page)));
                gui.setItem(2, 5, ItemBuilder.from(skillsManager.miningSkills.get(2113).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2113, 2, page)));
                gui.setItem(2, 6, ItemBuilder.from(skillsManager.miningSkills.get(2114).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2114, 2, page)));
                gui.setItem(2, 7, ItemBuilder.from(skillsManager.miningSkills.get(2115).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2115, 2, page)));

                gui.setItem(4, 3, ItemBuilder.from(skillsManager.miningSkills.get(2211).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2211, 2, page)));
                gui.setItem(4, 4, ItemBuilder.from(skillsManager.miningSkills.get(2212).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2212, 2, page)));
                gui.setItem(4, 5, ItemBuilder.from(skillsManager.miningSkills.get(2213).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2213, 2, page)));
                gui.setItem(4, 6, ItemBuilder.from(skillsManager.miningSkills.get(2214).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2214, 2, page)));
                gui.setItem(4, 7, ItemBuilder.from(skillsManager.miningSkills.get(2215).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2215, 2, page)));

                int col = 3;
                for (int i : ids) {
                    if (hasSkill.get(i)) {
                        ItemStack originalIcon = skillsManager.miningSkills.get(i).getIcon();
                        ItemMeta originalIconMeta = originalIcon.getItemMeta();

                        ItemStack icon = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
                        ItemMeta iconMeta = icon.getItemMeta();

                        iconMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                        iconMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        iconMeta.displayName(ColorParser.of("<green><bold>Unlocked: ").build().append(originalIconMeta.displayName()));
                        iconMeta.lore(originalIconMeta.lore());
                        icon.setItemMeta(iconMeta);

                        int row;
                        if (i > 2200) {
                            row = 4;
                        } else {
                            row = 2;
                        }

                        gui.setItem(row, col, ItemBuilder.from(icon).asGuiItem());
                    }
                    if (i > 2200) {
                        col++;
                    }
                }
            }
            case 4 -> {
                int[] ids = new int[]{2116, 2216, 2117, 2217, 2118, 2218, 2119, 2219, 2120, 2220};
                HashMap<Integer, Boolean> hasSkill = new HashMap<>();

                for (int i : ids) {
                    hasSkill.put(i, SkillsPlayerManager.playerHasSkill(player, i));
                }

                gui.setItem(2, 3, ItemBuilder.from(skillsManager.miningSkills.get(2116).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2116, 2, page)));
                gui.setItem(2, 4, ItemBuilder.from(skillsManager.miningSkills.get(2117).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2117, 2, page)));
                gui.setItem(2, 5, ItemBuilder.from(skillsManager.miningSkills.get(2118).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2118, 2, page)));
                gui.setItem(2, 6, ItemBuilder.from(skillsManager.miningSkills.get(2119).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2119, 2, page)));
                gui.setItem(2, 7, ItemBuilder.from(skillsManager.miningSkills.get(2120).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2120, 2, page)));

                gui.setItem(4, 3, ItemBuilder.from(skillsManager.miningSkills.get(2216).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2216, 2, page)));
                gui.setItem(4, 4, ItemBuilder.from(skillsManager.miningSkills.get(2217).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2217, 2, page)));
                gui.setItem(4, 5, ItemBuilder.from(skillsManager.miningSkills.get(2218).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2218, 2, page)));
                gui.setItem(4, 6, ItemBuilder.from(skillsManager.miningSkills.get(2219).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2219, 2, page)));
                gui.setItem(4, 7, ItemBuilder.from(skillsManager.miningSkills.get(2220).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 2220, 2, page)));


                int col = 3;
                for (int i : ids) {
                    if (hasSkill.get(i)) {
                        ItemStack originalIcon = skillsManager.miningSkills.get(i).getIcon();
                        ItemMeta originalIconMeta = originalIcon.getItemMeta();

                        ItemStack icon = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
                        ItemMeta iconMeta = icon.getItemMeta();

                        iconMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                        iconMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        iconMeta.displayName(ColorParser.of("<green><bold>Unlocked: ").build().append(originalIconMeta.displayName()));
                        iconMeta.lore(originalIconMeta.lore());
                        icon.setItemMeta(iconMeta);

                        int row;
                        if (i > 2200) {
                            row = 4;
                        } else {
                            row = 2;
                        }

                        gui.setItem(row, col, ItemBuilder.from(icon).asGuiItem());
                    }
                    if (i > 2200) {
                        col++;
                    }
                }
            }
            default -> {
                return;
            }
        }
    }

    private static void populateFarmingContent(Gui gui, Player player, int page) {
        switch (page) {
            case 1 -> {
                int[] ids = new int[]{101, 102, 103, 104, 105};
                HashMap<Integer, Boolean> hasSkill = new HashMap<>();

                for (int i : ids) {
                    hasSkill.put(i, SkillsPlayerManager.playerHasSkill(player, i));
                }

                gui.setItem(3, 3, ItemBuilder.from(skillsManager.farmingSkills.get(101).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 101, 1, page)));
                gui.setItem(3, 4, ItemBuilder.from(skillsManager.farmingSkills.get(102).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 102, 1, page)));
                gui.setItem(3, 5, ItemBuilder.from(skillsManager.farmingSkills.get(103).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 103, 1, page)));
                gui.setItem(3, 6, ItemBuilder.from(skillsManager.farmingSkills.get(104).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 104, 1, page)));
                gui.setItem(3, 7, ItemBuilder.from(skillsManager.farmingSkills.get(105).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 105, 1, page)));

                int col = 3;
                for (int i : ids) {
                    if (hasSkill.get(i)) {
                        ItemStack originalIcon = skillsManager.farmingSkills.get(i).getIcon();
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
            case 2 -> {
                int[] ids = new int[]{106, 107, 108, 109, 110};
                HashMap<Integer, Boolean> hasSkill = new HashMap<>();

                for (int i : ids) {
                    hasSkill.put(i, SkillsPlayerManager.playerHasSkill(player, i));
                }

                gui.setItem(3, 3, ItemBuilder.from(skillsManager.farmingSkills.get(106).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 106, 1, page)));
                gui.setItem(3, 4, ItemBuilder.from(skillsManager.farmingSkills.get(107).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 107, 1, page)));
                gui.setItem(3, 5, ItemBuilder.from(skillsManager.farmingSkills.get(108).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 108, 1, page)));
                gui.setItem(3, 6, ItemBuilder.from(skillsManager.farmingSkills.get(109).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 109, 1, page)));
                gui.setItem(3, 7, ItemBuilder.from(skillsManager.farmingSkills.get(110).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 110, 1, page)));


                int col = 3;
                for (int i : ids) {
                    if (hasSkill.get(i)) {
                        ItemStack originalIcon = skillsManager.farmingSkills.get(i).getIcon();
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
            case 3 -> {
                int[] ids = new int[]{1111, 1211, 1112, 1212, 1113, 1213, 1114, 1214, 1115, 1215};
                HashMap<Integer, Boolean> hasSkill = new HashMap<>();

                for (int i : ids) {
                    hasSkill.put(i, SkillsPlayerManager.playerHasSkill(player, i));
                }

                gui.setItem(2, 3, ItemBuilder.from(skillsManager.farmingSkills.get(1111).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1111, 1, page)));
                gui.setItem(2, 4, ItemBuilder.from(skillsManager.farmingSkills.get(1112).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1112, 1, page)));
                gui.setItem(2, 5, ItemBuilder.from(skillsManager.farmingSkills.get(1113).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1113, 1, page)));
                gui.setItem(2, 6, ItemBuilder.from(skillsManager.farmingSkills.get(1114).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1114, 1, page)));
                gui.setItem(2, 7, ItemBuilder.from(skillsManager.farmingSkills.get(1115).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1115, 1, page)));

                gui.setItem(4, 3, ItemBuilder.from(skillsManager.farmingSkills.get(1211).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1211, 1, page)));
                gui.setItem(4, 4, ItemBuilder.from(skillsManager.farmingSkills.get(1212).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1212, 1, page)));
                gui.setItem(4, 5, ItemBuilder.from(skillsManager.farmingSkills.get(1213).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1213, 1, page)));
                gui.setItem(4, 6, ItemBuilder.from(skillsManager.farmingSkills.get(1214).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1214, 1, page)));
                gui.setItem(4, 7, ItemBuilder.from(skillsManager.farmingSkills.get(1215).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1215, 1, page)));

                int col = 3;
                for (int i : ids) {
                    if (hasSkill.get(i)) {
                        ItemStack originalIcon = skillsManager.farmingSkills.get(i).getIcon();
                        ItemMeta originalIconMeta = originalIcon.getItemMeta();

                        ItemStack icon = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
                        ItemMeta iconMeta = icon.getItemMeta();

                        iconMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                        iconMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        iconMeta.displayName(ColorParser.of("<green><bold>Unlocked: ").build().append(originalIconMeta.displayName()));
                        iconMeta.lore(originalIconMeta.lore());
                        icon.setItemMeta(iconMeta);

                        int row;
                        if (i > 1200) {
                            row = 4;
                        } else {
                            row = 2;
                        }

                        gui.setItem(row, col, ItemBuilder.from(icon).asGuiItem());
                    }
                    if (i > 1200) {
                        col++;
                    }
                }
            }
            case 4 -> {
                int[] ids = new int[]{1116, 1216, 1117, 1217, 1118, 1218, 1119, 1219, 1120, 1220};
                HashMap<Integer, Boolean> hasSkill = new HashMap<>();

                for (int i : ids) {
                    hasSkill.put(i, SkillsPlayerManager.playerHasSkill(player, i));
                }

                gui.setItem(2, 3, ItemBuilder.from(skillsManager.farmingSkills.get(1116).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1116, 1, page)));
                gui.setItem(2, 4, ItemBuilder.from(skillsManager.farmingSkills.get(1117).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1117, 1, page)));
                gui.setItem(2, 5, ItemBuilder.from(skillsManager.farmingSkills.get(1118).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1118, 1, page)));
                gui.setItem(2, 6, ItemBuilder.from(skillsManager.farmingSkills.get(1119).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1119, 1, page)));
                gui.setItem(2, 7, ItemBuilder.from(skillsManager.farmingSkills.get(1120).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1120, 1, page)));

                gui.setItem(4, 3, ItemBuilder.from(skillsManager.farmingSkills.get(1216).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1216, 1, page)));
                gui.setItem(4, 4, ItemBuilder.from(skillsManager.farmingSkills.get(1217).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1217, 1, page)));
                gui.setItem(4, 5, ItemBuilder.from(skillsManager.farmingSkills.get(1218).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1218, 1, page)));
                gui.setItem(4, 6, ItemBuilder.from(skillsManager.farmingSkills.get(1219).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1219, 1, page)));
                gui.setItem(4, 7, ItemBuilder.from(skillsManager.farmingSkills.get(1220).getIcon()).asGuiItem(event -> GuiHelper.openConfirmGui(player, 1220, 1, page)));


                int col = 3;
                for (int i : ids) {
                    if (hasSkill.get(i)) {
                        ItemStack originalIcon = skillsManager.farmingSkills.get(i).getIcon();
                        ItemMeta originalIconMeta = originalIcon.getItemMeta();

                        ItemStack icon = new ItemStack(Material.GREEN_STAINED_GLASS_PANE);
                        ItemMeta iconMeta = icon.getItemMeta();

                        iconMeta.addEnchant(Enchantment.DURABILITY, 1, true);
                        iconMeta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
                        iconMeta.displayName(ColorParser.of("<green><bold>Unlocked: ").build().append(originalIconMeta.displayName()));
                        iconMeta.lore(originalIconMeta.lore());
                        icon.setItemMeta(iconMeta);

                        int row;
                        if (i > 1200) {
                            row = 4;
                        } else {
                            row = 2;
                        }

                        gui.setItem(row, col, ItemBuilder.from(icon).asGuiItem());
                    }
                    if (i > 1200) {
                        col++;
                    }
                }
            }
            default -> {
                return;
            }
        }
    }

}
