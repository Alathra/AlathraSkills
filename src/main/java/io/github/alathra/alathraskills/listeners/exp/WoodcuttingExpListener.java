package io.github.alathra.alathraskills.listeners.exp;

import java.util.ArrayList;

import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.SkillsPlayer;

public class WoodcuttingExpListener implements Listener {
	
	public static ArrayList<Material> logs = new ArrayList<>(Tag.LOGS.getValues());

	// Completely arbitrary right now
	private float logExpAmount = 3.0f;
	
	@EventHandler
	public void LogBreakingListener(BlockBreakEvent event) {
		Block block = event.getBlock();
		if (!logs.contains(block.getType())) {
			return;
		}

        // PDC check for unnatural block
        if (PDCUtil.isUnnatural(block)) {
            return;
        }

        // Block broken is a log
		Player player = event.getPlayer();
		SkillsPlayer skillsPlayer = new SkillsPlayer(player.getUniqueId());
		skillsPlayer.addExp(SkillsManager.WOODCUTTING_SKILL_ID, logExpAmount);
	}
}
