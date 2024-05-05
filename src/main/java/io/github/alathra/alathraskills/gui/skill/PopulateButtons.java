package io.github.alathra.alathraskills.gui.skill;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.gui.GuiHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PopulateButtons {

    static SkillsManager skillsManager = AlathraSkills.getSkillsManager();

    public static void populateButtons(Gui gui, Player player, int skillCategoryId, int page) {


        switch (skillCategoryId) {
            case 1 -> {
                populateFarmingButtons(gui, player, page);
            }
            case 2 -> {
                populateMiningButtons(gui, player, page);
            }
            case 3 -> {
                populateWoodcuttingButtons(gui, player, page);
            }
            default -> {
                return;
            }
        }
    }

    private static void populateWoodcuttingButtons(Gui gui, Player player, int page) {
        ItemStack returnStack = new ItemStack(Material.PAPER);
        ItemMeta returnMeta = returnStack.getItemMeta();
        returnMeta.displayName(ColorParser.of("<bold><red>Return").build());
        returnStack.setItemMeta(returnMeta);

        ItemStack nextStack = new ItemStack(Material.PAPER);
        ItemMeta nextMeta = returnStack.getItemMeta();
        nextMeta.displayName(ColorParser.of("<bold><green>Next").build());
        nextStack.setItemMeta(nextMeta);

        switch (page) {
            case 1 -> {
                gui.setItem(6, 1, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillCategoryGui(player)));
                gui.setItem(6, 9, ItemBuilder.from(nextStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 3, page + 1)));
            }
            case 2, 3 -> {
                gui.setItem(6, 1, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 3, page - 1)));
                gui.setItem(6, 9, ItemBuilder.from(nextStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 3, page + 1)));
            }
            case 4 -> {
                gui.setItem(6, 1, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 3, page - 1)));
            }
        }
    }

    private static void populateMiningButtons(Gui gui, Player player, int page) {
        ItemStack returnStack = new ItemStack(Material.PAPER);
        ItemMeta returnMeta = returnStack.getItemMeta();
        returnMeta.displayName(ColorParser.of("<bold><red>Return").build());
        returnStack.setItemMeta(returnMeta);

        ItemStack nextStack = new ItemStack(Material.PAPER);
        ItemMeta nextMeta = returnStack.getItemMeta();
        nextMeta.displayName(ColorParser.of("<bold><green>Next").build());
        nextStack.setItemMeta(nextMeta);

        switch (page) {
            case 1 -> {
                gui.setItem(6, 1, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillCategoryGui(player)));
                gui.setItem(6, 9, ItemBuilder.from(nextStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 2, page + 1)));
            }
            case 2, 3 -> {
                gui.setItem(6, 1, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 2, page - 1)));
                gui.setItem(6, 9, ItemBuilder.from(nextStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 2, page + 1)));
            }
            case 4 -> {
                gui.setItem(6, 1, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 2, page - 1)));
            }
        }
    }

    private static void populateFarmingButtons(Gui gui, Player player, int page) {
        ItemStack returnStack = new ItemStack(Material.PAPER);
        ItemMeta returnMeta = returnStack.getItemMeta();
        returnMeta.displayName(ColorParser.of("<bold><red>Return").build());
        returnStack.setItemMeta(returnMeta);

        ItemStack nextStack = new ItemStack(Material.PAPER);
        ItemMeta nextMeta = returnStack.getItemMeta();
        nextMeta.displayName(ColorParser.of("<bold><green>Next").build());
        nextStack.setItemMeta(nextMeta);

        switch (page) {
            case 1 -> {
                gui.setItem(6, 1, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillCategoryGui(player)));
                gui.setItem(6, 9, ItemBuilder.from(nextStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 1, page + 1)));
            }
            case 2, 3 -> {
                gui.setItem(6, 1, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 1, page - 1)));
                gui.setItem(6, 9, ItemBuilder.from(nextStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 1, page + 1)));
            }
            case 4 -> {
                gui.setItem(6, 1, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 1, page - 1)));
            }
        }
    }

}
