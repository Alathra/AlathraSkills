package io.github.alathra.alathraskills.listeners.exp;

import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class FarmingExpListener {

	// TODO:
	// sweetberries, glowberries

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
            if (ageable.getAge() == ageable.getAge())
			    expAmount = 4.0f;
			break;
		case BROWN_MUSHROOM:
			expAmount = 4.0f;
			break;
		case CACTUS:
			expAmount = 4.0f;
			break;
		case CARROTS:
            if (ageable.getAge() == ageable.getAge())
                expAmount = 4.0f;
			break;
		case COCOA:
            if (ageable.getAge() == ageable.getAge())
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
            if (ageable.getAge() == ageable.getAge())
                expAmount = 4.0f;
			break;
		case POTATOES:
            if (ageable.getAge() == ageable.getAge())
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
