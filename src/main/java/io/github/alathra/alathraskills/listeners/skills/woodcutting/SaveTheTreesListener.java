package io.github.alathra.alathraskills.listeners.skills.woodcutting;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class SaveTheTreesListener implements Listener {

    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (!Tag.LOGS.isTagged(block.getType()))
            return;

        // TODO Check if block was placed by player

        Material blockUnder = block.getRelative(0, -1, 0).getType();
        if (!Tag.DIRT.isTagged(blockUnder))
            return;

        String blockString = block.getType().toString();

        // Removes "STRIPPED_".
        if (blockString.contains("STRIPPED"))
            blockString = blockString.substring(9);

        String[] materialArray = blockString.split("_");

        // Handles dark oak.
        if (materialArray.length > 2 && materialArray[2] != null)
            materialArray[0] = materialArray[0].concat("_").concat(materialArray[1]);

        String sapling = materialArray[0].concat("_SAPLING");

        block.setType(Material.getMaterial(sapling));
    }

}
