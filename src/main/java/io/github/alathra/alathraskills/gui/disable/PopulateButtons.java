package io.github.alathra.alathraskills.gui.disable;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.gui.GuiHelper;
import io.github.alathra.alathraskills.gui.GuiItemHelper;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PopulateButtons {

    public static void populateButtons(Gui gui, Player p) {
        gui.getFiller().fill(ItemBuilder.from(GuiItemHelper.borderItem).asGuiItem());

        ItemStack back = new ItemStack(Material.PAPER);
        ItemMeta backMeta = back.getItemMeta();
        backMeta.displayName(ColorParser.of(GuiHelper.NEGATIVE + "Back").build());
        back.setItemMeta(backMeta);
        gui.setItem(6, 1, ItemBuilder.from(back).asGuiItem(event -> GuiHelper.openOptionsGui(p.getPlayer())));

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.displayName(ColorParser.of(GuiHelper.NEGATIVE + "Close").build());
        exit.setItemMeta(exitMeta);
        gui.setItem(6, 9, ItemBuilder.from(exit).asGuiItem(event -> gui.close(p)));
    }

}
