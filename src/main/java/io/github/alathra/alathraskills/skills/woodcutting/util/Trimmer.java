package io.github.alathra.alathraskills.skills.woodcutting.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class Trimmer {

    public static void runTrimmerSkill(Block eventBlock, double chance) {
        if (Math.random() > chance)
            return;

        Location location = eventBlock.getLocation();
        World world = eventBlock.getWorld();

        world.dropItemNaturally(location, new ItemStack(Material.APPLE));
    }

}
