package io.github.alathra.alathraskills.gui.skill;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.gui.GuiHelper;
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
        returnMeta.displayName(ColorParser.of("<red>Previous page").build().decoration(TextDecoration.ITALIC, false));
        returnStack.setItemMeta(returnMeta);

        ItemStack nextStack = new ItemStack(Material.ARROW);
        ItemMeta nextMeta = returnStack.getItemMeta();
        nextMeta.displayName(ColorParser.of("<green>Next page").build().decoration(TextDecoration.ITALIC, false));
        nextStack.setItemMeta(nextMeta);

        ItemStack openSkillTrees = new ItemStack(Material.PAPER);
        ItemMeta openSkillTreesMeta = openSkillTrees.getItemMeta();
        openSkillTreesMeta.displayName(ColorParser.of("<red>Back").build().decoration(TextDecoration.ITALIC, false));
        openSkillTrees.setItemMeta(openSkillTreesMeta);

        ItemStack exit = new ItemStack(Material.BARRIER);
        ItemMeta exitMeta = exit.getItemMeta();
        exitMeta.displayName(ColorParser.of("<red>Close").build().decoration(TextDecoration.ITALIC, false));
        exit.setItemMeta(exitMeta);

        ItemStack availableSkillPoints = new ItemStack(Material.END_CRYSTAL);
        ItemMeta availableSkillPointsMeta = availableSkillPoints.getItemMeta();
        availableSkillPointsMeta.displayName(ColorParser.of(GuiHelper.EXPERIENCE_GRADIENT + "Skill Points").build().decoration(TextDecoration.ITALIC, false));
        availableSkillPointsMeta.lore(
            List.of(
                ColorParser.of("<color:#a8a8a8>Points: "+ skillsPlayer.getAvailableSkillpoints()).build()/*,
                ColorParser.of("<color:#a8a8a8>Next Level: %s/%s".formatted(remainingExp, totalExp)).build()*/ // TODO Implement methods to show these
            )
        );
        availableSkillPoints.setItemMeta(availableSkillPointsMeta);

        ItemStack border = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta borderMeta = border.getItemMeta();
        borderMeta.displayName(Component.text(""));
        border.setItemMeta(borderMeta);

        switch (page) {
            case 1 -> {
                gui.getFiller().fill(ItemBuilder.from(border).asGuiItem());

                gui.setItem(6, 1, ItemBuilder.from(openSkillTrees).asGuiItem(event -> GuiHelper.openMainGui(player)));
                gui.setItem(6, 5, ItemBuilder.from(availableSkillPoints).asGuiItem());
                gui.setItem(6, 6, ItemBuilder.from(nextStack).asGuiItem(event -> GuiHelper.openSkillGui(player, skillCategoryId, page + 1)));
                gui.setItem(6, 9, ItemBuilder.from(exit).asGuiItem(event -> gui.close(player)));
            }
            case 2, 3 -> {
                gui.getFiller().fill(ItemBuilder.from(border).asGuiItem());

                gui.setItem(6, 1, ItemBuilder.from(openSkillTrees).asGuiItem(event -> GuiHelper.openMainGui(player)));
                gui.setItem(6, 4, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillGui(player, skillCategoryId, page - 1)));
                gui.setItem(6, 5, ItemBuilder.from(availableSkillPoints).asGuiItem());
                gui.setItem(6, 6, ItemBuilder.from(nextStack).asGuiItem(event -> GuiHelper.openSkillGui(player, skillCategoryId, page + 1)));
                gui.setItem(6, 9, ItemBuilder.from(exit).asGuiItem(event -> gui.close(player)));
            }
            case 4 -> {
                gui.getFiller().fill(ItemBuilder.from(border).asGuiItem());

                gui.setItem(6, 1, ItemBuilder.from(openSkillTrees).asGuiItem(event -> GuiHelper.openMainGui(player)));
                gui.setItem(6, 4, ItemBuilder.from(returnStack).asGuiItem(event -> GuiHelper.openSkillGui(player, skillCategoryId, page - 1)));
                gui.setItem(6, 5, ItemBuilder.from(availableSkillPoints).asGuiItem());
                gui.setItem(6, 9, ItemBuilder.from(exit).asGuiItem(event -> gui.close(player)));
            }
        }
    }
}
