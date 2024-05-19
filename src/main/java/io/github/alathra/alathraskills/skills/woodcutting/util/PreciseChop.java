package io.github.alathra.alathraskills.skills.woodcutting.util;

import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import io.github.alathra.alathraskills.utility.Cfg;

public class PreciseChop {

    public static void run(Block block, int skillLevel) {
        if (Math.random() > getChance(skillLevel))
            return;

        // Drop additional block, effectively simulating double drops
        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(block.getType(), 1));
    }

    private static double getChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> Cfg.get().get("skills.woodcutting.preciseChop.chance.l1", 0.05);
            case 2 -> Cfg.get().get("skills.woodcutting.preciseChop.chance.l2", 0.10);
            case 3 -> Cfg.get().get("skills.woodcutting.preciseChop.chance.l3", 0.20);
            case 4 -> Cfg.get().get("skills.woodcutting.preciseChop.chance.l4", 0.35);
            case 5 -> Cfg.get().get("skills.woodcutting.preciseChop.chance.l5", 0.55);
            case 6 -> Cfg.get().get("skills.woodcutting.preciseChop.chance.l6", 0.70);
            case 7 -> Cfg.get().get("skills.woodcutting.preciseChop.chance.l7", 0.85);
            case 8 -> Cfg.get().get("skills.woodcutting.preciseChop.chance.l8", 1.00);
            default -> 0;
        };
    }

}
