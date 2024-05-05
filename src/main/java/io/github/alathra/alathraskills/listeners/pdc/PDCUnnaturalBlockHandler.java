package io.github.alathra.alathraskills.listeners.pdc;

import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPistonExtendEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PDCUnnaturalBlockHandler implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void BlockPlaceListener(BlockPlaceEvent event) {
        if (event.isCancelled()) {
            return;
        }
        PDCUtil.setUnnnatural(event.getBlock(), true);

        // TODO: Apply to items maybe?
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void BlockBreakListener(BlockBreakEvent event) {
        if (event.isCancelled()) {
            return;
        }
        PDCUtil.clearUnnatural(event.getBlock());
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void BlockMovedByPistonListener(BlockPistonExtendEvent event) {
        if (event.isCancelled()) {
            return;
        }
        for (Block block : event.getBlocks()) {
            block = block.getRelative(event.getDirection());
            PDCUtil.setUnnnatural(block, true);
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void BlockGrownByTreeListener(StructureGrowEvent event) {
        if (event.isCancelled()) {
            return;
        }
        for (BlockState blockState : event.getBlocks()) {
            PDCUtil.clearUnnatural(blockState.getBlock());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void PDCTest(PlayerInteractEvent event) {
        if (event.isCancelled()) {
            return;
        }
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getHand() == EquipmentSlot.HAND) {
                if (event.getPlayer().getInventory().getItemInMainHand() == null || event.getPlayer().getInventory().getItemInMainHand().getType() == Material.AIR)
                    Bukkit.broadcastMessage("Is Unnatural Block: " + String.valueOf(PDCUtil.isUnnatural(event.getClickedBlock())));
            }
        }
    }
}
