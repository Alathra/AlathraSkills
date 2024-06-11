package io.github.alathra.alathraskills.gui.skill;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.gui.GuiHelper;
import io.github.alathra.alathraskills.utility.Cfg;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

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

        ItemStack returnStack = new ItemStack(Material.ARROW);
        ItemMeta returnMeta = returnStack.getItemMeta();
        returnMeta.displayName(ColorParser.of("<red>Previous page").build());
        returnStack.setItemMeta(returnMeta);

        ItemStack nextStack = new ItemStack(Material.ARROW);
        ItemMeta nextMeta = returnStack.getItemMeta();
        nextMeta.displayName(ColorParser.of("<green>Next page").build());
        nextStack.setItemMeta(nextMeta);

        ItemStack openSkillTrees = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta openSkillTreesMeta = openSkillTrees.getItemMeta();
        openSkillTreesMeta.displayName(ColorParser.of("<yellow><bold>Back to Skill Trees").build());
        openSkillTrees.setItemMeta(openSkillTreesMeta);

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.displayName(ColorParser.of("<dark_red><bold>Exit").build());
        exit.setItemMeta(exitMeta);

        ItemStack availableSkillPoints = new ItemStack(Material.BOOK);
        ItemMeta availableSkillPointsMeta = availableSkillPoints.getItemMeta();
        availableSkillPointsMeta.displayName(ColorParser.of("<red><bold>Available Skill Points").build());
        availableSkillPointsMeta.lore(Collections.singletonList(ColorParser.of("<yellow>Amount: " + availableSkillPoints(player)).build()));
        availableSkillPoints.setItemMeta(availableSkillPointsMeta);

        ItemStack border = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta borderMeta = border.getItemMeta();
        borderMeta.displayName(Component.text(""));
        border.setItemMeta(borderMeta);

        switch (page) {
            case 1 -> {
                gui.getFiller().fillBetweenPoints(4, 1, 4, 9, ItemBuilder.from(border).asGuiItem());
                gui.setItem(5, 1, ItemBuilder.from(openSkillTrees).asGuiItem(event -> GuiHelper.openSkillCategoryGui(player)));
                gui.setItem(5, 5, ItemBuilder.from(availableSkillPoints).asGuiItem());
                gui.setItem(5, 6, ItemBuilder.from(nextStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 3, page + 1)));
                gui.setItem(5, 9, ItemBuilder.from(exit).asGuiItem(event -> gui.close(player)));
            }
            case 2 -> {
                gui.getFiller().fillBetweenPoints(4, 1, 4, 9, ItemBuilder.from(border).asGuiItem());
                gui.setItem(5, 1, ItemBuilder.from(openSkillTrees).asGuiItem(event -> GuiHelper.openSkillCategoryGui(player)));
                gui.setItem(5, 4, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 3, page - 1)));
                gui.setItem(5, 5, ItemBuilder.from(availableSkillPoints).asGuiItem());
                gui.setItem(5, 6, ItemBuilder.from(nextStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 3, page + 1)));
                gui.setItem(5, 9, ItemBuilder.from(exit).asGuiItem(event -> gui.close(player)));
            }
            case 3 -> {
                gui.getFiller().fillBetweenPoints(4, 1, 4, 9, ItemBuilder.from(border).asGuiItem());
                gui.setItem(5, 1, ItemBuilder.from(openSkillTrees).asGuiItem(event -> GuiHelper.openSkillCategoryGui(player)));
                gui.setItem(5, 4, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 3, page - 1)));
                gui.setItem(5, 5, ItemBuilder.from(availableSkillPoints).asGuiItem());
                gui.setItem(5, 9, ItemBuilder.from(exit).asGuiItem(event -> gui.close(player)));
            }
        }
    }

    private static void populateMiningButtons(Gui gui, Player player, int page) {
        ItemStack returnStack = new ItemStack(Material.ARROW);
        ItemMeta returnMeta = returnStack.getItemMeta();
        returnMeta.displayName(ColorParser.of("<red>Previous page").build());
        returnStack.setItemMeta(returnMeta);

        ItemStack nextStack = new ItemStack(Material.ARROW);
        ItemMeta nextMeta = returnStack.getItemMeta();
        nextMeta.displayName(ColorParser.of("<green>Next page").build());
        nextStack.setItemMeta(nextMeta);

        ItemStack openSkillTrees = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta openSkillTreesMeta = openSkillTrees.getItemMeta();
        openSkillTreesMeta.displayName(ColorParser.of("<yellow><bold>Back to Skill Trees").build());
        openSkillTrees.setItemMeta(openSkillTreesMeta);

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.displayName(ColorParser.of("<dark_red><bold>Exit").build());
        exit.setItemMeta(exitMeta);

        ItemStack availableSkillPoints = new ItemStack(Material.BOOK);
        ItemMeta availableSkillPointsMeta = availableSkillPoints.getItemMeta();
        availableSkillPointsMeta.displayName(ColorParser.of("<red><bold>Available Skill Points").build());
        availableSkillPointsMeta.lore(Collections.singletonList(ColorParser.of("<yellow>Amount: " + availableSkillPoints(player)).build()));
        availableSkillPoints.setItemMeta(availableSkillPointsMeta);

        ItemStack border = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta borderMeta = border.getItemMeta();
        borderMeta.displayName(Component.text(""));
        border.setItemMeta(borderMeta);

        switch (page) {
            case 1 -> {
                gui.getFiller().fillBetweenPoints(4, 1, 4, 9, ItemBuilder.from(border).asGuiItem());
                gui.setItem(5, 1, ItemBuilder.from(openSkillTrees).asGuiItem(event -> GuiHelper.openSkillCategoryGui(player)));
                gui.setItem(5, 5, ItemBuilder.from(availableSkillPoints).asGuiItem());
                gui.setItem(5, 6, ItemBuilder.from(nextStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 2, page + 1)));
                gui.setItem(5, 9, ItemBuilder.from(exit).asGuiItem(event -> gui.close(player)));
            }
            case 2 -> {
                gui.getFiller().fillBetweenPoints(4, 1, 4, 9, ItemBuilder.from(border).asGuiItem());
                gui.setItem(5, 1, ItemBuilder.from(openSkillTrees).asGuiItem(event -> GuiHelper.openSkillCategoryGui(player)));
                gui.setItem(5, 4, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 2, page - 1)));
                gui.setItem(5, 5, ItemBuilder.from(availableSkillPoints).asGuiItem());
                gui.setItem(5, 6, ItemBuilder.from(nextStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 2, page + 1)));
                gui.setItem(5, 9, ItemBuilder.from(exit).asGuiItem(event -> gui.close(player)));
            }
            case 3 -> {
                gui.getFiller().fillBetweenPoints(4, 1, 4, 9, ItemBuilder.from(border).asGuiItem());
                gui.setItem(5, 1, ItemBuilder.from(openSkillTrees).asGuiItem(event -> GuiHelper.openSkillCategoryGui(player)));
                gui.setItem(5, 4, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 2, page - 1)));
                gui.setItem(5, 5, ItemBuilder.from(availableSkillPoints).asGuiItem());
                gui.setItem(5, 9, ItemBuilder.from(exit).asGuiItem(event -> gui.close(player)));
            }
        }
    }

    private static void populateFarmingButtons(Gui gui, Player player, int page) {
        ItemStack returnStack = new ItemStack(Material.ARROW);
        ItemMeta returnMeta = returnStack.getItemMeta();
        returnMeta.displayName(ColorParser.of("<red>Previous page").build());
        returnStack.setItemMeta(returnMeta);

        ItemStack nextStack = new ItemStack(Material.ARROW);
        ItemMeta nextMeta = returnStack.getItemMeta();
        nextMeta.displayName(ColorParser.of("<green>Next page").build());
        nextStack.setItemMeta(nextMeta);

        ItemStack openSkillTrees = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta openSkillTreesMeta = openSkillTrees.getItemMeta();
        openSkillTreesMeta.displayName(ColorParser.of("<yellow><bold>Back to Skill Trees").build());
        openSkillTrees.setItemMeta(openSkillTreesMeta);

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.displayName(ColorParser.of("<dark_red><bold>Exit").build());
        exit.setItemMeta(exitMeta);

        ItemStack availableSkillPoints = new ItemStack(Material.BOOK);
        ItemMeta availableSkillPointsMeta = availableSkillPoints.getItemMeta();
        availableSkillPointsMeta.displayName(ColorParser.of("<red><bold>Available Skill Points").build());
        availableSkillPointsMeta.lore(Collections.singletonList(ColorParser.of("<yellow>Amount: " + availableSkillPoints(player)).build()));
        availableSkillPoints.setItemMeta(availableSkillPointsMeta);

        ItemStack border = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta borderMeta = border.getItemMeta();
        borderMeta.displayName(Component.text(""));
        border.setItemMeta(borderMeta);

        switch (page) {
            case 1 -> {
                gui.getFiller().fillBetweenPoints(4, 1, 4, 9, ItemBuilder.from(border).asGuiItem());
                gui.setItem(5, 1, ItemBuilder.from(openSkillTrees).asGuiItem(event -> GuiHelper.openSkillCategoryGui(player)));
                gui.setItem(5, 5, ItemBuilder.from(availableSkillPoints).asGuiItem());
                gui.setItem(5, 6, ItemBuilder.from(nextStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 1, page + 1)));
                gui.setItem(5, 9, ItemBuilder.from(exit).asGuiItem(event -> gui.close(player)));
            }
            case 2 -> {
                gui.getFiller().fillBetweenPoints(4, 1, 4, 9, ItemBuilder.from(border).asGuiItem());
                gui.setItem(5, 1, ItemBuilder.from(openSkillTrees).asGuiItem(event -> GuiHelper.openSkillCategoryGui(player)));
                gui.setItem(5, 4, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 1, page - 1)));
                gui.setItem(5, 5, ItemBuilder.from(availableSkillPoints).asGuiItem());
                gui.setItem(5, 6, ItemBuilder.from(nextStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 1, page + 1)));
                gui.setItem(5, 9, ItemBuilder.from(exit).asGuiItem(event -> gui.close(player)));
            }
            case 3 -> {
                gui.getFiller().fillBetweenPoints(4, 1, 4, 9, ItemBuilder.from(border).asGuiItem());
                gui.setItem(5, 1, ItemBuilder.from(openSkillTrees).asGuiItem(event -> GuiHelper.openSkillCategoryGui(player)));
                gui.setItem(5, 4, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillGui(player, 1, page - 1)));
                gui.setItem(5, 5, ItemBuilder.from(availableSkillPoints).asGuiItem());
                gui.setItem(5, 9, ItemBuilder.from(exit).asGuiItem(event -> gui.close(player)));
            }
        }
    }

    private static int availableSkillPoints(Player p) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(p.getUniqueId());

        float totalExp = SkillsPlayerManager.getTotalExperience(offlinePlayer);

        float expPerLevel = Cfg.get().getFloat("experience.perLevel");

        float remainingExp = totalExp % expPerLevel;
        if (totalExp < expPerLevel) {
            remainingExp = expPerLevel - totalExp;
        }

        int skillPointsAvailable = (int) ((totalExp - remainingExp) / expPerLevel);
        int unlockedSkills = SkillsPlayerManager.getUsedSkillPoints(offlinePlayer);
        skillPointsAvailable -= unlockedSkills;

        return skillPointsAvailable;
    }

}
