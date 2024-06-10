package io.github.alathra.alathraskills.skills.farming.util;

import io.github.alathra.alathraskills.skills.farming.util.helper.FarmingData;
import io.github.alathra.alathraskills.utility.Cfg;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class GreenThumb {

    public static int MAX_LEVEL = 6;

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
            case 1 -> Double.parseDouble(Cfg.getValue("skills.farming.greenThumb.chance.l1").toString());
            case 2 -> Double.parseDouble(Cfg.getValue("skills.farming.greenThumb.chance.l2").toString());
            case 3 -> Double.parseDouble(Cfg.getValue("skills.farming.greenThumb.chance.l3").toString());
            case 4 -> Double.parseDouble(Cfg.getValue("skills.farming.greenThumb.chance.l4").toString());
            case 5 -> Double.parseDouble(Cfg.getValue("skills.farming.greenThumb.chance.l5").toString());
            case 6 -> Double.parseDouble(Cfg.getValue("skills.farming.greenThumb.chance.l6").toString());
            default -> 0;
        };
    }
}
