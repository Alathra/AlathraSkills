package io.github.alathra.alathraskills.skills.mining.util;

import org.bukkit.block.Block;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ProudProspector {

    // Called on BlockBreakEvent
    public static void run(BlockBreakEvent event, int skillLevel) {
        if (Math.random() >= getChance(skillLevel))
            return;

        if (event.isCancelled())
            return;

        // If there are no drops, return
        if (event.getBlock().getDrops().isEmpty())
            return;

        if (!event.isDropItems())
            return;

        // If tool is silk touch, drop an ore block
        ItemStack tool = event.getPlayer().getInventory().getItemInMainHand();
        if (tool.hasItemMeta()) {
            if (tool.getItemMeta().getEnchants().containsKey(Enchantment.SILK_TOUCH)) {
                event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), new ItemStack(event.getBlock().getType()));
                return;
            }
        }

        // If no silk touch, duplicate drops
        for (ItemStack itemStack : event.getBlock().getDrops()) {
            // Find the drops of the block and drop them again, effectively doubling the drops
            event.getBlock().getWorld().dropItemNaturally(event.getBlock().getLocation(), itemStack);
        }

    }

    private static double getChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> 0.05;
            case 2 -> 0.10;
            case 3 -> 0.15;
            case 4 -> 0.20;
            case 5 -> 0.25;
            case 6 -> 0.30;
            default -> 0;
        };
    }

}
