package io.github.alathra.alathraskills.skills.woodcutting.util;

import org.bukkit.Material;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

import io.github.alathra.alathraskills.utility.Cfg;

public class Trimmer {

    public static void run(BlockDamageEvent event, int skillLevel) {

        if (event.isCancelled()) {
            return;
        }

        event.setInstaBreak(true);

        if (Math.random() > getChance(skillLevel))
            return;

        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.APPLE));
    }

    private static double getChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> Cfg.get().get("skills.woodcutting.trimmer.chance.l1", 0.02);
            case 2 -> Cfg.get().get("skills.woodcutting.trimmer.chance.l2", 0.04);
            case 3 -> Cfg.get().get("skills.woodcutting.trimmer.chance.l3", 0.08);
            case 4 -> Cfg.get().get("skills.woodcutting.trimmer.chance.l4", 0.10);
            case 5 -> Cfg.get().get("skills.woodcutting.trimmer.chance.l5", 0.12);
            case 6 -> Cfg.get().get("skills.woodcutting.trimmer.chance.l6", 0.15);
            case 7 -> Cfg.get().get("skills.woodcutting.trimmer.chance.l7", 0.20);
            default -> 0;
        };
    }

}
