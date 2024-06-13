package io.github.alathra.alathraskills.gui.reset;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.gui.GuiHelper;
import io.github.alathra.alathraskills.utility.Logger;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class PopulateContent {

    public static void populateContent(Gui gui, Player player) {
        SkillsManager skillsManager = AlathraSkills.getSkillsManager();

        ItemStack resetFree = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta resetFreeMeta = resetFree.getItemMeta();
        resetFreeMeta.displayName(ColorParser.of("<green><bold>Reset progress").build());
        resetFreeMeta.lore(List.of(ColorParser.of("<green>Cost: Free").build(),
            ColorParser.of("<red>Resets all of your skills and experience.").build(),
            ColorParser.of("<dark_red>This action is permanent and cannot be undone.").build()));
        resetFree.setItemMeta(resetFreeMeta);

        ItemStack resetCost = new ItemStack(Material.EMERALD_BLOCK);
        ItemMeta resetCostMeta = resetCost.getItemMeta();
        resetCostMeta.displayName(ColorParser.of("<green><bold>Reset progress").build());
        resetCostMeta.lore(List.of(ColorParser.of("<green>Cost: $15,000").build(),
            ColorParser.of("<red>Resets all of your skills, but you keep 25% of your experience.").build(),
            ColorParser.of("<dark_red>This action is permanent and cannot be undone.").build()));
        resetCost.setItemMeta(resetCostMeta);

        SkillsPlayer skillsPlayer = SkillsPlayerManager.getSkillsPlayer(player);
        int latestSkillId = skillsPlayer.getLatestSkillUnlocked();
        ItemStack latestSkill;
        // If no latest skill
        if (latestSkillId == -1 || latestSkillId == 0) {
            latestSkill = new ItemStack(Material.REDSTONE_BLOCK);
            ItemMeta latestSkillMeta = latestSkill.getItemMeta();
            latestSkillMeta.displayName(ColorParser.of("<green><bold>Refund latest skill").build());
            latestSkillMeta.lore(List.of(ColorParser.of("<red>No skill available for refund.").build(),
                ColorParser.of("<red>This is either because you haven't purchased a skill,").build(),
                ColorParser.of("<red>or because you've already refunded a skill.").build()));
            latestSkill.setItemMeta(latestSkillMeta);
        } else {
            latestSkill = skillsManager.getSkill(latestSkillId).getIcon();
            ItemMeta latestSkillMeta = latestSkill.getItemMeta();
            Component displayName = latestSkillMeta.displayName();
            latestSkillMeta.displayName(ColorParser.of("<green><bold>Refund: ").build().append(displayName));
            latestSkill.setItemMeta(latestSkillMeta);
        }

        gui.setItem(2, 3, ItemBuilder.from(resetFree).asGuiItem());
        gui.setItem(2, 5, ItemBuilder.from(resetCost).asGuiItem());
        gui.setItem(2, 7, ItemBuilder.from(latestSkill).asGuiItem());
    }
}
