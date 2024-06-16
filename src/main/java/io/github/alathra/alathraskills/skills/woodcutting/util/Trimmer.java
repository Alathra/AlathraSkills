package io.github.alathra.alathraskills.skills.woodcutting.util;

import io.github.alathra.alathraskills.utility.Cfg;
import org.bukkit.Material;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

public class Trimmer {

    public static int MAX_LEVEL = 7;

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
            case 1 -> Double.parseDouble(Cfg.getValue("skills.woodcutting.trimmer.chance.l1").toString());
            case 2 -> Double.parseDouble(Cfg.getValue("skills.woodcutting.trimmer.chance.l2").toString());
            case 3 -> Double.parseDouble(Cfg.getValue("skills.woodcutting.trimmer.chance.l3").toString());
            case 4 -> Double.parseDouble(Cfg.getValue("skills.woodcutting.trimmer.chance.l4").toString());
            case 5 -> Double.parseDouble(Cfg.getValue("skills.woodcutting.trimmer.chance.l5").toString());
            case 6 -> Double.parseDouble(Cfg.getValue("skills.woodcutting.trimmer.chance.l6").toString());
            case 7 -> Double.parseDouble(Cfg.getValue("skills.woodcutting.trimmer.chance.l7").toString());
            default -> 0;
        };
    }

}
