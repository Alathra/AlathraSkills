package io.github.alathra.alathraskills.skills.farming.util;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.inventory.ItemStack;

import io.github.alathra.alathraskills.utility.Cfg;

public class ReadyToEat {
    // TODO MAX LEVEL
    public static int MAX_LEVEL = 2;

    // Call this on the BlockBreakEvent if wheat, carrots, potatoes or beetroot broken
    // crop is the block being broken
    public static void run(Block crop, int skillLevel) {
        if (Math.random() >= getChance(skillLevel))
            return;

        // Crop is not fully grown, return
        Ageable ageable = (Ageable) crop.getBlockData();
        if (ageable.getAge() != ageable.getMaximumAge()) {
            return;
        }

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
            case 1 -> Double.parseDouble(Cfg.getValue("skills.farming.readyToEat.chance.l1").toString());
            case 2 -> Double.parseDouble(Cfg.getValue("skills.farming.readyToEat.chance.l2").toString());
            default -> 0;
        };
    }
}
