package io.github.alathra.alathraskills.skills.mining.util;

import io.github.alathra.alathraskills.utility.Cfg;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ProudProspector {

    public static int MAX_LEVEL = 6;

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
            case 1 -> Double.parseDouble(Cfg.getValue("skills.mining.proudProspector.chance.l1").toString());
            case 2 -> Double.parseDouble(Cfg.getValue("skills.mining.proudProspector.chance.l2").toString());
            case 3 -> Double.parseDouble(Cfg.getValue("skills.mining.proudProspector.chance.l3").toString());
            case 4 -> Double.parseDouble(Cfg.getValue("skills.mining.proudProspector.chance.l4").toString());
            case 5 -> Double.parseDouble(Cfg.getValue("skills.mining.proudProspector.chance.l5").toString());
            case 6 -> Double.parseDouble(Cfg.getValue("skills.mining.proudProspector.chance.l6").toString());
            default -> 0;
        };
    }

}
