package io.github.alathra.alathraskills.listeners.exp;

import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.utility.Cfg;
import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import io.github.alathra.alathraskills.api.SkillsManager;

public class MiningExpListener implements Listener {

    @EventHandler
    public void BlockMiningListener(BlockBreakEvent event) {

        if (event.getPlayer() == null) {
            return;
        }

        Block block = event.getBlock();

        // PDC check for unnatural block
        if (PDCUtil.isUnnatural(block)) {
            return;
        }

        float expAmount = switch (block.getType()) {
            case AMETHYST_BLOCK, BUDDING_AMETHYST, AMETHYST_CLUSTER -> Float.parseFloat(Cfg.getValue("experience.mining.amethyst").toString());
            case COAL_ORE, DEEPSLATE_COAL_ORE -> Float.parseFloat(Cfg.getValue("experience.mining.coal").toString());
            case COPPER_ORE, DEEPSLATE_COPPER_ORE -> Float.parseFloat(Cfg.getValue("experience.mining.copper").toString());
            case DEEPSLATE_DIAMOND_ORE, DIAMOND_ORE -> Float.parseFloat(Cfg.getValue("experience.mining.diamond").toString());
            case DEEPSLATE_EMERALD_ORE, EMERALD_ORE -> Float.parseFloat(Cfg.getValue("experience.mining.emerald").toString());
            case DEEPSLATE_GOLD_ORE, GOLD_ORE -> Float.parseFloat(Cfg.getValue("experience.mining.gold").toString());
            case DEEPSLATE_IRON_ORE, IRON_ORE -> Float.parseFloat(Cfg.getValue("experience.mining.iron").toString());
            case DEEPSLATE_LAPIS_ORE, LAPIS_ORE -> Float.parseFloat(Cfg.getValue("experience.mining.lapis").toString());
            case DEEPSLATE_REDSTONE_ORE, REDSTONE_ORE -> Float.parseFloat(Cfg.getValue("experience.mining.redstone").toString());
            case NETHER_QUARTZ_ORE -> Float.parseFloat(Cfg.getValue("experience.mining.quartz").toString());
            case ANCIENT_DEBRIS -> Float.parseFloat(Cfg.getValue("experience.mining.debris").toString());
            default -> 0.0f;
        };

        SkillsPlayerManager.addPlayerExperience(event.getPlayer(), SkillsManager.MINING_SKILL_ID, expAmount);
    }
}
