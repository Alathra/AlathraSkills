package io.github.alathra.alathraskills.listeners.exp;

import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.api.events.SkillPointGainEvent;
import io.github.alathra.alathraskills.utility.Cfg;
import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class MiningExpListener implements Listener {

    @EventHandler
    public void BlockMiningListener(BlockBreakEvent event) {
        Player p = event.getPlayer();

        if (p == null) {
            return;
        }

        Block block = event.getBlock();

        // PDC check for unnatural block
        if (PDCUtil.isUnnatural(block)) {
            return;
        }

        float expAmount = switch (block.getType()) {
            case AMETHYST_BLOCK, BUDDING_AMETHYST, AMETHYST_CLUSTER -> Cfg.get().getFloat("experience.mining.amethyst");
            case COAL_ORE, DEEPSLATE_COAL_ORE -> Cfg.get().getFloat("experience.mining.coal");
            case COPPER_ORE, DEEPSLATE_COPPER_ORE -> Cfg.get().getFloat("experience.mining.copper");
            case DEEPSLATE_DIAMOND_ORE, DIAMOND_ORE -> Cfg.get().getFloat("experience.mining.diamond");
            case DEEPSLATE_EMERALD_ORE, EMERALD_ORE -> Cfg.get().getFloat("experience.mining.emerald");
            case DEEPSLATE_GOLD_ORE, GOLD_ORE -> Cfg.get().getFloat("experience.mining.gold");
            case DEEPSLATE_IRON_ORE, IRON_ORE -> Cfg.get().getFloat("experience.mining.iron");
            case DEEPSLATE_LAPIS_ORE, LAPIS_ORE -> Cfg.get().getFloat("experience.mining.lapis");
            case DEEPSLATE_REDSTONE_ORE, REDSTONE_ORE -> Cfg.get().getFloat("experience.mining.redstone");
            case NETHER_QUARTZ_ORE -> Cfg.get().getFloat("experience.mining.quartz");
            case ANCIENT_DEBRIS -> Cfg.get().getFloat("experience.mining.debris");
            default -> 0.0f;
        };

        if (SkillsPlayerManager.isSkillPointGained(p, expAmount)) {
            Bukkit.getPluginManager().callEvent(new SkillPointGainEvent(SkillsPlayerManager.getSkillsPlayer(p)));
        }
        SkillsPlayerManager.addPlayerExperience(event.getPlayer(), SkillsManager.MINING_SKILL_ID, expAmount);
    }
}
