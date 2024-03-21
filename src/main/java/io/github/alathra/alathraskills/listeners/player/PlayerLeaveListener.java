package io.github.alathra.alathraskills.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import io.github.alathra.alathraskills.AlathraSkills;

public class PlayerLeaveListener implements Listener {
		
	public PlayerLeaveListener() {
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLeave(PlayerQuitEvent e) {
        final Player p = e.getPlayer();
    	AlathraSkills.getSkillsPlayerManager().handlePlayerLeave(p);
    }

}
