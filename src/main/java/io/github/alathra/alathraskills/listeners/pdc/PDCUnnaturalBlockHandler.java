package io.github.alathra.alathraskills.listeners.pdc;

import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PDCUnnaturalBlockHandler implements Listener {
    @EventHandler
    public void BlockPlaceListener(BlockPlaceEvent event) {
        PDCUtil.setUnnnatural(event.getBlock(), true);

        // TODO: Apply to items maybe?
    }

    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {
        PDCUtil.clearUnnatural(event.getBlock());
    }
}
