package io.github.alathra.alathraskills.listeners.exp;

import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.CaveVines;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;

public class FarmingExpListener {

	// TODO:
	// sweetberries, glowberries


    @EventHandler
    public void BerriesHarvestingListener(PlayerHarvestBlockEvent event) {
        Block block = event.getHarvestedBlock();

        float expAmount = 0.0f;

        switch (block.getType()) {
            case CAVE_VINES:
                expAmount = 4.0f;
                break;
            case SWEET_BERRY_BUSH:
                expAmount = 4.0f;
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
			expAmount = 4.0f;
			break;
		case BEETROOTS:
            if (ageable.getMaximumAge() == ageable.getAge())
			    expAmount = 4.0f;
			break;
		case BROWN_MUSHROOM:
			expAmount = 4.0f;
			break;
		case CACTUS:
			expAmount = 4.0f;
			break;
		case CARROTS:
            if (ageable.getMaximumAge() == ageable.getAge())
                expAmount = 4.0f;
            break;
        case CAVE_VINES:
            CaveVines caveVines = (CaveVines) block.getBlockData();
            if (caveVines.isBerries()) 
                expAmount = 4.0f;
            break;
		case COCOA:
            if (ageable.getMaximumAge() == ageable.getAge())
                expAmount = 4.0f;
			break;
		case CHORUS_FLOWER:
			expAmount = 4.0f;
			break;
		case CRIMSON_FUNGUS:
			expAmount = 4.0f;
			break;
		case KELP:
			expAmount = 4.0f;
			break;
		case MELON:
			expAmount = 4.0f;
			break;
		case NETHER_WART:
            if (ageable.getMaximumAge() == ageable.getAge())
                expAmount = 4.0f;
			break;
		case POTATOES:
            if (ageable.getMaximumAge() == ageable.getAge())
                expAmount = 4.0f;
			break;
		case PUMPKIN:
			expAmount = 4.0f;
			break;
		case RED_MUSHROOM:
			expAmount = 4.0f;
			break;
		case SUGAR_CANE:
			expAmount = 4.0f;
			break;
        case SWEET_BERRY_BUSH:
            if (ageable.getMaximumAge() == ageable.getAge())
            expAmount = 4.0f;
            break;
		case WARPED_FUNGUS:
			expAmount = 4.0f;
			break;
        case WHEAT:
            if (ageable.getAge() == ageable.getAge())
                expAmount = 4.0f;
            break;
		default:
			return;
		}
	}
}
