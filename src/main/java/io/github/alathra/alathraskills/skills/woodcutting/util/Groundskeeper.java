package io.github.alathra.alathraskills.skills.woodcutting.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Groundskeeper {

    public static void runGroundskeeperSkill(BlockBreakEvent event, int x1, int y1, int z1, double chance, int leaves) {
        Block block = event.getBlock();
        Material material = block.getType();
        Location location = block.getLocation();

        List<Block> blockList = new ArrayList<>();

        int x2, y2, z2 = 0;
        for (x2 = 0; x2 < x1; x2++) {
            for (y2 = 0; y2 < y1; y2++) {
                for (z2 = 0; z2 < z1; z2++) {
                    if (x2 == 0 || y2 == 0 || z2 == 0)
                        continue;

                    blockList.add(block.getRelative(x2, y2, z2));
                }
                blockList.add(block.getRelative(x2, y2, z2));
            }
            blockList.add(block.getRelative(x2, y2, z2));
        }

        blockList.forEach(Block::breakNaturally);

        if (Math.random() <= chance)
            location.getWorld().dropItemNaturally(location, new ItemStack(material, leaves));
    }

}
