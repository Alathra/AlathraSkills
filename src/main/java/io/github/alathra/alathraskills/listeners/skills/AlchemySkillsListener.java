package io.github.alathra.alathraskills.listeners.skills;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.skills.alchemy.util.AlchemicalRecycling;
import io.github.alathra.alathraskills.skills.alchemy.util.ArcaneExplorer;
import io.github.alathra.alathraskills.skills.alchemy.util.QualityIngredients;
import io.github.alathra.alathraskills.skills.alchemy.util.RapidInfusion;
import org.bukkit.Bukkit;
import org.bukkit.block.BrewingStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BrewingStartEvent;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.inventory.BrewerInventory;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.UUID;

public class AlchemySkillsListener implements Listener {

    // Runs skill Alchemical Recycling and QualityIngredients
    @EventHandler
    public void BrewEndListener(BrewEvent event) {
        BrewingStand brewingStand = (BrewingStand) event.getBlock().getState();
        if (!brewingStand.hasMetadata("alathraskills_brewer")) {
            return; // When this brew was started, it was not done by a player
        }
        Player player = Bukkit.getPlayer(UUID.fromString(brewingStand.getMetadata("alathraskills_brewer").get(0).asString()));
        AlchemicalRecycling.run(event, 5);
        QualityIngredients.run(event, 5);
    }

    // Runs skill Rapid Infusion and finds player
    @EventHandler
    public void BrewStartListener(BrewingStartEvent event) {
        BrewingStand brewingStand = (BrewingStand) event.getBlock().getState();
        if (brewingStand.getInventory().getViewers().isEmpty()) {
            if (brewingStand.hasMetadata("alathraskills_brewer")) {
                brewingStand.removeMetadata("alathraskills_brewer", AlathraSkills.getInstance());
            }
            return; // Brew was not started by a player
        } else if (!(brewingStand.getInventory().getViewers().get(0) instanceof Player)) {
            return;
        }
        Player player = (Player) brewingStand.getInventory().getViewers().get(0);
        brewingStand.setMetadata("alathraskills_brewer", new FixedMetadataValue(AlathraSkills.getInstance(), player.getUniqueId().toString()));
        RapidInfusion.run(event, 5);
    }

    // Testing...
    @EventHandler
    public void BrewingStandOpenEvent(InventoryOpenEvent event) {
        if (event.getInventory().getLocation() == null) {
            return; // inventory does not correspond to a block
        }
        if (!(event.getInventory() instanceof BrewerInventory)) {
            return;
        }

        ArcaneExplorer.run(event);

    }
}
