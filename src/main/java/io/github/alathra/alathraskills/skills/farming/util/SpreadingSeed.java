package io.github.alathra.alathraskills.skills.farming.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SpreadingSeed {

    // Called on BlockPlaceEvent on an instance of farmland when the player places a seed
    public static void run (Block initialCrop, Player player, int skillLevel) {
        if (Math.random() >= getChance(skillLevel))
            return;

        switch (skillLevel) {
            case 1, 2, 3:
                runForPlusShape(initialCrop, player);
                break;
            case 4, 6, 5, 7:
                runFor3by3Shape(initialCrop, player);
                break;
        }
    }

    private static void processInventoryForSeedsAndPlaceCrop(Material seed, Material crop, Player player, Block block) {
        if (player.getInventory().contains(seed)) {
            for (int i = 0; i < player.getInventory().getContents().length; i++) {
                if (player.getInventory().getItem(i) == null) {
                    continue;
                }
                if (player.getInventory().getItem(i).getType() == seed) {
                    ItemStack itemStack = player.getInventory().getItem(i);
                    if (itemStack.getAmount() == 1) {
                        player.getInventory().remove(itemStack);
                    } else {
                        itemStack.setAmount(itemStack.getAmount() - 1);
                        player.getInventory().setItem(i, itemStack);
                    }
                }
            }
            // place a crop of age 0 (no growth)
            Ageable ageableBlock = (Ageable) crop.createBlockData();
            ageableBlock.setAge(0);
            block.setBlockData(ageableBlock);
        }
    }

    private static double getChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> 0.15;
            case 2 -> 0.3;
            case 3 -> 0.5;
            case 4 -> 0.2;
            case 5 -> 0.4;
            case 6 -> 0.6;
            case 7 -> 1.00;
            default -> 0;
        };
    }

    private static void runForPlusShape(Block initialCrop, Player player) {
        for (Block block : FarmingBlockUtil.getPlusBlocks(initialCrop)) {
            scanAdjacentBlocks(initialCrop, player, block);
        }
    }

    private static void runFor3by3Shape(Block initialCrop, Player player) {
        for (Block block : FarmingBlockUtil.get3by3Blocks(initialCrop)) {
            scanAdjacentBlocks(initialCrop, player, block);
        }
    }

    private static void scanAdjacentBlocks(Block initialCrop, Player player, Block block) {
        if (block.getRelative(BlockFace.DOWN).getType() == Material.FARMLAND) {
            // If there is not space to plant the seed, continue looping
            if (block.getType() != Material.AIR) {
                return;
            }
            // Loop through player's inventory for seed type and remove item
            switch (initialCrop.getType()) {
                case WHEAT:
                    SpreadingSeed.processInventoryForSeedsAndPlaceCrop(Material.WHEAT_SEEDS, Material.WHEAT, player, block);
                    break;
                case BEETROOT_SEEDS:
                    SpreadingSeed.processInventoryForSeedsAndPlaceCrop(Material.BEETROOT_SEEDS, Material.BEETROOTS, player, block);
                    break;
                case CARROTS:
                    SpreadingSeed.processInventoryForSeedsAndPlaceCrop(Material.CARROT, Material.CARROTS, player, block);
                    break;
                case POTATOES:
                    SpreadingSeed.processInventoryForSeedsAndPlaceCrop(Material.POTATO, Material.POTATOES, player, block);
                    break;
            }
        }
    }
}
