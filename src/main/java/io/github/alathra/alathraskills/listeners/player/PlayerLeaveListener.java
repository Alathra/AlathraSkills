package io.github.alathra.alathraskills.listeners.player;

import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.api.events.SkillsPlayerUnloadedEvent;
import io.github.alathra.alathraskills.utility.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import io.github.alathra.alathraskills.AlathraSkills;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PlayerLeaveListener implements Listener {
		
	public PlayerLeaveListener() {
	}
	
	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerLeave(PlayerQuitEvent e) {
        final Player p = e.getPlayer();

        SkillsPlayer skillsPlayer = null;
    	CompletableFuture<SkillsPlayer> future = SkillsPlayerManager.handlePlayerLeave(p);
        try {
            skillsPlayer = future.get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.get().error("Something went wrong: " + ex);
        }

        SkillsPlayerUnloadedEvent firedEvent = new SkillsPlayerUnloadedEvent(p.getUniqueId(), skillsPlayer);
        Bukkit.getPluginManager().callEvent(firedEvent);
    }

}
