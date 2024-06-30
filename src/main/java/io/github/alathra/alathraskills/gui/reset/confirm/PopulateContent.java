package io.github.alathra.alathraskills.gui.reset.confirm;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.gui.GuiHelper;
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

    public static void populateResetProgressContent(Gui gui, Player player, int cost, float expRetained) {
        SkillsPlayer skillsPlayer = SkillsPlayerManager.getSkillsPlayer(player);
        if (skillsPlayer == null)
            return;

        ItemStack confirm = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.displayName(ColorParser.of(GuiHelper.POSITIVE + "<bold>Confirm").build());
        confirmMeta.lore(List.of(ColorParser.of("<green>Reset all of your skills and keep 25% of your experience").build(),
            ColorParser.of("<green>Cost: $15,000").build(),
            ColorParser.of("<dark_red>This action is permanent and cannot be undone.").build()));
        confirm.setItemMeta(confirmMeta);

        ItemStack deny = new ItemStack(Material.RED_STAINED_GLASS);
        ItemMeta denyMeta = deny.getItemMeta();
        denyMeta.displayName(ColorParser.of(GuiHelper.NEGATIVE + "<bold>Cancel").build());
        deny.setItemMeta(denyMeta);

        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwningPlayer(player);
        skullMeta.displayName(ColorParser.of(GuiHelper.COMMON_TITLE + "<bold>Reset skills").build());
        playerHead.setItemMeta(skullMeta);

        gui.setItem(1, 3, ItemBuilder.from(deny).asGuiItem(event -> GuiHelper.openMainGui(player)));
        gui.setItem(1, 5, ItemBuilder.from(playerHead).asGuiItem());
        gui.setItem(1, 7, ItemBuilder.from(confirm).asGuiItem(event -> {
            boolean reset = skillsPlayer.resetProgress(cost, expRetained);
            if (reset) GuiHelper.openMainGui(player);
            else {
                player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.AMBIENT, 1, 1);
                player.sendMessage(ColorParser.of("<dark_grey>[<color:#00B300>AlathraSkills<dark_grey>]<red> Resetting costs $15,000.").build());
            }
        }));
    }

    public static void populateResetProgressFreeContent(Gui gui, Player player) {
        SkillsPlayer skillsPlayer = SkillsPlayerManager.getSkillsPlayer(player);
        if (skillsPlayer == null)
            return;

        ItemStack confirm = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.displayName(ColorParser.of(GuiHelper.POSITIVE + "<bold>Confirm").build());
        confirmMeta.lore(List.of(ColorParser.of("<green>Reset all of your skills and experience.").build(),
            ColorParser.of("<dark_red>This action is permanent and cannot be undone.").build()));
        confirm.setItemMeta(confirmMeta);

        ItemStack deny = new ItemStack(Material.RED_STAINED_GLASS);
        ItemMeta denyMeta = deny.getItemMeta();
        denyMeta.displayName(ColorParser.of(GuiHelper.NEGATIVE + "<bold>Cancel").build());
        deny.setItemMeta(denyMeta);

        ItemStack playerHead = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta skullMeta = (SkullMeta) playerHead.getItemMeta();
        skullMeta.setOwningPlayer(player);
        skullMeta.displayName(ColorParser.of(GuiHelper.COMMON_TITLE + "<bold>Reset skills").build());
        playerHead.setItemMeta(skullMeta);

        gui.setItem(1, 3, ItemBuilder.from(deny).asGuiItem(event -> GuiHelper.openMainGui(player)));
        gui.setItem(1, 5, ItemBuilder.from(playerHead).asGuiItem());
        gui.setItem(1, 7, ItemBuilder.from(confirm).asGuiItem(event -> {
            boolean reset = skillsPlayer.resetProgress(0, 0);
            if (reset) GuiHelper.openMainGui(player);
            else {
                player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.AMBIENT, 1, 1);
                player.sendMessage(ColorParser.of("<dark_grey>[<color:#00B300>AlathraSkills<dark_grey>]<red> Something went wrong. If this issue persists, please submit a bug report.").build());
                gui.close(player);
            }
        }));
    }

    public static void populateRefundSkillContent(Gui gui, Player player, Skill skill) {
        SkillsPlayer skillsPlayer = SkillsPlayerManager.getSkillsPlayer(player);
        if (skillsPlayer == null)
            return;

        ItemStack confirm = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
        ItemMeta confirmMeta = confirm.getItemMeta();
        confirmMeta.displayName(ColorParser.of(GuiHelper.POSITIVE + "<bold>Confirm").build());
        confirmMeta.lore(List.of(ColorParser.of("<green>Refund your latest unlocked skill.").build()));
        confirm.setItemMeta(confirmMeta);

        ItemStack deny = new ItemStack(Material.RED_STAINED_GLASS);
        ItemMeta denyMeta = deny.getItemMeta();
        denyMeta.displayName(ColorParser.of(GuiHelper.NEGATIVE + "<bold>Cancel").build());
        deny.setItemMeta(denyMeta);

        ItemStack skillStack = skill.getIcon();
        ItemMeta skillMeta = skillStack.getItemMeta();
        skillMeta.displayName(ColorParser.of(GuiHelper.POSITIVE + "<bold>Refund: ").build().append(skillMeta.displayName()));
        skillStack.setItemMeta(skillMeta);

        gui.setItem(1, 3, ItemBuilder.from(deny).asGuiItem(event -> GuiHelper.openMainGui(player)));
        gui.setItem(1, 5, ItemBuilder.from(skillStack).asGuiItem());
        gui.setItem(1, 7, ItemBuilder.from(confirm).asGuiItem(event -> {
            skillsPlayer.refundLatestSkill();
            GuiHelper.openMainGui(player);
        }));
    }

}
