package io.github.alathra.alathraskills.skills.woodcutting.util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class PreciseChop {

    public static void run(Block block, int skillLevel) {
        if (Math.random() > getChance(skillLevel))
            return;

        // Drop additional block, effectively simulating double drops
        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(block.getType(), 1));
    }

    private static double getChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> 0.05;
            case 2 -> 0.10;
            case 3 -> 0.20;
            case 4 -> 0.35;
            case 5 -> 0.55;
            case 6 -> 0.70;
            case 7 -> 0.85;
            case 8 -> 1.00;
            default -> 0;
        };
    }

}
