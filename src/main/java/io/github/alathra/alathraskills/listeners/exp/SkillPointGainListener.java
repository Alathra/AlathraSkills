package io.github.alathra.alathraskills.listeners.exp;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.events.SkillPointGainEvent;
import io.github.alathra.alathraskills.gui.GuiHelper;
import io.github.alathra.alathraskills.utility.SoundEffectsUtil;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SkillPointGainListener implements Listener {
    @EventHandler
    public void onSkillPointGain(SkillPointGainEvent event) {
        Player p = (Player) event.getSkillsPlayer().getPlayer();

        SkillsPlayer skillsPlayer = AlathraSkills.getSkillsPlayerManager().getSkillsPlayer(p);
        if (skillsPlayer == null)
            return;

        skillsPlayer.levelUp();

        SoundEffectsUtil.playLevelUpSound(p);
        p.sendActionBar(ColorParser.of(GuiHelper.POSITIVE + "<bold>+1 Skill Point").build());
    }
}
