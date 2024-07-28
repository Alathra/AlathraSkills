package io.github.alathra.alathraskills.skills.alchemy.util;

import io.github.alathra.alathraskills.AlathraSkills;
import org.bukkit.Bukkit;
import org.bukkit.block.BrewingStand;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;

public class AlchemicalRecycling {

    private final int MAX_LEVEL = 5; // ARBITRARY
    private static AlathraSkills plugin;

    public static void run(BrewEvent event, int skillLevel) {
        if (plugin == null) {
            plugin = AlathraSkills.getInstance();
        }

        ItemStack currentFuel = event.getContents().getFuel();
        ItemStack currentIngredient = event.getContents().getIngredient();
        BrewingStand brewingStand = (BrewingStand) event.getBlock().getState();

        // If fuel has run out (confusingly reset to 20)
        if (event.getFuelLevel() == 20) {
            recycleFuel(brewingStand, currentFuel, skillLevel);
        }

        if (currentIngredient != null) {
            recycleIngredient(brewingStand, currentIngredient, skillLevel);
        }

    }

    private static void recycleFuel(BrewingStand brewingStand, ItemStack currentFuel, int skillLevel) {
        // Run at next tick
        Bukkit.getScheduler().runTask(plugin, () -> {
            if (Math.random() <= getFuelChance(skillLevel)) {
                brewingStand.getInventory().setItem(4, currentFuel.add(1));
            }
        });
    }

    private static void recycleIngredient(BrewingStand brewingStand, ItemStack currentIngredient, int skillLevel) {
        // Run at next tick
        Bukkit.getScheduler().runTask(plugin, () -> {
            if (Math.random() <= getIngredientChance(skillLevel)) {
                brewingStand.getInventory().setItem(3, currentIngredient.add(1));
            }
        });
    }

    private static double getFuelChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> 0.1;
            case 2 -> 0.2;
            case 3 -> 0.3;
            case 4 -> 0.4;
            case 5 -> 1;
            default -> 0;
        };
    }

    private static double getIngredientChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> 0.1;
            case 2 -> 0.2;
            case 3 -> 0.3;
            case 4 -> 0.4;
            case 5 -> 1;
            default -> 0;
        };
    }

}
