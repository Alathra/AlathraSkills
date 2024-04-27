package io.github.alathra.alathraskills.skills.farming.util;

import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FarmingBlockUtil {

    public static ArrayList<Block> getPlusBlocks(Block block) {
        ArrayList<Block> blocks = new ArrayList<>();
        blocks.add(block.getRelative(BlockFace.NORTH));
        blocks.add(block.getRelative(BlockFace.SOUTH));
        blocks.add(block.getRelative(BlockFace.EAST));
        blocks.add(block.getRelative(BlockFace.WEST));
        return blocks;
    }

    public static ArrayList<Block> get3by3Blocks(Block block) {
        ArrayList<Block> blocks = getPlusBlocks(block);
        blocks.add(block.getRelative(BlockFace.NORTH_WEST));
        blocks.add(block.getRelative(BlockFace.NORTH_EAST));
        blocks.add(block.getRelative(BlockFace.SOUTH_WEST));
        blocks.add(block.getRelative(BlockFace.SOUTH_EAST));
        return blocks;
    }
}
