package io.github.alathra.alathraskills.skills.mining.util;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.skills.mining.util.helper.MiningData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;
import org.bukkit.event.block.BlockBreakEvent;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class VeinBreaker {

	private static final BlockFace[] blockFaces = {BlockFace.DOWN, BlockFace.UP, BlockFace.NORTH, BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH};

    public static void run(Block block, Player player, int skillLevel) {

        if (Math.random() >= getChance(skillLevel))
            return;

        VeinBreaker.breakVein(block, player, skillLevel);
    }

    public static HashSet<Block> findVein(Block start, int maxSize) {
        HashSet<Block> blocks = new HashSet<>();
        HashSet<Block> last = new HashSet<>();
        HashSet<Block> toAdd = new HashSet<>();
        last.add(start);

        boolean found = true;
        while (found) {
            found = false;
            for (Block b : last) {
                for (BlockFace face : blockFaces) {
                    Block block = b.getRelative(face);
                    if (MiningData.getOres().contains(block.getType())) {
                        toAdd.add(block);
                    }
                }
            }
            blocks.addAll(toAdd);
            last = toAdd;
            if (blocks.size() >= maxSize)
                return blocks;
        }
        return blocks;
    }

    public static void breakVein(Block block, Player player, int level) {

        if (MiningData.getOres().contains(block.getType())) {
            HashSet<Block> veins = findVein(block,10*level);
            for (final Block iterBlock : veins) {
                BlockBreakEvent event = new BlockBreakEvent(block,player);
                Bukkit.getPluginManager().callEvent(event);
                if (event.isCancelled()) {
                    break;
                }
                iterBlock.breakNaturally();
            }
        }

    }

    private static double getChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> 0.05;
            case 2 -> 0.10;
            case 3 -> 0.20;
            case 4 -> 0.30;
            case 5 -> 0.40;
            case 6 -> 0.50;
            case 7 -> 0.60;
            default -> 0;
        };
    }

}
