package io.github.alathra.alathraskills.skills.farming.test;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockFertilizeEvent;

public class FertilizerTest implements Listener {

    @EventHandler
    public void blockFertilize(BlockFertilizeEvent e) {
        Bukkit.broadcastMessage("Event Fired!");
    }

}
