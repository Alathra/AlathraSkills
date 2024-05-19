package io.github.alathra.alathraskills.skills.mining.util;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

import io.github.alathra.alathraskills.utility.Cfg;

public class OreInTheRough {

    // Block is the stone block broken
    public static void run(Block block, int skillLevel) {
        if (Math.random() >= getChance(skillLevel))
            return;

        double dropChance = Math.random();
        Material drop;

        if (dropChance < getGoldOreChance(skillLevel)) {
            drop = Material.RAW_GOLD;
        } else if (dropChance < getIronOreChance(skillLevel)) {
            drop = Material.RAW_IRON;
        } else {
            drop = Material.RAW_COPPER;
        }

        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(drop, 1));
    }

    private static double getChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> Cfg.get().get("skills.mining.oreInTheRough.chance.l1", 0.03);
            case 2 -> Cfg.get().get("skills.mining.oreInTheRough.chance.l2", 0.06);
            case 3 -> Cfg.get().get("skills.mining.oreInTheRough.chance.l3", 0.09);
            case 4 -> Cfg.get().get("skills.mining.oreInTheRough.chance.l4", 0.12);
            case 5 -> Cfg.get().get("skills.mining.oreInTheRough.chance.l5", 0.15);
            default -> 0;
        };
    }

    private static double getGoldOreChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> Cfg.get().get("skills.mining.oreInTheRough.goldChance.l1", 0.02);
            case 2 -> Cfg.get().get("skills.mining.oreInTheRough.goldChance.l2", 0.04);
            case 3 -> Cfg.get().get("skills.mining.oreInTheRough.goldChance.l3", 0.06);
            case 4 -> Cfg.get().get("skills.mining.oreInTheRough.goldChance.l4", 0.08);
            case 5 -> Cfg.get().get("skills.mining.oreInTheRough.goldChance.l5", 0.10);
            default -> 0;
        };
    }

    private static double getIronOreChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> Cfg.get().get("skills.mining.oreInTheRough.ironChance.l1", 0.08);
            case 2 -> Cfg.get().get("skills.mining.oreInTheRough.ironChance.l2", 0.11);
            case 3 -> Cfg.get().get("skills.mining.oreInTheRough.ironChance.l3", 0.14);
            case 4 -> Cfg.get().get("skills.mining.oreInTheRough.ironChance.l4", 0.17);
            case 5 -> Cfg.get().get("skills.mining.oreInTheRough.ironChance.l5", 0.20);
            default -> 0;
        };
    }

    private static double getCopperOreChance(int skillLevel) {
    	// Config not added due to method not being used
        return switch (skillLevel) {
            case 1 -> 0.90;
            case 2 -> 0.85;
            case 3 -> 0.80;
            case 4 -> 0.75;
            case 5 -> 0.70;
            default -> 0;
        };
    }
}
