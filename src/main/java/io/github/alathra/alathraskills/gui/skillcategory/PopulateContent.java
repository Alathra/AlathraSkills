package io.github.alathra.alathraskills.gui.skillcategory;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.gui.GuiHelper;
import org.bukkit.entity.Player;

public class PopulateContent {

    public static void populateContent(Gui gui, Player player) {
        SkillsManager skillsManager = AlathraSkills.getSkillsManager();

        gui.setItem(2, 4, ItemBuilder.from(skillsManager.skillCategories.get(1).getIcon()).asGuiItem(event -> GuiHelper.openSkillGui(player, 1, 1)));
        gui.setItem(2, 5, ItemBuilder.from(skillsManager.skillCategories.get(2).getIcon()).asGuiItem(event -> GuiHelper.openSkillGui(player, 2, 1)));
        gui.setItem(2, 6, ItemBuilder.from(skillsManager.skillCategories.get(3).getIcon()).asGuiItem(event -> GuiHelper.openSkillGui(player, 3, 1)));
    }

}
