package io.github.alathra.alathraskills.gui.main;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import io.github.alathra.alathraskills.AlathraSkills;
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
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Collections;

public class PopulateContent {

    public static void populateContent(Gui gui, Player player) {
        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(player.getUniqueId());

        float totalExp = SkillsPlayerManager.getTotalExperience(offlinePlayer);

        float expPerLevel = Cfg.get().getFloat("experience.perLevel");

        float remainingExp = expPerLevel - (totalExp % expPerLevel);
        if (totalExp < expPerLevel) {
            remainingExp = expPerLevel - totalExp;
        }

        int skillPointsAvailable = (int) ((totalExp - remainingExp) / expPerLevel);
        int unlockedSkills = SkillsPlayerManager.getUsedSkillPoints(offlinePlayer);

        skillPointsAvailable = skillPointsAvailable - unlockedSkills;

        // Should fix issues with UI showing -1 available.
        if (skillPointsAvailable < 0) skillPointsAvailable = 0;

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

        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwningPlayer(offlinePlayer);
        skullMeta.displayName(ColorParser.of("<color:#00B300><bold>Reset skills").build());
        playerHead.setItemMeta(skullMeta);

        ItemStack border = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta borderMeta = border.getItemMeta();
        borderMeta.displayName(Component.text(""));
        border.setItemMeta(borderMeta);

        gui.getFiller().fill(ItemBuilder.from(border).asGuiItem());

        gui.setItem(2, 5, ItemBuilder.from(totalExpItem).asGuiItem());
        gui.setItem(3, 4, ItemBuilder.from(availableSkillPoints).asGuiItem());
        gui.setItem(3, 5, ItemBuilder.from(openSkillTrees).asGuiItem(event -> {
            GuiHelper.openSkillCategoryGui(player);
        }));
        gui.setItem(3, 6, ItemBuilder.from(expToNext).asGuiItem());
        gui.setItem(5, 5, ItemBuilder.from(playerHead).asGuiItem(event -> GuiHelper.openResetGui(player)));
        gui.setItem(5, 9, ItemBuilder.from(exit).asGuiItem(event -> gui.close(player)));
    }

}
