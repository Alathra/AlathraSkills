package io.github.alathra.alathraskills.listeners.exp;

import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.SkillsPlayer;

public class MiningExpListener implements Listener {

	@EventHandler
	public void BlockMiningListener(BlockBreakEvent event) {
		Block block = event.getBlock();

        // PDC check for unnatural block
        if (PDCUtil.isUnnatural(block)) {
            return;
        }

		float expAmount = 0.0f;

		switch (block.getType()) {
		case ANCIENT_DEBRIS:
			expAmount = 100.0f;
			break;
		case ANDESITE:
			expAmount = 4.0f;
			break;
		case AMETHYST_BLOCK:
			expAmount = 4.0f;
			break;
		case AMETHYST_CLUSTER:
			expAmount = 4.0f;
			break;
		case BLACKSTONE:
			expAmount = 4.0f;
			break;
		case BUDDING_AMETHYST:
			expAmount = 4.0f;
			break;
		case CALCITE:
			expAmount = 4.0f;
			break;
		case COAL_ORE:
			expAmount = 4.0f;
			break;
		case COPPER_ORE:
			expAmount = 6.0f;
			break;
		case DEEPSLATE:
			expAmount = 4.0f;
			break;
		case DEEPSLATE_DIAMOND_ORE:
			expAmount = 50.0f;
			break;
		case DEEPSLATE_COAL_ORE:
			expAmount = 4.0f;
			break;
		case DEEPSLATE_COPPER_ORE:
			expAmount = 6.0f;
			break;
		case DEEPSLATE_EMERALD_ORE:
			expAmount = 45.0f;
			break;
		case DEEPSLATE_GOLD_ORE:
			expAmount = 25.0f;
			break;
		case DEEPSLATE_IRON_ORE:
			expAmount = 8.0f;
			break;
		case DEEPSLATE_LAPIS_ORE:
			expAmount = 20.0f;
			break;
		case DEEPSLATE_REDSTONE_ORE:
			expAmount = 10.0f;
			break;
		case DIAMOND_ORE:
			expAmount = 50.0f;
			break;
		case DIORITE:
			expAmount = 4.0f;
			break;
		case EMERALD_ORE:
			expAmount = 45.0f;
			break;
		case END_STONE:
			expAmount = 4.0f;
			break;
		case GRANITE:
			expAmount = 4.0f;
			break;
		case GRAVEL:
			expAmount = 4.0f;
			break;
		case GOLD_ORE:
			expAmount = 25.0f;
			break;
		case IRON_ORE:
			expAmount = 8.0f;
			break;
		case LAPIS_ORE:
			expAmount = 20.0f;
			break;
		case MAGMA_BLOCK:
			expAmount = 4.0f;
			break;
		case NETHERRACK:
			expAmount = 4.0f;
			break;
		case NETHER_QUARTZ_ORE:
			expAmount = 4.0f;
			break;
		case REDSTONE_ORE:
			expAmount = 10.0f;
			break;
		case SMOOTH_BASALT:
			expAmount = 4.0f;
			break;
		case STONE:
			expAmount = 4.0f;
			break;
		case TUFF:
			expAmount = 4.0f;
			break;
		default:
			return;
		}
		Player player = event.getPlayer();
		SkillsPlayer skillsPlayer = new SkillsPlayer(player.getUniqueId());
		skillsPlayer.addExp(SkillsManager.MINING_SKILL_ID, expAmount);
	}
}
