package io.github.alathra.alathraskills.skills.woodcutting.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class PreciseChop {

    public static void run(Block block, double chance) {
        if (Math.random() > chance)
            return;

        Material material = block.getType();
        Location location = block.getLocation();
        World world = location.getWorld();

        world.dropItemNaturally(location, new ItemStack(material));
    }

}
