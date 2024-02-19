package io.github.alathra.alathraskills.gui.skill;

import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import dev.triumphteam.gui.guis.GuiItem;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;

public class PopulateBorders {

    public static void populateBorders(Gui gui) {
        GuiItem greyGlassPane = ItemBuilder.from(Material.GRAY_STAINED_GLASS_PANE).name(Component.text("")).asGuiItem();
        gui.getFiller().fillBorder(greyGlassPane);
    }

}
