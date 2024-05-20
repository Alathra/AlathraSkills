package io.github.alathra.alathraskills.skills.farming.util;

import io.github.alathra.alathraskills.skills.farming.util.helper.FarmingBlockUtil;
import io.github.alathra.alathraskills.skills.farming.util.helper.FarmingData;
import io.github.alathra.alathraskills.utility.Cfg;

import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class FastHarvest {

    // Call this on the BlockBreakEvent if instance of breakable crop
    // crop is event.getBlock()
    public static void run(Block initialCrop, Player player, int skillLevel) {
        if (Math.random() >= getChance(skillLevel))
            return;
        switch (skillLevel) {
            case 1, 2, 3:
                runForPlusShape(initialCrop, player);
                break;
            case 4, 5, 6, 7:
                runFor3by3Shape(initialCrop, player);
                break;

        }
    }

    private static void runForPlusShape(Block initialCrop, Player player) {
        ItemStack tool = null;
        if (player.getInventory().getItemInMainHand() != null) {
            tool = player.getInventory().getItemInMainHand();
        }

        for (Block crop : FarmingBlockUtil.getPlusBlocks(initialCrop)) {
            // If adjacent block is a breakable crop
            if (FarmingData.getBreakableCrops().contains(crop.getType())) {
                // If crop has growth cycles
                if (crop.getBlockData() instanceof Ageable ageableCrop) {
                    // If crop is fully grown
                    if (ageableCrop.getAge() == ageableCrop.getMaximumAge()) {
                        crop.breakNaturally();
                        if (tool != null) {
                            crop.breakNaturally(tool);
                        } else {
                            crop.breakNaturally();
                        }
                    }
                    // Is a crop, but not fully grown so ignore
                    continue;
                } else {
                    // Is a crop that does not have growth cycles
                    if (tool != null) {
                        crop.breakNaturally(tool);
                    } else {
                        crop.breakNaturally();
                    }
                }
            }
        }
    }

    private static void runFor3by3Shape(Block initialCrop, Player player) {
        ItemStack tool = null;
        if (player.getInventory().getItemInMainHand() != null) {
            tool = player.getInventory().getItemInMainHand();
        }

        for (Block crop : FarmingBlockUtil.get3by3Blocks(initialCrop)) {
            // If adjacent block is a breakable crop
            if (FarmingData.getBreakableCrops().contains(crop.getType())) {
                // If crop has growth cycles
                if (crop.getBlockData() instanceof Ageable ageableCrop) {
                    // If crop is fully grown
                    if (ageableCrop.getAge() == ageableCrop.getMaximumAge()) {
                        crop.breakNaturally();
                        if (tool != null) {
                            crop.breakNaturally(tool);
                        } else {
                            crop.breakNaturally();
                        }
                    }
                    // Is a crop, but not fully grown so ignore
                    continue;
                } else {
                    // Is a crop that does not have growth cycles
                    if (tool != null) {
                        crop.breakNaturally(tool);
                    } else {
                        crop.breakNaturally();
                    }
                }
            }
        }
    }

    private static double getChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> Double.parseDouble(Cfg.getValue("skills.farming.fastHarvest.chance.l1").toString());
            case 2 -> Double.parseDouble(Cfg.getValue("skills.farming.fastHarvest.chance.l2").toString());
            case 3 -> Double.parseDouble(Cfg.getValue("skills.farming.fastHarvest.chance.l3").toString());
            case 4 -> Double.parseDouble(Cfg.getValue("skills.farming.fastHarvest.chance.l4").toString());
            case 5 -> Double.parseDouble(Cfg.getValue("skills.farming.fastHarvest.chance.l5").toString());
            case 6 -> Double.parseDouble(Cfg.getValue("skills.farming.fastHarvest.chance.l6").toString());
            case 7 -> Double.parseDouble(Cfg.getValue("skills.farming.fastHarvest.chance.l7").toString());
            default -> 0;
        };
    }
}
