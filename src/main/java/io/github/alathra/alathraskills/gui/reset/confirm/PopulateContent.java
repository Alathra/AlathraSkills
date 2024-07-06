package io.github.alathra.alathraskills.gui.reset.confirm;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.gui.GuiHelper;
import io.github.alathra.alathraskills.gui.GuiItemHelper;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.List;

public class PopulateContent {

    public static void populateResetProgressContent(Gui gui, Player player, int cost, double expRetained) {
        SkillsPlayer skillsPlayer = AlathraSkills.getSkillsPlayerManager().getSkillsPlayer(player);
        if (skillsPlayer == null)
            return;

        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwningPlayer(player);
        skullMeta.displayName(ColorParser.of(GuiHelper.COMMON_TITLE + "Reset progress").build());
        skullMeta.lore(List.of(ColorParser.of(GuiHelper.LORETEXT + "Cost: $%s".formatted(cost)).build(),
            ColorParser.of(GuiHelper.LORETEXT + "Reset all of your skills and keep %s".formatted(Math.round(expRetained * 100)) + "% of your experience").build(),
            ColorParser.of(GuiHelper.NEGATIVE + "<bold>This action is permanent and cannot be undone.").build()));
        playerHead.setItemMeta(skullMeta);

        gui.setItem(1, 3, ItemBuilder.from(GuiItemHelper.denyItem).asGuiItem(event -> GuiHelper.openMainGui(player)));
        gui.setItem(1, 5, ItemBuilder.from(playerHead).asGuiItem());
        gui.setItem(1, 7, ItemBuilder.from(GuiItemHelper.confirmItem).asGuiItem(event -> {
            boolean reset = skillsPlayer.resetProgress(cost, expRetained);
            if (reset) GuiHelper.openMainGui(player);
            else {
                player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.AMBIENT, 1, 1);
                player.sendMessage(ColorParser.of("<dark_grey>[<color:#00B300>AlathraSkills<dark_grey>]<red> Resetting costs $" + cost + ".").build());
            }
        }));
    }

    public static void populateResetProgressFreeContent(Gui gui, Player player) {
        SkillsPlayer skillsPlayer = AlathraSkills.getSkillsPlayerManager().getSkillsPlayer(player);
        if (skillsPlayer == null)
            return;

        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwningPlayer(player);
        skullMeta.displayName(ColorParser.of(GuiHelper.COMMON_TITLE + "Reset skills").build());
        skullMeta.lore(List.of(ColorParser.of(GuiHelper.LORETEXT + "Reset all of your progress.").build(),
            ColorParser.of(GuiHelper.NEGATIVE + "<bold>This action is permanent and cannot be undone.").build()));
        playerHead.setItemMeta(skullMeta);

        gui.setItem(1, 3, ItemBuilder.from(GuiItemHelper.denyItem).asGuiItem(event -> GuiHelper.openResetGui(player)));
        gui.setItem(1, 5, ItemBuilder.from(playerHead).asGuiItem());
        gui.setItem(1, 7, ItemBuilder.from(GuiItemHelper.confirmItem).asGuiItem(event -> {
            boolean reset = skillsPlayer.resetProgress(0, 0.0f);
            if (reset) GuiHelper.openMainGui(player);
            else {
                player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.AMBIENT, 1, 1);
                player.sendMessage(ColorParser.of("<dark_grey>[<color:#00B300>AlathraSkills<dark_grey>]<red> Something went wrong. If this issue persists, please submit a bug report.").build());
                gui.close(player);
            }
        }));
    }

    public static void populateRefundSkillContent(Gui gui, Player player, Skill skill) {
        SkillsPlayer skillsPlayer = AlathraSkills.getSkillsPlayerManager().getSkillsPlayer(player);
        if (skillsPlayer == null)
            return;

        ItemStack skillStack = new ItemStack(skill.getIcon());
        ItemMeta skillMeta = skillStack.getItemMeta();
        skillMeta.displayName(ColorParser.of(GuiHelper.POSITIVE + "Refund: ").build().append(skillMeta.displayName()));
        skillStack.setItemMeta(skillMeta);

        gui.setItem(1, 3, ItemBuilder.from(GuiItemHelper.denyItem).asGuiItem(event -> GuiHelper.openResetGui(player)));
        gui.setItem(1, 5, ItemBuilder.from(skillStack).asGuiItem());
        gui.setItem(1, 7, ItemBuilder.from(GuiItemHelper.confirmItem).asGuiItem(event -> {
            skillsPlayer.refundLatestSkill();
            GuiHelper.openMainGui(player);
        }));
    }

}
