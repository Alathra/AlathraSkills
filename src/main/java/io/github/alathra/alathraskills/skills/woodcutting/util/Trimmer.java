package io.github.alathra.alathraskills.skills.woodcutting.util;

import org.bukkit.Material;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

public class Trimmer {

    public static void run(BlockDamageEvent event, int skillLevel) {

        if (event.isCancelled()) {
            return;
        }

        event.setInstaBreak(true);

        if (Math.random() > getChance(skillLevel))
            return;

        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.APPLE));
    }

    private static double getChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> 0.02;
            case 2 -> 0.04;
            case 3 -> 0.08;
            case 4 -> 0.10;
            case 5 -> 0.12;
            case 6 -> 0.15;
            case 7 -> 0.20;
            default -> 0;
        };
    }

}
