package io.github.alathra.alathraskills.listeners.exp;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.SkillsPlayer;

public class WoodcuttingExpListener implements Listener {
	
	public static ArrayList<Material> logs = new ArrayList<>();
	
	static {
		// overworld wood
		logs.add(Material.ACACIA_LOG);
		logs.add(Material.BIRCH_LOG);
		logs.add(Material.CHERRY_LOG);
		logs.add(Material.DARK_OAK_LOG);
		logs.add(Material.JUNGLE_LOG);
		logs.add(Material.MANGROVE_LOG);
		logs.add(Material.OAK_LOG);
		logs.add(Material.SPRUCE_LOG);
		
		// nether wood
		logs.add(Material.CRIMSON_STEM);
		logs.add(Material.WARPED_STEM);
	}
	
	// Completely arbitrary right now
	private float expAmount = 5.0f;
	
	@EventHandler
	public void LogBreakingListener(BlockBreakEvent event) {
		Block block = event.getBlock();
		if (!logs.contains(block.getType())) {
			return;
		}
		
		// Block broken is a log
		// TODO: CHECK FOR PCD DATA
		Player player = event.getPlayer();
		SkillsPlayer skillsPlayer = new SkillsPlayer(player.getUniqueId());
		skillsPlayer.addExp(SkillsManager.WOODCUTTING_SKILL_ID, expAmount);
	}
}
