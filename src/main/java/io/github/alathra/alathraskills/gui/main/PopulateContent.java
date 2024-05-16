package io.github.alathra.alathraskills.gui.main;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.gui.GuiHelper;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class PopulateContent {

    // TODO: all of this math should probably be refactored out of this class
    public static void populateContent(Gui gui, Player player) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());
        float farmingExp = SkillsPlayerManager.getSkillCategoryExperience(offlinePlayer, 1);
        float miningExp = SkillsPlayerManager.getSkillCategoryExperience(offlinePlayer, 2);
        float woodcuttingExp = SkillsPlayerManager.getSkillCategoryExperience(offlinePlayer, 3);
        float totalExp = farmingExp + miningExp + woodcuttingExp;

        float remainingExp = totalExp % 5000;
        if (totalExp < 5000) {
            remainingExp = 5000 - totalExp;
        }

        int skillPointsAvailable = (int) ((totalExp - remainingExp) / 5000);
        int unlockedSkills = (int) SkillsPlayerManager.getAllSkills(offlinePlayer).count();

        skillPointsAvailable -= unlockedSkills;

        ItemStack expToNext = new ItemStack(Material.EMERALD);
        ItemMeta expToNextMeta = expToNext.getItemMeta();
        expToNextMeta.displayName(ColorParser.of("<green><bold>Exp to Next Skill Point").build());
        expToNextMeta.lore(Collections.singletonList(ColorParser.of("<yellow>Amount: " + Math.round(remainingExp)).build()));
        expToNext.setItemMeta(expToNextMeta);

        ItemStack availableSkillPoints = new ItemStack(Material.BOOK);
        ItemMeta availableSkillPointsMeta = availableSkillPoints.getItemMeta();
        availableSkillPointsMeta.displayName(ColorParser.of("<red><bold>Available Skill Points").build());
        availableSkillPointsMeta.lore(Collections.singletonList(ColorParser.of("<yellow>Amount: " + skillPointsAvailable).build()));
        availableSkillPoints.setItemMeta(availableSkillPointsMeta);

        ItemStack openSkillTrees = new ItemStack(Material.EXPERIENCE_BOTTLE);
        ItemMeta openSkillTreesMeta = openSkillTrees.getItemMeta();
        openSkillTreesMeta.displayName(ColorParser.of("<yellow><bold>Open Skill Trees").build());
        openSkillTrees.setItemMeta(openSkillTreesMeta);

        ItemStack totalExpItem = new ItemStack(Material.AMETHYST_SHARD);
        ItemMeta totalExpMeta = totalExpItem.getItemMeta();
        totalExpMeta.displayName(ColorParser.of("<light_purple><bold>Total Exp Earned").build());
        totalExpMeta.lore(Collections.singletonList(ColorParser.of("<yellow>Amount: " + Math.round(totalExp)).build()));
        totalExpItem.setItemMeta(totalExpMeta);

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.displayName(ColorParser.of("<dark_red><bold>Exit").build());
        exit.setItemMeta(exitMeta);

        gui.setItem(0, ItemBuilder.from(expToNext).asGuiItem());
        gui.setItem(1, ItemBuilder.from(availableSkillPoints).asGuiItem());
        gui.setItem(4, ItemBuilder.from(openSkillTrees).asGuiItem(event -> {
            GuiHelper.openSkillCategoryGui(player);
        }));
        gui.setItem(7, ItemBuilder.from(totalExpItem).asGuiItem());
        gui.setItem(8, ItemBuilder.from(exit).asGuiItem(event -> gui.close(player)));
    }

}
