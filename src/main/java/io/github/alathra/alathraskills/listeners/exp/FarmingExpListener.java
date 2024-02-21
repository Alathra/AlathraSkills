package io.github.alathra.alathraskills.listeners.exp;

import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;

public class FarmingExpListener {

	// TODO:
	// sweetberries, glowberries

	@EventHandler
	public void FarmHarvestingListener(BlockBreakEvent event) {
		
		Block block = event.getBlock();
		float expAmount = 0.0f;
		
		switch (block.getType()) {
		case BAMBOO:
			expAmount = 4.0f;
			break;
		case BEETROOTS:
			expAmount = 4.0f;
			break;
		case BROWN_MUSHROOM:
			expAmount = 4.0f;
			break;
		case CACTUS:
			expAmount = 4.0f;
			break;
		case CARROTS:
			expAmount = 4.0f;
			break;
		case COCOA:
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
			expAmount = 4.0f;
			break;
		case POTATOES:
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
		default:
			return;
		}
	}
}
