package io.github.alathra.alathraskills.gui.reset;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.triumphteam.gui.builder.item.ItemBuilder;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.gui.GuiHelper;
import io.github.alathra.alathraskills.skills.Skill;
import io.github.alathra.alathraskills.utility.Cfg;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.time.Duration;
import java.time.Instant;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class PopulateContent {


    public static void populateContent(Gui gui, Player player) {
        SkillsManager skillsManager = AlathraSkills.getSkillsManager();
        SkillsPlayer skillsPlayer = AlathraSkills.getSkillsPlayerManager().getSkillsPlayer(player);
        if (skillsPlayer == null)
            return;

        ItemStack resetFree;

        ItemStack resetCost;

        if (!skillsPlayer.isOnCooldown()) {
            resetFree = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
            ItemMeta resetFreeMeta = resetFree.getItemMeta();
            resetFreeMeta.displayName(ColorParser.of(GuiHelper.POSITIVE + "Reset progress").build());
            resetFreeMeta.lore(List.of(ColorParser.of(GuiHelper.LORETEXT + "Cost: Free").build(),
                ColorParser.of(GuiHelper.LORETEXT + "Resets all of your progress.").build(),
                ColorParser.of(GuiHelper.NEGATIVE + "<bold>This action is permanent and cannot be undone.").build()));
            resetFree.setItemMeta(resetFreeMeta);

            int cost = Cfg.get().getInt("skills.resetCost");
            double expRetained = Cfg.get().getDouble("skills.resetRetain");

            resetCost = new ItemStack(Material.LIME_STAINED_GLASS_PANE);
            ItemMeta resetCostMeta = resetCost.getItemMeta();
            resetCostMeta.displayName(ColorParser.of(GuiHelper.POSITIVE + "Reset progress").build());
            resetCostMeta.lore(List.of(ColorParser.of(GuiHelper.LORETEXT + "Cost: $%s".formatted(cost)).build(),
                ColorParser.of(GuiHelper.LORETEXT + "Resets all of your skills, but you keep %s".formatted(Math.round(expRetained * 100)) + "% of your experience.").build(),
                ColorParser.of(GuiHelper.NEGATIVE + "<bold>This action is permanent and cannot be undone.").build()
            ));
            resetCost.setItemMeta(resetCostMeta);

            gui.setItem(2, 3, ItemBuilder.from(resetFree).asGuiItem(event -> GuiHelper.openResetProgressFreeConfirmGui(player)));
            gui.setItem(2, 5, ItemBuilder.from(resetCost).asGuiItem(event -> GuiHelper.openResetProgressConfirmGui(player, cost, expRetained)));
        } else {
            long cooldownRemaining = Duration.between(Instant.now(), skillsPlayer.getResetCooldown()).getSeconds();
            long hours = TimeUnit.SECONDS.toHours(cooldownRemaining);
            long minutes = TimeUnit.SECONDS.toMinutes(cooldownRemaining) - (TimeUnit.SECONDS.toHours(cooldownRemaining) * 60);
            long seconds = TimeUnit.SECONDS.toSeconds(cooldownRemaining) - (TimeUnit.SECONDS.toMinutes(cooldownRemaining) * 60);
            String cooldownString;

            if (hours > 0 && minutes > 29) cooldownString = "approx. " + (hours + 1) + " hours";
            else if (hours > 0) cooldownString = "approx. " + hours + " hours";
            else if (minutes > 0) cooldownString = "approx. " + minutes + " minutes";
            else cooldownString = "less than a minute";

            resetFree = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            ItemMeta resetFreeMeta = resetFree.getItemMeta();
            resetFreeMeta.displayName(ColorParser.of(GuiHelper.NEGATIVE + "Reset progress").build());
            resetFreeMeta.lore(List.of(ColorParser.of(GuiHelper.LORETEXT + "Cost: Free").build(),
                ColorParser.of(GuiHelper.LORETEXT + "Remaining cooldown: " + cooldownString).build()));
            resetFree.setItemMeta(resetFreeMeta);

            resetCost = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            ItemMeta resetCostMeta = resetCost.getItemMeta();
            resetCostMeta.displayName(ColorParser.of(GuiHelper.NEGATIVE + "Reset progress").build());
            resetCostMeta.lore(List.of(ColorParser.of(GuiHelper.LORETEXT + "Cost: $15,000").build(),
                ColorParser.of(GuiHelper.LORETEXT + "Remaining cooldown: " + cooldownString).build()));
            resetCost.setItemMeta(resetCostMeta);

            gui.setItem(2, 3, ItemBuilder.from(resetFree).asGuiItem(event -> player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.AMBIENT, 1, 1)));
            gui.setItem(2, 5, ItemBuilder.from(resetCost).asGuiItem(event -> player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.AMBIENT, 1, 1)));
        }


        int latestSkillId = skillsPlayer.getLatestSkillUnlocked();
        ItemStack latestSkill;
        // If no latest skill
        if (latestSkillId == -1 || latestSkillId == 0) {
            latestSkill = new ItemStack(Material.RED_STAINED_GLASS_PANE);
            ItemMeta latestSkillMeta = latestSkill.getItemMeta();
            latestSkillMeta.displayName(ColorParser.of(GuiHelper.NEGATIVE + "Refund latest skill").build());
            latestSkillMeta.lore(List.of(ColorParser.of(GuiHelper.LORETEXT + "No skill available for refund.").build(),
                ColorParser.of(GuiHelper.LORETEXT + "This is either because you haven't purchased a skill,").build(),
                ColorParser.of(GuiHelper.LORETEXT + "or because you've already refunded a skill.").build()));
            latestSkill.setItemMeta(latestSkillMeta);

            gui.setItem(2, 7, ItemBuilder.from(latestSkill).asGuiItem(event -> player.playSound(player, Sound.ENTITY_ENDERMAN_TELEPORT, SoundCategory.AMBIENT, 1, 1)));
        } else {
            latestSkill = new ItemStack(skillsManager.getSkill(latestSkillId).getIcon());
            ItemMeta latestSkillMeta = latestSkill.getItemMeta();
            Component displayName = latestSkillMeta.displayName();
            latestSkillMeta.displayName(ColorParser.of(GuiHelper.POSITIVE + "Refund: ").build().append(displayName));
            latestSkill.setItemMeta(latestSkillMeta);

            Skill skill = skillsManager.getSkill(latestSkillId);

            gui.setItem(2, 7, ItemBuilder.from(latestSkill).asGuiItem(event -> GuiHelper.openRefundSkillGui(player, skill)));
        }
    }
}
