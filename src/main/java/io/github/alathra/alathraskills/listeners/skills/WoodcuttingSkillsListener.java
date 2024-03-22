package io.github.alathra.alathraskills.listeners.skills;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.skills.woodcutting.*;
import io.github.alathra.alathraskills.skills.woodcutting.util.OneSwing;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.Recipe;

public class WoodcuttingSkillsListener implements Listener {

    private SkillsManager skillsManager = AlathraSkills.getSkillsManager();
    private SkillsPlayerManager skillsPlayerManager = AlathraSkills.getSkillsPlayerManager();

    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();
        Player player = event.getPlayer();

        SkillsPlayer skillsPlayer = skillsPlayerManager.getSkillPlayers().get(player.getUniqueId());

        // TODO Check if block was placed by player

        if (Tag.LOGS.isTagged(material)) {
            if (skillsPlayer.getPlayerSkills().get(301))
                SaveTheTreesSkill.runSaveTheTreesSkill(block);

            if (skillsPlayer.getPlayerSkills().get(302)) {
                if (skillsPlayer.getPlayerSkills().get(303)) {
                    PreciseChopTwoSkill.runPreciseChopTwoSkill(block);
                } else {
                    PreciseChopOneSkill.runPreciseChopOneSkill(block);
                }
            }

            if (skillsPlayer.getPlayerSkills().get(305)) {
                if (skillsManager.oneSwingActive(player)) {
                    OneSwingOneSkill.runOneSwingOneSkill(player, block);
                }

                if (skillsManager.oneSwingRunning(player)) {
                    OneSwing.fellTree(block);
                }
            }
        }

        if (Tag.LEAVES.isTagged(material)) {
            if (skillsPlayer.getPlayerSkills().get(304)) {
                if (skillsPlayer.getPlayerSkills().get(307)) {
                    TrimmerTwoSkill.runTrimmerTwoSkill(block);
                } else {
                    TrimmerOneSkill.runTrimmerOneSkill(block);
                }
            }
        }
    }


    @EventHandler
    public void BlockDamageListener(BlockDamageEvent event) {
        SkillsPlayer skillsPlayer = skillsPlayerManager.getSkillPlayers().get(event.getPlayer().getUniqueId());

        if (!Tag.LEAVES.isTagged(event.getBlock().getType()))
            return;

        if (!Tag.ITEMS_AXES.isTagged(event.getItemInHand().getType()))
            return;

        if (!skillsPlayer.getPlayerSkills().get(304))
            return;

        event.setInstaBreak(true);
    }

    @EventHandler
    public void RightClickListener(PlayerInteractEvent event) {
        SkillsPlayer skillsPlayer = skillsPlayerManager.getSkillPlayers().get(event.getPlayer().getUniqueId());

        if (event.getHand() == EquipmentSlot.OFF_HAND)
            return;

        if (!event.hasItem())
            return;

        if (!Tag.ITEMS_AXES.isTagged(event.getMaterial()))
            return;

        // Return if right click logs
        if (Tag.LOGS.isTagged(event.getClickedBlock().getType()))
            return;

        Player player = event.getPlayer();

        if (skillsPlayer.getPlayerSkills().get(305)) {
            if (OneSwing.hasOneSwingCooldown(player)) {
                player.sendActionBar(ColorParser.of("<dark_red>One Swing isn't ready yet. Cooldown remaining: " + OneSwing.getRemainingCooldown(player) + " seconds.").build());
                return;
            }
            OneSwingOneSkill.readyOneSwingOneSkill(player);
        }

    }

    @EventHandler
    public void CraftingListener(CraftItemEvent event) {
        Player player = (Player) event.getViewers().get(0);
        SkillsPlayer skillsPlayer = skillsPlayerManager.getSkillPlayers().get(player.getUniqueId());

        Recipe recipe = event.getRecipe();
        Material material = recipe.getResult().getType();

        if(!Tag.PLANKS.isTagged(material))
            return;

        if (skillsPlayer.getPlayerSkills().get(306)) {
            player.getInventory().addItem(new ItemStack(material, 2));
        }
    }
}
