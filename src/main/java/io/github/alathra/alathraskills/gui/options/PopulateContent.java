package io.github.alathra.alathraskills.gui.options;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.gui.GuiHelper;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PopulateContent {
    public static void populateContent(Gui gui, Player player) {
        ItemStack openDisable = new ItemStack(Material.LEVER);
        ItemMeta disableMeta = openDisable.getItemMeta();
        disableMeta.displayName(ColorParser.of(GuiHelper.NEGATIVE + "Disable passive skills").build().decoration(TextDecoration.ITALIC, false));
        openDisable.setItemMeta(disableMeta);

        ItemStack resetButton = new ItemStack(Material.COMPARATOR);
        ItemMeta resetMeta = resetButton.getItemMeta();
        resetMeta.displayName(ColorParser.of(GuiHelper.NEGATIVE + "Reset options").build().decoration(TextDecoration.ITALIC, false));
        resetButton.setItemMeta(resetMeta);

        gui.setItem(2, 4, ItemBuilder.from(resetButton).asGuiItem(event -> GuiHelper.openResetGui(player)));
        gui.setItem(2, 6, ItemBuilder.from(openDisable).asGuiItem(event -> GuiHelper.openDisableSkillGui(player)));
    }

}
