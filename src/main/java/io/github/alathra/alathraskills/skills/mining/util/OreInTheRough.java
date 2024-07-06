package io.github.alathra.alathraskills.skills.mining.util;

import io.github.alathra.alathraskills.utility.Cfg;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;

public class OreInTheRough {

    public static final int MAX_LEVEL = 5;

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
            case 1 -> Cfg.get().getDouble("skills.mining.oreInTheRough.chance.l1");
            case 2 -> Cfg.get().getDouble("skills.mining.oreInTheRough.chance.l2");
            case 3 -> Cfg.get().getDouble("skills.mining.oreInTheRough.chance.l3");
            case 4 -> Cfg.get().getDouble("skills.mining.oreInTheRough.chance.l4");
            case 5 -> Cfg.get().getDouble("skills.mining.oreInTheRough.chance.l5");
            default -> 0;
        };
    }

    private static double getGoldOreChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> Cfg.get().getDouble("skills.mining.oreInTheRough.goldChance.l1");
            case 2 -> Cfg.get().getDouble("skills.mining.oreInTheRough.goldChance.l2");
            case 3 -> Cfg.get().getDouble("skills.mining.oreInTheRough.goldChance.l3");
            case 4 -> Cfg.get().getDouble("skills.mining.oreInTheRough.goldChance.l4");
            case 5 -> Cfg.get().getDouble("skills.mining.oreInTheRough.goldChance.l5");
            default -> 0;
        };
    }

    private static double getIronOreChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> Cfg.get().getDouble("skills.mining.oreInTheRough.ironChance.l1");
            case 2 -> Cfg.get().getDouble("skills.mining.oreInTheRough.ironChance.l2");
            case 3 -> Cfg.get().getDouble("skills.mining.oreInTheRough.ironChance.l3");
            case 4 -> Cfg.get().getDouble("skills.mining.oreInTheRough.ironChance.l4");
            case 5 -> Cfg.get().getDouble("skills.mining.oreInTheRough.ironChance.l5");
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
