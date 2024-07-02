package io.github.alathra.alathraskills.skills.woodcutting.util;

import io.github.alathra.alathraskills.utility.Cfg;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.inventory.ItemStack;

public class Trimmer {

    public static int MAX_LEVEL = 7;

    public static void run(BlockDamageEvent event, int skillLevel) {

        if (event.isCancelled()) {
            return;
        }

        event.setInstaBreak(true);
        event.getBlock().getWorld().playSound(event.getBlock().getLocation(), Sound.BLOCK_GRASS_BREAK, SoundCategory.BLOCKS, 1.0f, 1.0f);

        if (Math.random() > getChance(skillLevel))
            return;

        event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(Material.APPLE));
    }

    private static double getChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> Cfg.get().getDouble("skills.woodcutting.trimmer.chance.l1");
            case 2 -> Cfg.get().getDouble("skills.woodcutting.trimmer.chance.l2");
            case 3 -> Cfg.get().getDouble("skills.woodcutting.trimmer.chance.l3");
            case 4 -> Cfg.get().getDouble("skills.woodcutting.trimmer.chance.l4");
            case 5 -> Cfg.get().getDouble("skills.woodcutting.trimmer.chance.l5");
            case 6 -> Cfg.get().getDouble("skills.woodcutting.trimmer.chance.l6");
            case 7 -> Cfg.get().getDouble("skills.woodcutting.trimmer.chance.l7");
            default -> 0;
        };
    }

}
