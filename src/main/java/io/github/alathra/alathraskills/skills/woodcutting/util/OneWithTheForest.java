package io.github.alathra.alathraskills.skills.woodcutting.util;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.utility.Cfg;
import io.github.alathra.alathraskills.utility.RandomUtil;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;

public class OneWithTheForest {

    private static final Set<Block> saplings = new HashSet<>();
    private static final ArrayList<Location> treeBlockLocations = new ArrayList<>();
    private static final ArrayList<Location> possibleBeeNestLocations = new ArrayList<>();

    public static int MAX_LEVEL = 7;


    // Get called on StructureGrowEvent
    public static void run(StructureGrowEvent event, int skillLevel) {
        Block origin = event.getLocation().getBlock();
        bonemealGround(origin);

        // Grow nearby saplings
        saplings.clear();
        getNearbySaplings(event.getLocation(), getRadius(skillLevel), 3);
        for (Block sapling : saplings) {
            for (int i = 0; i < 10; i++) {
                sapling.applyBoneMeal(BlockFace.UP);
            }
        }

        if (Math.random() > getBeehiveChance(skillLevel)) {
            return;
        }
        // check tree type
        if (event.getSpecies() == TreeType.TALL_BIRCH ||
            event.getSpecies() == TreeType.BIRCH ||
            event.getSpecies() == TreeType.BIG_TREE ||
            event.getSpecies() == TreeType.TREE) {
            recordTreeBlockLocations(event.getBlocks());
            // Place bee nest after 2 tick delay (to let tree grow)
            new BukkitRunnable() {
                public void run() {
                    findPossibleBeeNestLocations();
                    if (possibleBeeNestLocations.isEmpty()) {
                        return;
                    }
                    Block beeNestBlock = RandomUtil.getRandomElementInList(possibleBeeNestLocations).getBlock();
                    beeNestBlock.setType(Material.BEE_NEST);
                }
            }.runTaskLater(AlathraSkills.getInstance(), 2);
        }

    }

    private static void recordTreeBlockLocations(List<BlockState> blocks) {
        treeBlockLocations.clear();
        for (BlockState blockState : blocks) {
            treeBlockLocations.add(blockState.getLocation());
        }
    }

    private static void findPossibleBeeNestLocations() {
        possibleBeeNestLocations.clear();
        for (Location location : treeBlockLocations) {
            Block block = location.getBlock();
            if (Tag.LEAVES.isTagged(block.getType())) {
                block = block.getRelative(BlockFace.DOWN);
                if (block.getType().isAir()) {
                    if (Tag.LOGS.isTagged(block.getRelative(BlockFace.NORTH).getType())) {
                        possibleBeeNestLocations.add(block.getLocation());
                    } else if (Tag.LOGS.isTagged(block.getRelative(BlockFace.SOUTH).getType())) {
                        possibleBeeNestLocations.add(block.getLocation());
                    } else if (Tag.LOGS.isTagged(block.getRelative(BlockFace.EAST).getType())) {
                        possibleBeeNestLocations.add(block.getLocation());
                    } else if (Tag.LOGS.isTagged(block.getRelative(BlockFace.WEST).getType())) {
                        possibleBeeNestLocations.add(block.getLocation());
                    }
                }
            }
        }
    }

    private static void bonemealGround(Block origin) {
        Block north = getRelativeGround(origin, BlockFace.NORTH, 1);
        Block south = getRelativeGround(origin, BlockFace.SOUTH, 1);
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

    private static Block getRelativeGround(Block block, BlockFace face, int distance) {
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
            return getRelativeGround(newBlock, face, distance + 1);
        }
        newBlock = newBlock.getRelative(BlockFace.DOWN);
        // Block could still be air (ground is lower)
        return scanBelowIfStillLAir(newBlock);
    }

    private static Block scanBelowIfStillLAir(Block block) {
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

    private static void getNearbySaplings(Location origin, int radius, int height) {
        World world = origin.getWorld();
        int originX = origin.getBlockX();
        int originY = origin.getBlockY() - height;
        int originZ = origin.getBlockZ();

        // Iterate through the height of the cylinder
        for (int y = 0; y < height * 2; y++) {
            // Iterate through a square that bounds the circle for each layer
            for (int x = -radius; x <= radius; x++) {
                for (int z = -radius; z <= radius; z++) {
                    // Check if the point (x, z) is within the radius from the center
                    if (x * x + z * z <= radius * radius) {
                        // Add the block to the set
                        Block block = world.getBlockAt(originX + x, originY + y, originZ + z);
                        if (Tag.SAPLINGS.isTagged(block.getType())) {
                            saplings.add(block);
                        }
                    }
                }
            }
        }
    }

    private static int getRadius(int skillLevel) {
        switch (skillLevel) {
            case 1 -> {
                return Integer.parseInt(Cfg.getValue("skills.woodcutting.oneWithTheForest.radius.l1").toString());
            }
            case 2 -> {
                return Integer.parseInt(Cfg.getValue("skills.woodcutting.oneWithTheForest.radius.l2").toString());
            }
            case 3 -> {
                return Integer.parseInt(Cfg.getValue("skills.woodcutting.oneWithTheForest.radius.l3").toString());
            }
            case 4 -> {
                return Integer.parseInt(Cfg.getValue("skills.woodcutting.oneWithTheForest.radius.l4").toString());
            }
            case 5 -> {
                return Integer.parseInt(Cfg.getValue("skills.woodcutting.oneWithTheForest.radius.l5").toString());
            }
            case 6 -> {
                return Integer.parseInt(Cfg.getValue("skills.woodcutting.oneWithTheForest.radius.l6").toString());
            }
            case 7 -> {
                return Integer.parseInt(Cfg.getValue("skills.woodcutting.oneWithTheForest.radius.l7").toString());
            }
            default -> {
                return 0;
            }
        }
    }

    private static double getBeehiveChance(int skillLevel) {
        switch (skillLevel) {
            case 1 -> {
                return Double.parseDouble(Cfg.getValue("skills.woodcutting.oneWithTheForest.beehiveChance.l1").toString());
            }
            case 2 -> {
                return Double.parseDouble(Cfg.getValue("skills.woodcutting.oneWithTheForest.beehiveChance.l2").toString());
            }
            case 3 -> {
                return Double.parseDouble(Cfg.getValue("skills.woodcutting.oneWithTheForest.beehiveChance.l3").toString());
            }
            case 4 -> {
                return Double.parseDouble(Cfg.getValue("skills.woodcutting.oneWithTheForest.beehiveChance.l4").toString());
            }
            case 5 -> {
                return Double.parseDouble(Cfg.getValue("skills.woodcutting.oneWithTheForest.beehiveChance.l5").toString());
            }
            case 6 -> {
                return Double.parseDouble(Cfg.getValue("skills.woodcutting.oneWithTheForest.beehiveChance.l6").toString());
            }
            case 7 -> {
                return Double.parseDouble(Cfg.getValue("skills.woodcutting.oneWithTheForest.beehiveChance.l7").toString());
            }
            default -> {
                return 0;
            }
        }
    }

}
