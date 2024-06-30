package io.github.alathra.alathraskills.listeners.exp;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.api.events.SkillPointGainEvent;
import io.github.alathra.alathraskills.utility.SoundEffectsUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SkillPointGainListener implements Listener {
    @EventHandler
    public void onSkillPointGain(SkillPointGainEvent event) {
        Player p = (Player) event.getSkillsPlayer().getPlayer();

        SoundEffectsUtil.playLevelUpSound(p);
        p.sendActionBar(ColorParser.of("<green><bold>+1 Skill Point</bold></green>").build());
    }
}
