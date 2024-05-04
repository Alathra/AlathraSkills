package io.github.alathra.alathraskills.gui.skill;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PopulateContent {

    public static void populateContent(Gui gui, Player player) {
        SkillsManager skillsManager = AlathraSkills.getSkillsManager();

        gui.setItem(1, 4, ItemBuilder.from(skillsManager.skillCategories.get(1).getIcon()).asGuiItem()); // TODO: open skill guis
        gui.setItem(1, 5, ItemBuilder.from(skillsManager.skillCategories.get(2).getIcon()).asGuiItem());
        gui.setItem(1, 6, ItemBuilder.from(skillsManager.skillCategories.get(3).getIcon()).asGuiItem());
    }

}
