package io.github.alathra.alathraskills.gui.reset;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.gui.GuiHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PopulateButtons {
    public static void populateButtons(Gui gui, Player player) {
        ItemStack border = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta borderMeta = border.getItemMeta();
        borderMeta.displayName(Component.text(""));
        border.setItemMeta(borderMeta);

        ItemStack back = new ItemStack(Material.PAPER);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.displayName(ColorParser.of("<red>Back").build().decoration(TextDecoration.ITALIC, false));
        back.setItemMeta(backMeta);

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.displayName(ColorParser.of("<red>Close").build().decoration(TextDecoration.ITALIC, false));
        exit.setItemMeta(exitMeta);

        gui.getFiller().fill(ItemBuilder.from(border).asGuiItem());
        gui.setItem(3, 1, ItemBuilder.from(back).asGuiItem(event -> GuiHelper.openOptionsGui(player)));
        gui.setItem(3, 9, ItemBuilder.from(exit).asGuiItem(event -> gui.close(player)));
    }
}
