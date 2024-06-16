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
            case 1 -> Double.parseDouble(Cfg.getValue("skills.woodcutting.preciseChop.chance.l1").toString());
            case 2 -> Double.parseDouble(Cfg.getValue("skills.woodcutting.preciseChop.chance.l2").toString());
            case 3 -> Double.parseDouble(Cfg.getValue("skills.woodcutting.preciseChop.chance.l3").toString());
            case 4 -> Double.parseDouble(Cfg.getValue("skills.woodcutting.preciseChop.chance.l4").toString());
            case 5 -> Double.parseDouble(Cfg.getValue("skills.woodcutting.preciseChop.chance.l5").toString());
            case 6 -> Double.parseDouble(Cfg.getValue("skills.woodcutting.preciseChop.chance.l6").toString());
            case 7 -> Double.parseDouble(Cfg.getValue("skills.woodcutting.preciseChop.chance.l7").toString());
            case 8 -> Double.parseDouble(Cfg.getValue("skills.woodcutting.preciseChop.chance.l8").toString());
            default -> 0;
        };
    }

}
