package io.github.alathra.alathraskills.skills.alchemy.util;

import org.bukkit.event.block.BrewingStartEvent;

public class RapidInfusion {

    private final int MAX_LEVEL = 5; // ARBITRARY

    public static void run(BrewingStartEvent event, int skillLevel) {
        event.setTotalBrewTime((int) (event.getTotalBrewTime() * getTimeReduction(skillLevel)));
    }

    private static double getTimeReduction(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> 0.9;
            case 2 -> 0.8;
            case 3 -> 0.7;
            case 4 -> 0.6;
            case 5 -> 0.05;
            default -> 0;
        };
    }
}
