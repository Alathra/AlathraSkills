package io.github.alathra.alathraskills.listeners.player;

import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.events.SkillsPlayerLoadedEvent;
import io.github.alathra.alathraskills.utility.Logger;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.nossr50.api.exceptions.McMMOPlayerNotFoundException;
import com.gmail.nossr50.datatypes.experience.XPGainReason;
import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.datatypes.skills.PrimarySkillType;
import com.gmail.nossr50.util.EventUtils;
import com.gmail.nossr50.util.player.UserManager;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.utility.Cfg;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

public class PlayerJoinListener implements Listener {
	
	public PlayerJoinListener() {
	}

	@EventHandler(priority = EventPriority.HIGHEST)
	public void onPlayerJoin(PlayerJoinEvent e) {
        final Player p = e.getPlayer();

        SkillsPlayer skillsPlayer = null;
    	CompletableFuture<SkillsPlayer> future = SkillsPlayerManager.handlePlayerJoin(p);
        try {
            skillsPlayer = future.get();
        } catch (InterruptedException | ExecutionException ex) {
            Logger.get().error("Something went wrong: " + ex);
        }

        SkillsPlayerLoadedEvent firedEvent = new SkillsPlayerLoadedEvent(p.getUniqueId(), skillsPlayer);
        Bukkit.getPluginManager().callEvent(firedEvent);

        new BukkitRunnable() {
            public void run() {
                McMMOPlayer playerMcMMO;
                try {
                	playerMcMMO = UserManager.getPlayer(p);
                } catch (McMMOPlayerNotFoundException exception){
                	System.out.println("Player not found. " + exception.toString());
                    return;
                }

                float maxSkillLevel = Float.parseFloat(Cfg.getValue("mcmmo.maxSkillLevel").toString());
                float conversionFactor = Float.parseFloat(Cfg.getValue("mcmmo.conversionFactor").toString());

                if (playerMcMMO == null) return;
                float skillLevelFarming = 1.0f * playerMcMMO.getHerbalismManager().getSkillLevel();
                float skillLevelMining = 1.0f * playerMcMMO.getMiningManager().getSkillLevel();
                float skillLevelWoodcutting = 1.0f * playerMcMMO.getWoodcuttingManager().getSkillLevel();

                if (!(skillLevelFarming > 0.0f || skillLevelMining > 0.0f || skillLevelWoodcutting > 0.0f)) {
                	// Short circuit due to no levels needing to be transfered
                	return;
                }

                float finalExperienceFarming = (skillLevelFarming / maxSkillLevel) * conversionFactor;
                float finalExperienceMining = (skillLevelMining / maxSkillLevel) * conversionFactor;
                float finalExperienceWoodcutting = (skillLevelWoodcutting / maxSkillLevel) * conversionFactor;

                SkillsPlayerManager.setPlayerExperience(p, 1, finalExperienceFarming);
                SkillsPlayerManager.setPlayerExperience(p, 2, finalExperienceMining);
                SkillsPlayerManager.setPlayerExperience(p, 3, finalExperienceWoodcutting);

                playerMcMMO.modifySkill(PrimarySkillType.HERBALISM, 0);
                playerMcMMO.modifySkill(PrimarySkillType.MINING, 0);
                playerMcMMO.modifySkill(PrimarySkillType.WOODCUTTING, 0);

                int levelsRemovedHerbalism = playerMcMMO.getProfile().getSkillLevel(PrimarySkillType.HERBALISM);
                int levelsRemovedMining = playerMcMMO.getProfile().getSkillLevel(PrimarySkillType.MINING);
                int levelsRemovedWoodcutting = playerMcMMO.getProfile().getSkillLevel(PrimarySkillType.WOODCUTTING);
                float xpRemovedHerbalism = playerMcMMO.getProfile().getSkillXpLevelRaw(PrimarySkillType.HERBALISM);
                float xpRemovedMining = playerMcMMO.getProfile().getSkillXpLevelRaw(PrimarySkillType.MINING);
                float xpRemovedWoodcutting = playerMcMMO.getProfile().getSkillXpLevelRaw(PrimarySkillType.WOODCUTTING);

                EventUtils.tryLevelChangeEvent(playerMcMMO, PrimarySkillType.HERBALISM, levelsRemovedHerbalism, xpRemovedHerbalism, false,  XPGainReason.COMMAND);
                EventUtils.tryLevelChangeEvent(playerMcMMO, PrimarySkillType.HERBALISM, levelsRemovedMining, xpRemovedMining, false,  XPGainReason.COMMAND);
                EventUtils.tryLevelChangeEvent(playerMcMMO, PrimarySkillType.HERBALISM, levelsRemovedWoodcutting, xpRemovedWoodcutting, false,  XPGainReason.COMMAND);
            }
        }.runTaskLater(AlathraSkills.getInstance(), 200);

    }

}
