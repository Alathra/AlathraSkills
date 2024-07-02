package io.github.alathra.alathraskills.gui.disable;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.gui.GuiHelper;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class PopulateContent {

    private static final int[] levelOnePassiveFarmingSkillIds = new int[]{101, 102, 103, 105};
    private static final int[] levelOnePassiveMiningSkillIds = new int[]{201, 202, 203, 2111};
    private static final int[] levelOnePassiveWoodcuttingSkillIds = new int[]{301, 302, 304, 306};

    public static void populateContent(Gui gui, Player p) {
        SkillsPlayer sp = SkillsPlayerManager.getSkillsPlayer(p);

        int row = 2;
        for (int id : levelOnePassiveFarmingSkillIds) {
            boolean hasSkill = sp.playerHasSkill(id);
            if (!hasSkill) {
                continue;
            }

            boolean isEnabled = sp.isSkillEnabled(id);
            gui.setItem(row, 2, ItemBuilder.from(getIcon(id, isEnabled)).asGuiItem(event -> GuiHelper.toggleSkillEnabled(gui, p, sp, id)));
            row++;
        }

        row = 2;
        for (int id : levelOnePassiveMiningSkillIds) {
            boolean hasSkill = sp.playerHasSkill(id);
            if (!hasSkill) {
                continue;
            }

            boolean isEnabled = sp.isSkillEnabled(id);
            gui.setItem(row, 5, ItemBuilder.from(getIcon(id, isEnabled)).asGuiItem(event -> GuiHelper.toggleSkillEnabled(gui, p, sp, id)));
            row++;
        }

        row = 2;
        for (int id : levelOnePassiveWoodcuttingSkillIds) {
            boolean hasSkill = sp.playerHasSkill(id);
            if (!hasSkill) {
                continue;
            }

            boolean isEnabled = sp.isSkillEnabled(id);
            gui.setItem(row, 8, ItemBuilder.from(getIcon(id, isEnabled)).asGuiItem(event -> GuiHelper.toggleSkillEnabled(gui, p, sp, id)));
            row++;
        }
    }

    private static ItemStack getIcon(int id, boolean isEnabled) {
        ItemStack icon = new ItemStack(AlathraSkills.getSkillsManager().getSkill(id).getIcon());
        ItemMeta meta = icon.getItemMeta();

        if (!isEnabled) {
            icon.setType(Material.RED_STAINED_GLASS);
            ItemMeta newMeta = icon.getItemMeta();

            List<Component> loreList = meta.lore();
            loreList.remove(0);
            loreList.remove(loreList.size() - 1);
            newMeta.lore(loreList);

            Component displayName = meta.displayName();
            String name = PlainTextComponentSerializer.plainText().serialize(displayName).replace(" 1", "");
            displayName = ColorParser.of(getPrefix(isEnabled) + name).build();
            newMeta.displayName(displayName);

            icon.setItemMeta(newMeta);

            return icon;
        } else {
            List<Component> loreList = meta.lore();
            loreList.remove(0);
            loreList.remove(loreList.size() - 1);
            meta.lore(loreList);

            Component displayName = meta.displayName();
            String name = PlainTextComponentSerializer.plainText().serialize(displayName).replace(" 1", "");
            displayName = ColorParser.of(getPrefix(isEnabled) + name).build();
            meta.displayName(displayName);

            icon.setItemMeta(meta);

            return icon;
        }
    }

    private static String getPrefix(boolean isEnabled) {
        if (isEnabled)
            return "<green><bold>Disable: ";
        else
            return "<green><bold>Enable: ";
    }

}
