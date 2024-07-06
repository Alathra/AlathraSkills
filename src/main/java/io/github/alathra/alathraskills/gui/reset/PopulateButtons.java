package io.github.alathra.alathraskills.gui.reset;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.gui.GuiHelper;
import io.github.alathra.alathraskills.gui.GuiItemHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class PopulateButtons {
    public static void populateButtons(Gui gui, Player player) {
        gui.getFiller().fill(ItemBuilder.from(GuiItemHelper.borderItem).asGuiItem());
        gui.setItem(3, 1, ItemBuilder.from(GuiItemHelper.backItem).asGuiItem(event -> GuiHelper.openOptionsGui(player)));
        gui.setItem(3, 9, ItemBuilder.from(GuiItemHelper.exitItem).asGuiItem(event -> gui.close(player)));
    }
}
