package io.github.alathra.alathraskills.gui.main;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.gui.GuiHelper;
import io.github.alathra.alathraskills.gui.GuiItemHelper;
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

        ItemStack optionsButton = new ItemStack(Material.COMPARATOR);
        ItemMeta optionsMeta = optionsButton.getItemMeta();
        optionsMeta.displayName(ColorParser.of(GuiHelper.COMMON_TITLE + "Options").build().decoration(TextDecoration.ITALIC, false));
        optionsMeta.lore(
            List.of(
                ColorParser.of(GuiHelper.LORETEXT + "Open the options menu").build()
            )
        );
        optionsButton.setItemMeta(optionsMeta);

        gui.getFiller().fill(ItemBuilder.from(GuiItemHelper.borderItem).asGuiItem());

        gui.setItem(2, 5, ItemBuilder.from(GuiItemHelper.skillpointsItem(player)).asGuiItem());

        gui.setItem(6, 1, ItemBuilder.from(optionsButton).asGuiItem(event -> GuiHelper.openOptionsGui(player)));
        gui.setItem(6, 9, ItemBuilder.from(GuiItemHelper.exitItem).asGuiItem(event -> gui.close(player)));

        // Open skill trees
        gui.setItem(4, 2, ItemBuilder.from(skillsManager.skillCategories.get(1).getIcon()).asGuiItem(event -> GuiHelper.openSkillGui(player, 1, 1)));
        gui.setItem(4, 4, ItemBuilder.from(skillsManager.skillCategories.get(2).getIcon()).asGuiItem(event -> GuiHelper.openSkillGui(player, 2, 1)));
        gui.setItem(4, 6, ItemBuilder.from(skillsManager.skillCategories.get(3).getIcon()).asGuiItem(event -> GuiHelper.openSkillGui(player, 3, 1)));
        gui.setItem(4, 8, ItemBuilder.from(skillsManager.skillCategories.get(4).getIcon()).asGuiItem(event -> GuiHelper.openSkillGui(player, 4, 1)));
    }

}
