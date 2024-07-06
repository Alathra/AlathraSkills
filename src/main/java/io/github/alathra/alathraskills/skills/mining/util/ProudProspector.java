package io.github.alathra.alathraskills.skills.mining.util;

import io.github.alathra.alathraskills.utility.Cfg;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ProudProspector {

    public static final int MAX_LEVEL = 6;

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
            case 1 -> Cfg.get().getDouble("skills.mining.proudProspector.chance.l1");
            case 2 -> Cfg.get().getDouble("skills.mining.proudProspector.chance.l2");
            case 3 -> Cfg.get().getDouble("skills.mining.proudProspector.chance.l3");
            case 4 -> Cfg.get().getDouble("skills.mining.proudProspector.chance.l4");
            case 5 -> Cfg.get().getDouble("skills.mining.proudProspector.chance.l5");
            case 6 -> Cfg.get().getDouble("skills.mining.proudProspector.chance.l6");
            default -> 0;
        };
    }

}
