package io.github.alathra.alathraskills.gui.skill;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.gui.GuiHelper;
import io.github.alathra.alathraskills.gui.GuiItemHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PopulateButtons {

    public static void populateButtons(Gui gui, Player player, int skillCategoryId, int page) {

        SkillsPlayer skillsPlayer = AlathraSkills.getSkillsPlayerManager().getSkillsPlayer(player);
        if (skillsPlayer == null)
            return;

        ItemStack returnStack = new ItemStack(Material.ARROW);
        ItemMeta returnMeta = returnStack.getItemMeta();
        returnMeta.displayName(ColorParser.of(GuiHelper.NEGATIVE + "Previous page").build().decoration(TextDecoration.ITALIC, false));
        returnStack.setItemMeta(returnMeta);

        ItemStack nextStack = new ItemStack(Material.ARROW);
        ItemMeta nextMeta = returnStack.getItemMeta();
        nextMeta.displayName(ColorParser.of(GuiHelper.POSITIVE + "Next page").build().decoration(TextDecoration.ITALIC, false));
        nextStack.setItemMeta(nextMeta);

        gui.getFiller().fill(ItemBuilder.from(GuiItemHelper.borderItem).asGuiItem());

        gui.setItem(6, 1, ItemBuilder.from(GuiItemHelper.backItem).asGuiItem(event -> GuiHelper.openMainGui(player)));
        gui.setItem(6, 5, ItemBuilder.from(GuiItemHelper.skillpointsItem(player)).asGuiItem());
        gui.setItem(6, 9, ItemBuilder.from(GuiItemHelper.exitItem).asGuiItem(event -> gui.close(player)));

        switch (page) {
            case 1 -> {
                gui.setItem(6, 6, ItemBuilder.from(nextStack).asGuiItem(event -> GuiHelper.openSkillGui(player, skillCategoryId, page + 1)));
            }
            case 2, 3 -> {
                gui.setItem(6, 4, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillGui(player, skillCategoryId, page - 1)));
                gui.setItem(6, 6, ItemBuilder.from(nextStack).asGuiItem(event -> GuiHelper.openSkillGui(player, skillCategoryId, page + 1)));
            }
            case 4 -> {
                gui.setItem(6, 4, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillGui(player, skillCategoryId, page - 1)));
            }
        }
    }
}
