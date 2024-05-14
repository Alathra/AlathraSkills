package io.github.alathra.alathraskills.listeners.skills;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.skills.woodcutting.util.*;
import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class WoodcuttingSkillsListener implements Listener {

    private SkillsPlayerManager skillsPlayerManager = AlathraSkills.getSkillsPlayerManager();

    // calls "Precise Chop", "Save the Trees" and "Groundskeeper"
    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (PDCUtil.isUnnatural(block))
            return;

        if (Tag.LOGS.isTagged(block.getType())) {
            PreciseChop.run(block, 7);
            OneSwing.run(player, block, 7);
            if (Tag.DIRT.isTagged(block.getRelative(BlockFace.DOWN).getType())) {
                SaveTheTrees.run(block, player);
            }
        }

    }

    // calls "Trimmer"
    @EventHandler
    public void BlockDamageListener(BlockDamageEvent event) {

        if (!Tag.LEAVES.isTagged(event.getBlock().getType())) {
            return;
        }

        if (!Tag.ITEMS_AXES.isTagged(event.getItemInHand().getType())) {
            return;
        }

        if (PDCUtil.isUnnatural(event.getBlock())) {
            return;
        }

        // run Trimmer
        Trimmer.run(event, 7);
    }

    // used to activate "One Swing"
    @EventHandler
    public void RightClickListener(PlayerInteractEvent event) {
        // event only needs to be run once
        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }
        // if no item in main hand
        if (!event.hasItem()) {
            return;
        }
        // if the item in main hand is not an axe
        if (!Tag.ITEMS_AXES.isTagged(event.getItem().getType())) {
            return;
        }
        // if not a right click, return;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            // make sure stripping logs does not activate skill
            if (event.getClickedBlock() != null) {
                if (Tag.LOGS.isTagged(event.getClickedBlock().getType())) {
                    return;
                }
            }
            OneSwing.activate(event.getPlayer(), 7);
        }
    }
}
