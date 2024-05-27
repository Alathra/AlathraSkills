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

	// Completely arbitrary right now
	private float expAmount = Float.parseFloat(Cfg.getValue("experience.woodcutting.log").toString());
	
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

        // Block broken is a log

        SkillsPlayerManager.addPlayerExperience(event.getPlayer(), SkillsManager.WOODCUTTING_SKILL_ID, expAmount);
	}
}
