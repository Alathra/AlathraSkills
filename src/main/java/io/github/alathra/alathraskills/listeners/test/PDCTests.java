package io.github.alathra.alathraskills.listeners.test;

import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class PDCTests implements Listener {
    
    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {
        Block block = event.getBlock();
        Bukkit.broadcastMessage("Is Block Unnatural: " + String.valueOf(PDCUtil.isUnnatural(block)));
    }
}
