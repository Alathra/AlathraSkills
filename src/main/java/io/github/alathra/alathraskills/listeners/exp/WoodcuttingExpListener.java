package io.github.alathra.alathraskills.listeners.exp;

import java.util.ArrayList;

import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.utility.Cfg;
import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import io.github.alathra.alathraskills.api.SkillsManager;

public class WoodcuttingExpListener implements Listener {
	
	public static ArrayList<Material> logs = new ArrayList<>(Tag.LOGS.getValues());

	
	@EventHandler
	public void LogBreakingListener(BlockBreakEvent event) {

        if (event.getPlayer() == null) {
            return;
        }

		Block block = event.getBlock();
		if (!logs.contains(block.getType())) {
			return;
		}

        // PDC check for unnatural block
        if (PDCUtil.isUnnatural(block)) {
            return;
        }

        float expAmount = switch (block.getType()) {
            case OAK_LOG, STRIPPED_OAK_LOG, OAK_WOOD, STRIPPED_OAK_WOOD -> Cfg.get().getFloat("experience.woodcutting.oak");
            case SPRUCE_LOG, STRIPPED_SPRUCE_LOG, SPRUCE_WOOD, STRIPPED_SPRUCE_WOOD -> Cfg.get().getFloat("experience.woodcutting.spruce");
            case BIRCH_LOG, STRIPPED_BIRCH_LOG, BIRCH_WOOD, STRIPPED_BIRCH_WOOD -> Cfg.get().getFloat("experience.woodcutting.birch");
            case JUNGLE_LOG, STRIPPED_JUNGLE_LOG, JUNGLE_WOOD, STRIPPED_JUNGLE_WOOD -> Cfg.get().getFloat("experience.woodcutting.jungle");
            case ACACIA_LOG, STRIPPED_ACACIA_LOG, ACACIA_WOOD, STRIPPED_ACACIA_WOOD -> Cfg.get().getFloat("experience.woodcutting.acacia");
            case DARK_OAK_LOG, STRIPPED_DARK_OAK_LOG, DARK_OAK_WOOD, STRIPPED_DARK_OAK_WOOD -> Cfg.get().getFloat("experience.woodcutting.darkOak");
            case MANGROVE_LOG, STRIPPED_MANGROVE_LOG, MANGROVE_WOOD, STRIPPED_MANGROVE_WOOD -> Cfg.get().getFloat("experience.woodcutting.mangrove");
            case CHERRY_LOG, STRIPPED_CHERRY_LOG, CHERRY_WOOD, STRIPPED_CHERRY_WOOD -> Cfg.get().getFloat("experience.woodcutting.cherry");
            case CRIMSON_STEM, STRIPPED_CRIMSON_STEM -> Cfg.get().getFloat("experience.woodcutting.crimson");
            case WARPED_STEM, STRIPPED_WARPED_STEM -> Cfg.get().getFloat("experience.woodcutting.warped");
            default -> 0.0f;
        };

        // Block broken is a log

        SkillsPlayerManager.addPlayerExperience(event.getPlayer(), SkillsManager.WOODCUTTING_SKILL_ID, expAmount);
	}
}
