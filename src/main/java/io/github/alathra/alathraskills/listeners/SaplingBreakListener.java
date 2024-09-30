package io.github.alathra.alathraskills.listeners;

import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDropItemEvent;

public class SaplingBreakListener implements Listener {

    @EventHandler
    public void onSaplingBreakListener(BlockDropItemEvent e) {
        Block block = e.getBlock();

        if (!Tag.SAPLINGS.isTagged(block.getType()))
            return;

        if (!PDCUtil.isPluginPlaced(block))
            return;

        e.setDropItems(false);
    }

}
