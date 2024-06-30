package io.github.alathra.alathraskills.gui.skillcategory;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.gui.GuiHelper;
import io.github.alathra.alathraskills.utility.Cfg;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PopulateButtons {

    public static void populateButtons(Gui gui, Player player) {
        ItemStack border = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta borderMeta = border.getItemMeta();
        borderMeta.displayName(Component.text(""));
        border.setItemMeta(borderMeta);

        gui.getFiller().fill(ItemBuilder.from(border).asGuiItem());

        ItemStack back = new ItemStack(Material.PAPER);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.displayName(ColorParser.of("<red>Back").build());
        back.setItemMeta(backMeta);
        gui.setItem(4, 1, ItemBuilder.from(back).asGuiItem(event -> {
            GuiHelper.openMainGui(player);
        }));

        ItemStack availableSkillPoints = new ItemStack(Material.END_CRYSTAL);
        ItemMeta availableSkillPointsMeta = availableSkillPoints.getItemMeta();
        availableSkillPointsMeta.displayName(ColorParser.of(GuiHelper.EXPERIENCE_GRADIENT + "Skill Points").build().decoration(TextDecoration.ITALIC, false));
        availableSkillPointsMeta.lore(
            List.of(
                ColorParser.of("<color:#a8a8a8>Points: "+ availableSkillPoints(player)).build()//,
//                ColorParser.of("<color:#a8a8a8>Next Level: %s/%s".formatted(remainingExp, totalExp)).build() // TODO Add a method for this somewhere that is more accessible
            )
        );
        availableSkillPoints.setItemMeta(availableSkillPointsMeta);
        gui.setItem(4, 5, ItemBuilder.from(availableSkillPoints).asGuiItem());


        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.displayName(ColorParser.of("<red>Close").build());
        exit.setItemMeta(exitMeta);
        gui.setItem(4, 9, ItemBuilder.from(exit).asGuiItem(event -> gui.close(player)));
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

        if (skillPointsAvailable < 0) return 0;

        return skillPointsAvailable;
    }

}
