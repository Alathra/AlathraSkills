package io.github.alathra.alathraskills.listeners.player;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.api.events.SkillsPlayerUnloadedEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveListener implements Listener {
    public PlayerLeaveListener() {
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerLeave(PlayerQuitEvent e) {
        final Player p = e.getPlayer();

        if (Bukkit.isStopping())
            return;

        SkillsPlayerManager.handlePlayerLeave(p)
            .thenAccept(skillsPlayerPassed -> {
                final SkillsPlayer skillsPlayer = skillsPlayerPassed;
                Bukkit.getScheduler().runTask(AlathraSkills.getInstance(), () -> {
                    AlathraSkills.getSkillsPlayerManager().unregisterSkillsPlayer(p.getUniqueId());

                    SkillsPlayerUnloadedEvent firedEvent = new SkillsPlayerUnloadedEvent(p.getUniqueId(), skillsPlayer);
                    Bukkit.getPluginManager().callEvent(firedEvent);
                });
            });
    }
}
