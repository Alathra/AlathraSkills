package io.github.alathra.alathraskills.skills.woodcutting.util;

import io.github.alathra.alathraskills.utility.Cfg;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class PreciseChop {

    public static int MAX_LEVEL = 8;

    public static void run(Block block, int skillLevel) {
        if (Math.random() > getChance(skillLevel))
            return;

        // Drop additional block, effectively simulating double drops
        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(block.getType(), 1));
    }

    private static double getChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> Cfg.get().getDouble("skills.woodcutting.preciseChop.chance.l1");
            case 2 -> Cfg.get().getDouble("skills.woodcutting.preciseChop.chance.l2");
            case 3 -> Cfg.get().getDouble("skills.woodcutting.preciseChop.chance.l3");
            case 4 -> Cfg.get().getDouble("skills.woodcutting.preciseChop.chance.l4");
            case 5 -> Cfg.get().getDouble("skills.woodcutting.preciseChop.chance.l5");
            case 6 -> Cfg.get().getDouble("skills.woodcutting.preciseChop.chance.l6");
            case 7 -> Cfg.get().getDouble("skills.woodcutting.preciseChop.chance.l7");
            case 8 -> Cfg.get().getDouble("skills.woodcutting.preciseChop.chance.l8");
            default -> 0;
        };
    }

}
