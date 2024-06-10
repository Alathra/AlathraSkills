package io.github.alathra.alathraskills.skills.mining.util;

import io.github.alathra.alathraskills.skills.mining.util.helper.MiningData;
import io.github.alathra.alathraskills.utility.Cfg;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class VeinBreaker {

    public static int MAX_LEVEL = 7;

    public static void run(Block block, Player player, int skillLevel) {

        if (Math.random() >= getChance(skillLevel))
            return;

        VeinBreaker.breakVein(block, player, skillLevel);
    }

    // Method to find all blocks in an ore vein
    public static Set<Block> findVein(Block startBlock, int level) {
        Set<Block> veinBlocks = new HashSet<>();
        Set<Location> visitedLocations = new HashSet<>();
        Material oreMaterial = startBlock.getType();

        exploreVein(startBlock, oreMaterial, veinBlocks, visitedLocations, level);

        return veinBlocks;
    }

    // Recursive method to explore the ore vein
    private static void exploreVein(Block currentBlock, Material oreMaterial, Set<Block> veinBlocks, Set<Location> visitedLocations, int level) {

        // Put a cap on how big the mined vein can be based on skill level
        if (veinBlocks.size() >= level * 4) {
            return;
        }

        // Check if the current block is part of the vein and has not been visited
        if (currentBlock.getType() == oreMaterial && !visitedLocations.contains(currentBlock.getLocation())) {
            // Add the current block to the vein
            veinBlocks.add(currentBlock);
            visitedLocations.add(currentBlock.getLocation());

            // Check neighboring blocks
            for (BlockFace face : BlockFace.values()) {
                Block neighbor = currentBlock.getRelative(face);
                // Recursively explore neighboring blocks if they are of the same material
                exploreVein(neighbor, oreMaterial, veinBlocks, visitedLocations, level);
            }
        }
    }

    public static void breakVein(Block block, Player player, int level) {

        ItemStack tool = player.getInventory().getItemInMainHand();

        Set<Block> veins = findVein(block, level);
        for (final Block iterBlock : veins) {
            BlockBreakEvent event = new BlockBreakEvent(block, player);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                break;
            }
            iterBlock.breakNaturally(tool);
        }

    }

    private static double getChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> Double.parseDouble(Cfg.getValue("skills.mining.veinBreaker.chance.l1").toString());
            case 2 -> Double.parseDouble(Cfg.getValue("skills.mining.veinBreaker.chance.l2").toString());
            case 3 -> Double.parseDouble(Cfg.getValue("skills.mining.veinBreaker.chance.l3").toString());
            case 4 -> Double.parseDouble(Cfg.getValue("skills.mining.veinBreaker.chance.l4").toString());
            case 5 -> Double.parseDouble(Cfg.getValue("skills.mining.veinBreaker.chance.l5").toString());
            case 6 -> Double.parseDouble(Cfg.getValue("skills.mining.veinBreaker.chance.l6").toString());
            case 7 -> Double.parseDouble(Cfg.getValue("skills.mining.veinBreaker.chance.l7").toString());
            default -> 0;
        };
    }

}
