package io.github.alathra.alathraskills.skills.woodcutting.util;

import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class Groundskeeper {
    

    public static void run(Player player, Block block, int skillLevel, ItemStack tool) {
        Material material = block.getType();
        Location location = block.getLocation();
        if (tool.getType() == Material.AIR || tool == null) {
            tool = new ItemStack(Material.SHEARS);
        }
        List<Block> blockList = new ArrayList<>();
        int x, y, z = 0;
        for (x = 0; x < getXLimit(skillLevel); x++) {
            for (y = 0; y < getYLimit(skillLevel); y++) {
                for (z = 0; z < getZLimit(skillLevel); z++) {
                    if (x == 0 || y == 0 || z == 0) {
                        continue;
                    }
                    blockList.add(block.getRelative(x, y, z));
                }
                blockList.add(block.getRelative(x, y, z));
            }
            blockList.add(block.getRelative(x, y, z));
        }
        final ItemStack finalTool = tool;

        blockList.forEach(b -> {
            if (!PDCUtil.isUnnatural(b)) {
                if (Tag.LEAVES.isTagged(b.getType())) {
                    b.breakNaturally(finalTool);
                }
            }
        });

        // Extra drops
        if (Math.random() <= getExtraDropsChance(skillLevel))
            location.getWorld().dropItemNaturally(location, new ItemStack(material, getExtraDrops(skillLevel)));
    }

    private static int getExtraDrops(int skillLevel) {
        return switch (skillLevel) {
            case 1, 2 -> 2;
            case 3, 4 -> 3;
            case 5, 6 -> 7;
            case 7 -> 16;
            default -> 0;
        };
    }

    private static double getExtraDropsChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> 0.15;
            case 2 -> 0.25;
            case 3 -> 0.35;
            case 4 -> 0.45;
            case 5 -> 0.55;
            case 6 -> 0.65;
            case 7 -> 0.75;
            default -> 0;
        };
    }

    private static int getXLimit(int skillLevel) {
        return switch (skillLevel) {
            case 1, 2, 3, 4 -> 2;
            case 5, 6 -> 3;
            case 7 -> 4;
            default -> 0;
        };
    }

    private static int getYLimit(int skillLevel) {
        return switch (skillLevel) {
            case 1, 2 -> 2;
            case 3, 4, 5, 6 -> 3;
            case 7 -> 4;
            default -> 0;
        };
    }

    private static int getZLimit(int skillLevel) {
        return switch (skillLevel) {
            case 1, 2, 3, 4 -> 2;
            case 5, 6 -> 3;
            case 7 -> 4;
            default -> 0;
        };
    }

}
