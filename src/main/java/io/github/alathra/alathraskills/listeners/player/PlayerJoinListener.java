package io.github.alathra.alathraskills.listeners.player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import io.github.alathra.alathraskills.AlathraSkills;

public class PlayerJoinListener implements Listener {
	
	public PlayerJoinListener() {
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();
    	AlathraSkills.getSkillsPlayerManager().handlePlayerJoin(p);
    }

}
