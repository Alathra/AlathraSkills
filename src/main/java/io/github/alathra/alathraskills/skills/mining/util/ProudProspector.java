package io.github.alathra.alathraskills.skills.mining.util;

import org.bukkit.block.Block;
import org.bukkit.event.block.BlockBreakEvent;

public class ProudProspector {

    // Called on BlockBreakEvent
    public static void run(BlockBreakEvent event, int skillLevel) {
        if (Math.random() >= getChance(skillLevel))
            return;

    }

    private static double getChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> 0.05;
            case 2 -> 0.10;
            case 3 -> 0.15;
            case 4 -> 0.20;
            case 5 -> 0.25;
            case 6 -> 0.30;
            default -> 0;
        };
    }

}
