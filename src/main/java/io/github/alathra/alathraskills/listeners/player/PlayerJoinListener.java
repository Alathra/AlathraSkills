package io.github.alathra.alathraskills.listeners.player;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.events.SkillsPlayerLoadedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();

        AlathraSkills.getSkillsPlayerManager().handlePlayerJoin(p)
            .thenAccept(skillsPlayerPassed -> {
                final SkillsPlayer skillsPlayer = skillsPlayerPassed;
                Bukkit.getScheduler().runTask(AlathraSkills.getInstance(), () -> {
                    // Register player to hashmap
                    AlathraSkills.getSkillsPlayerManager().registerSkillsPlayer(p.getUniqueId(), skillsPlayer);

                    // Fire loaded event
                    SkillsPlayerLoadedEvent firedEvent = new SkillsPlayerLoadedEvent(p.getUniqueId(), skillsPlayer);
                    Bukkit.getPluginManager().callEvent(firedEvent);
                });
            });
    }

}
