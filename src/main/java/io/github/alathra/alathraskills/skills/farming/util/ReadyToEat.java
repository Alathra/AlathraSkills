package io.github.alathra.alathraskills.skills.farming.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class ReadyToEat {

    // Call this on the BlockBreakEvent if wheat, carrots, potatoes or beetroot broken
    // crop is the block being broken
    public static void run(Block crop, int skillLevel) {
        if (Math.random() >= getChance(skillLevel))
            return;

        switch (crop.getType()) {
            case WHEAT, BEETROOTS:
                crop.getWorld().dropItem(crop.getLocation(), new ItemStack(Material.BREAD));
                break;
            case CARROTS:
                crop.getWorld().dropItem(crop.getLocation(), new ItemStack(Material.GOLDEN_CARROT));
                break;
            case POTATOES:
                crop.getWorld().dropItem(crop.getLocation(), new ItemStack(Material.BAKED_POTATO));
                break;
        }

    }

    private static double getChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> 0.025;
            case 2 -> 0.05;
            default -> 0;
        };
    }
}
