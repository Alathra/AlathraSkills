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

        float expAmount = switch (block.getType()) {
            case AMETHYST_BLOCK, BUDDING_AMETHYST, AMETHYST_CLUSTER -> 4.0f;
            case COAL_ORE, DEEPSLATE_COAL_ORE -> 1.5f;
            case COPPER_ORE, DEEPSLATE_COPPER_ORE -> 0.6f;
            case DEEPSLATE_DIAMOND_ORE, DIAMOND_ORE -> 15.0f;
            case DEEPSLATE_EMERALD_ORE, EMERALD_ORE -> 30.0f;
            case DEEPSLATE_GOLD_ORE, GOLD_ORE -> 7.5f;
            case DEEPSLATE_IRON_ORE, IRON_ORE -> 3.0f;
            case DEEPSLATE_LAPIS_ORE, LAPIS_ORE -> 2.0f;
            case DEEPSLATE_REDSTONE_ORE, REDSTONE_ORE -> 1.2f;
            case NETHER_QUARTZ_ORE -> 1.0f;
            case ANCIENT_DEBRIS -> 100.0f;
            default -> 0.0f;
        };

        Player player = event.getPlayer();
        SkillsPlayer skillsPlayer = new SkillsPlayer(player.getUniqueId());
        skillsPlayer.addExp(SkillsManager.MINING_SKILL_ID, expAmount);
    }
}
