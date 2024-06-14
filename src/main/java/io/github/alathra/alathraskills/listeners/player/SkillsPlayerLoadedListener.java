package io.github.alathra.alathraskills.listeners.player;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.events.SkillsPlayerLoadedEvent;
import io.github.alathra.alathraskills.utility.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SkillsPlayerLoadedListener implements Listener {

    @EventHandler
    public void onSkillsPlayerLoaded(SkillsPlayerLoadedEvent event) {
        AlathraSkills.getSkillsPlayerManager().registerSkillsPlayer(event.getUuid(), event.getSkillsPlayer());
        Logger.get().info("Caught a SkillsPlayerLoadedEvent!");
    }
}
