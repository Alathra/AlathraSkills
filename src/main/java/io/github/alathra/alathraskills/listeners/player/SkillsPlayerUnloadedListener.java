package io.github.alathra.alathraskills.listeners.player;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.events.SkillsPlayerUnloadedEvent;
import io.github.alathra.alathraskills.utility.Logger;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SkillsPlayerUnloadedListener implements Listener {

    @EventHandler
    public void onSkillsPlayerLoaded(SkillsPlayerUnloadedEvent event) {
        AlathraSkills.getSkillsPlayerManager().unregisterSkillsPlayer(event.getUuid());
    }
}
