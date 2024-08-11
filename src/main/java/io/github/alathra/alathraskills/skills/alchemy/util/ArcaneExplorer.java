package io.github.alathra.alathraskills.skills.alchemy.util;

import org.bukkit.block.BrewingStand;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.BrewerInventory;

public class ArcaneExplorer {
    public static void run(InventoryOpenEvent event) {
        BrewerInventory brewerInventory = (BrewerInventory) event.getInventory();
        BrewingStand brewingStand = brewerInventory.getHolder();
        brewingStand.setBrewingTime(100);
        brewingStand.update();
    }
}
