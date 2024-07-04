package io.github.alathra.alathraskills.gui.main;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.gui.GuiHelper;
import io.github.alathra.alathraskills.utility.Cfg;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PopulateContent {

    private static final SkillsManager skillsManager = AlathraSkills.getSkillsManager();


    public static void populateContent(Gui gui, Player player) {
        SkillsPlayer skillsPlayer = AlathraSkills.getSkillsPlayerManager().getSkillsPlayer(player);

        if (skillsPlayer == null)
            return;

        float expPerLevel = Cfg.get().getFloat("experience.perLevel");

        ItemStack availableSkillPoints = new ItemStack(Material.END_CRYSTAL);
        ItemMeta availableSkillPointsMeta = availableSkillPoints.getItemMeta();
        availableSkillPointsMeta.displayName(ColorParser.of(GuiHelper.EXPERIENCE_GRADIENT + "Skill Points").build().decoration(TextDecoration.ITALIC, false));
        availableSkillPointsMeta.lore(
            List.of(
                ColorParser.of("<color:#a8a8a8>Points: "+ skillsPlayer.getAvailableSkillpoints()).build(),
                ColorParser.of("<color:#a8a8a8>Next Point: %s/%s".formatted(Math.round(skillsPlayer.getNextSkillpointProgress()), Math.round(expPerLevel))).build()
            )
        );
        availableSkillPoints.setItemMeta(availableSkillPointsMeta);

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.displayName(ColorParser.of("<red>Close").build().decoration(TextDecoration.ITALIC, false));
        exit.setItemMeta(exitMeta);

        ItemStack optionsButton = new ItemStack(Material.COMPARATOR);
        ItemMeta optionsMeta = optionsButton.getItemMeta();
        optionsMeta.displayName(ColorParser.of("<aqua>Options").build().decoration(TextDecoration.ITALIC, false)); // TODO Replace with custom rgb color
        optionsMeta.lore(
            List.of(
                ColorParser.of("<color:#a8a8a8>Open the options menu").build()
            )
        );
        optionsButton.setItemMeta(optionsMeta);

        ItemStack border = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta borderMeta = border.getItemMeta();
        borderMeta.displayName(Component.text(""));
        border.setItemMeta(borderMeta);

        gui.getFiller().fill(ItemBuilder.from(border).asGuiItem());

        gui.setItem(2, 5, ItemBuilder.from(availableSkillPoints).asGuiItem());

        gui.setItem(6, 1, ItemBuilder.from(optionsButton).asGuiItem(event -> GuiHelper.openOptionsGui(player)));
        gui.setItem(6, 9, ItemBuilder.from(exit).asGuiItem(event -> gui.close(player)));

        // Open skill trees
        gui.setItem(4, 3, ItemBuilder.from(skillsManager.skillCategories.get(1).getIcon()).asGuiItem(event -> GuiHelper.openSkillGui(player, 1, 1)));
        gui.setItem(4, 5, ItemBuilder.from(skillsManager.skillCategories.get(2).getIcon()).asGuiItem(event -> GuiHelper.openSkillGui(player, 2, 1)));
        gui.setItem(4, 7, ItemBuilder.from(skillsManager.skillCategories.get(3).getIcon()).asGuiItem(event -> GuiHelper.openSkillGui(player, 3, 1)));
    }

}
