package io.github.alathra.alathraskills.listeners.exp;

import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.CaveVines;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;

public class FarmingExpListener {

    @EventHandler
    public void BerriesHarvestingListener(PlayerHarvestBlockEvent event) {
        Block block = event.getHarvestedBlock();

        float expAmount = 0.0f;

        switch (block.getType()) {
            case CAVE_VINES:
                expAmount = 0.3f;
                break;
            case SWEET_BERRY_BUSH:
                expAmount = 0.1f;
                break;
            default:
                return;
        }
    }

    @EventHandler
    public void FarmHarvestingListener(BlockBreakEvent event) {

        Block block = event.getBlock();
        float expAmount = 0.0f;

        Ageable ageable = null;

        if (block instanceof Ageable)
            ageable = (Ageable) block;

        // TODO: Check for PDC data where relevant

        switch (block.getType()) {
            case BAMBOO:
                expAmount = 0.06f;
                break;
            case BROWN_MUSHROOM, RED_MUSHROOM:
                expAmount = 1.0f;
                break;
            case CACTUS:
                expAmount = 1.0f;
                break;
            case CARROTS, POTATOES, BEETROOTS, WHEAT:
                if (ageable.getMaximumAge() == ageable.getAge())
                    expAmount = 1.0f;
                break;
            case CAVE_VINES:
                CaveVines caveVines = (CaveVines) block.getBlockData();
                if (caveVines.isBerries())
                    expAmount = 0.3f;
                break;
            case COCOA:
                if (ageable.getMaximumAge() == ageable.getAge())
                    expAmount = 4.0f;
                break;
            case KELP:
                expAmount = 0.1f;
                break;
            case MELON, PUMPKIN:
                expAmount = 1.0f;
                break;
            case NETHER_WART:
                if (ageable.getMaximumAge() == ageable.getAge())
                    expAmount = 0.5f;
                break;
            case SUGAR_CANE:
                expAmount = 0.2f;
                break;
            case SWEET_BERRY_BUSH:
                if (ageable.getMaximumAge() == ageable.getAge())
                    expAmount = 0.1f;
                break;
            default:
                return;
        }
    }
}
