package io.github.alathra.alathraskills.skills.farming.util;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.skills.farming.util.helper.FarmingBlockUtil;
import io.github.alathra.alathraskills.utility.Cfg;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class WideSpread {

    public static final int MAX_LEVEL = 7;

    // Called on BlockPlaceEvent on an instance of farmland when the player places a seed
    public static void run(Block initialCrop, Player player, int skillLevel) {
        if (Math.random() >= getChance(skillLevel))
            return;

        new BukkitRunnable() {
            public void run() {
                switch (skillLevel) {
                    case 1, 2, 3:
                        runForPlusShape(initialCrop, player);
                        break;
                    case 4, 6, 5, 7:
                        runFor3by3Shape(initialCrop, player);
                        break;
                }
            }
        }.runTaskLater(AlathraSkills.getInstance(), 2);

    }

    private static void processInventoryForSeedsAndPlaceCrop(Material seed, Material crop, Player player, Block block) {
        if (!player.getInventory().contains(seed)) {
            return;
        }
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            // If nothing in this inventory slot, continue looping
            if (player.getInventory().getItem(i) == null) {
                continue;
            }
            if (player.getInventory().getItem(i).getType() == seed) {
                ItemStack itemStack = player.getInventory().getItem(i);
                if (itemStack.getAmount() == 1) {
                    player.getInventory().setItem(i, null);
                } else {
                    itemStack.setAmount(itemStack.getAmount() - 1);
                    player.getInventory().setItem(i, itemStack);
                }
                break;
            }
        }

        // place a crop of age 0 (no growth)
        Ageable ageableBlock = (Ageable) crop.createBlockData();
        ageableBlock.setAge(0);
        block.setType(crop);
        block.setBlockData(ageableBlock);
    }

    private static double getChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> Cfg.get().getDouble("skills.farming.wideSpread.chance.l1");
            case 2 -> Cfg.get().getDouble("skills.farming.wideSpread.chance.l2");
            case 3 -> Cfg.get().getDouble("skills.farming.wideSpread.chance.l3");
            case 4 -> Cfg.get().getDouble("skills.farming.wideSpread.chance.l4");
            case 5 -> Cfg.get().getDouble("skills.farming.wideSpread.chance.l5");
            case 6 -> Cfg.get().getDouble("skills.farming.wideSpread.chance.l6");
            case 7 -> Cfg.get().getDouble("skills.farming.wideSpread.chance.l7");
            default -> 0;
        };
    }

    private static void runForPlusShape(Block initialCrop, Player player) {
        for (Block block : FarmingBlockUtil.getPlusBlocks(initialCrop)) {
            placeCropInAdjacentBlock(initialCrop, player, block);
        }
    }

    private static void runFor3by3Shape(Block initialCrop, Player player) {
        for (Block block : FarmingBlockUtil.get3by3Blocks(initialCrop)) {
            placeCropInAdjacentBlock(initialCrop, player, block);
        }
    }

    private static void placeCropInAdjacentBlock(Block initialCrop, Player player, Block block) {
        // If there is no space to plant the seed, continue looping
        if (block.getType() != Material.AIR) {
            return;
        }
        // If there is no farmland below the block, continue looping
        if (block.getRelative(BlockFace.DOWN).getType() != Material.FARMLAND) {
            return;
        }
        // Loop through player's inventory for seed type and remove item
        switch (initialCrop.getType()) {
            case WHEAT:
                WideSpread.processInventoryForSeedsAndPlaceCrop(Material.WHEAT_SEEDS, Material.WHEAT, player, block);
                break;
            case BEETROOTS:
                WideSpread.processInventoryForSeedsAndPlaceCrop(Material.BEETROOT_SEEDS, Material.BEETROOTS, player, block);
                break;
            case CARROTS:
                WideSpread.processInventoryForSeedsAndPlaceCrop(Material.CARROT, Material.CARROTS, player, block);
                break;
            case POTATOES:
                WideSpread.processInventoryForSeedsAndPlaceCrop(Material.POTATO, Material.POTATOES, player, block);
                break;
        }
    }

}
