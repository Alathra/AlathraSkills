package io.github.alathra.alathraskills.gui.skillcategory;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.gui.GuiHelper;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PopulateButtons {

    public static void populateButtons (Gui gui, Player player) {
        ItemStack back = new ItemStack(Material.PAPER);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.displayName(ColorParser.of("<red><bold>Return").build());
        back.setItemMeta(backMeta);
        gui.setItem(3, 1, ItemBuilder.from(back).asGuiItem(event -> {
            Gui newGui = GuiHelper.buildGui(GuiHelper.GuiType.MAIN);
            GuiHelper.populateMainGui(newGui, player);
            newGui.open(player);
        }));

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.displayName(ColorParser.of("<dark_red><bold>Exit").build());
        exit.setItemMeta(exitMeta);
        gui.setItem(3, 9, ItemBuilder.from(exit).asGuiItem(event -> gui.close(player)));
    }

}
