package io.github.alathra.alathraskills.skills.farming.util;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

public class GreenThumb {

    // Called on BlockFertilizeEvent
    public static void run(Block initialCrop, int skillLevel) {
        if (Math.random() >= getChance(skillLevel))
            return;
        switch (skillLevel) {
            case 1, 2:
                runForPlusShape(initialCrop);
                break;
            case 3, 4, 5, 6:
                runFor3by3Shape(initialCrop);
                break;

        }
    }

    private static void runForPlusShape(Block initialCrop) {
        if (FarmingData.getBonemealableCrops().contains(initialCrop.getRelative(BlockFace.NORTH).getType())) {
            initialCrop.getRelative(BlockFace.NORTH).applyBoneMeal(BlockFace.UP);
        }
        if (FarmingData.getBonemealableCrops().contains(initialCrop.getRelative(BlockFace.SOUTH).getType())) {
            initialCrop.getRelative(BlockFace.SOUTH).applyBoneMeal(BlockFace.UP);
        }
        if (FarmingData.getBonemealableCrops().contains(initialCrop.getRelative(BlockFace.EAST).getType())) {
            initialCrop.getRelative(BlockFace.EAST).applyBoneMeal(BlockFace.UP);
        }
        if (FarmingData.getBonemealableCrops().contains(initialCrop.getRelative(BlockFace.WEST).getType())) {
            initialCrop.getRelative(BlockFace.WEST).applyBoneMeal(BlockFace.UP);
        }
    }

    private static void runFor3by3Shape(Block initialCrop) {
        runForPlusShape(initialCrop);
        if (FarmingData.getBonemealableCrops().contains(initialCrop.getRelative(BlockFace.NORTH_WEST).getType())) {
            initialCrop.getRelative(BlockFace.NORTH_WEST).applyBoneMeal(BlockFace.UP);
        }
        if (FarmingData.getBonemealableCrops().contains(initialCrop.getRelative(BlockFace.NORTH_EAST).getType())) {
            initialCrop.getRelative(BlockFace.NORTH_EAST).applyBoneMeal(BlockFace.UP);
        }
        if (FarmingData.getBonemealableCrops().contains(initialCrop.getRelative(BlockFace.SOUTH_WEST).getType())) {
            initialCrop.getRelative(BlockFace.SOUTH_WEST).applyBoneMeal(BlockFace.UP);
        }
        if (FarmingData.getBonemealableCrops().contains(initialCrop.getRelative(BlockFace.SOUTH_EAST).getType())) {
            initialCrop.getRelative(BlockFace.SOUTH_EAST).applyBoneMeal(BlockFace.UP);
        }
    }

    private static double getChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> 0.10;
            case 2 -> 0.50;
            case 3 -> 0.20;
            case 4 -> 0.40;
            case 5 -> 0.60;
            case 6 -> 0.80;
            default -> 0;
        };
    }
}
