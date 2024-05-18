package io.github.alathra.alathraskills.skills.woodcutting.util;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;


public class OneWithTheForrest {

    // Get called on StructureGrowEvent
    public static void run(Block origin, int skillLevel) {
        bonemealGround(origin);
    }

    public static void bonemealGround(Block origin) {
        Block north = getRelativeGround(origin, BlockFace.NORTH, 1);
        Block south = getRelativeGround(origin, BlockFace.SOUTH, 1);;
        Block east = getRelativeGround(origin, BlockFace.EAST, 1);
        Block west = getRelativeGround(origin, BlockFace.WEST, 1);

        if (north != null) {
            north.applyBoneMeal(BlockFace.UP);
        }

        if (south != null) {
            south.applyBoneMeal(BlockFace.UP);
        }

        if (east != null) {
            east.applyBoneMeal(BlockFace.UP);
        }

        if (west != null) {
            west.applyBoneMeal(BlockFace.UP);
        }

    }

    public static Block getRelativeGround(Block block, BlockFace face, int distance) {
        if (distance > 5) {
            return null;
        }
        Block newBlock = block.getRelative(face, distance);
        if (!newBlock.getType().isAir()) {
            // Look 3 blocks above for air (if in ground)
            for (int i = 0; i < 3; i++) {
                Block newBlock2 = newBlock.getRelative(BlockFace.UP, i);
                if (newBlock2.getType().isAir()) {
                    return newBlock2.getRelative(BlockFace.DOWN);
                }
            }
            return getRelativeGround(newBlock, face, distance+1);
        }
        newBlock =  newBlock.getRelative(BlockFace.DOWN);
        // Block could still be air (ground is lower)
        return scanBelowIfStillLAir(newBlock);
    }

    public static Block scanBelowIfStillLAir(Block block) {
        if (!block.getType().isAir()) {
            return block;
        }
        // Look 3 blocks below for ground
        for (int i = 0; i < 3; i++) {
            block = block.getRelative(BlockFace.DOWN, i);
            if (!block.getType().isAir()) {
                return block;
            }
        }
        return null;
    }



}
