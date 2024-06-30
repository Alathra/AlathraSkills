package io.github.alathra.alathraskills.listeners.exp;

import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.api.events.SkillPointGainEvent;
import io.github.alathra.alathraskills.utility.Cfg;
import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.block.data.type.CaveVines;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerHarvestBlockEvent;

public class FarmingExpListener implements Listener {

    @EventHandler
    public void BerriesHarvestingListener(PlayerHarvestBlockEvent event) {
        Player p = event.getPlayer();
        if (p == null) {
            return;
        }

        Block block = event.getHarvestedBlock();

        float expAmount = 0.0f;

        switch (block.getType()) {
            case CAVE_VINES:
                expAmount = Float.parseFloat(Cfg.getValue("experience.farming.caveVine").toString());
                break;
            case SWEET_BERRY_BUSH:
                expAmount = Float.parseFloat(Cfg.getValue("experience.farming.sweetBerryBush").toString());
                break;
            default:
                return;
        }

        if (SkillsPlayerManager.isSkillPointGained(p, expAmount)) {
            Bukkit.getPluginManager().callEvent(new SkillPointGainEvent(SkillsPlayerManager.getSkillsPlayer(p)));
        }
        SkillsPlayerManager.addPlayerExperience(event.getPlayer(), SkillsManager.FARMING_SKILL_ID, expAmount);
    }

    @EventHandler
    public void FarmHarvestingListener(BlockBreakEvent event) {

        Player p = event.getPlayer();
        if (p == null) {
            return;
        }

        Block block = event.getBlock();
        float expAmount = 0.0f;

        Ageable ageable = null;

        if (block.getBlockData() instanceof Ageable)
            ageable = (Ageable) block.getBlockData();

        // TODO: Check for PDC data where relevant

        switch (block.getType()) {
            case BAMBOO:
                if (PDCUtil.isUnnatural(block)) return;

                expAmount = Float.parseFloat(Cfg.getValue("experience.farming.bamboo").toString());
                break;
            case BROWN_MUSHROOM, RED_MUSHROOM:
                if (PDCUtil.isUnnatural(block)) return;

                expAmount = Float.parseFloat(Cfg.getValue("experience.farming.mushroom").toString());
                break;
            case CACTUS:
                if (PDCUtil.isUnnatural(block)) return;

                expAmount = Float.parseFloat(Cfg.getValue("experience.farming.cactus").toString());
                break;
            case CARROTS:
                if (ageable.getMaximumAge() == ageable.getAge())
                    expAmount = Float.parseFloat(Cfg.getValue("experience.farming.carrot").toString());
                break;
            case POTATOES:
                if (ageable.getMaximumAge() == ageable.getAge())
                    expAmount = Float.parseFloat(Cfg.getValue("experience.farming.potato").toString());
                break;
            case BEETROOTS:
                if (ageable.getMaximumAge() == ageable.getAge())
                    expAmount = Float.parseFloat(Cfg.getValue("experience.farming.beetroot").toString());
                break;
            case WHEAT:
                if (ageable.getMaximumAge() == ageable.getAge())
                    expAmount = Float.parseFloat(Cfg.getValue("experience.farming.wheat").toString());
                break;
            case CAVE_VINES:
                CaveVines caveVines = (CaveVines) block.getBlockData();
                if (caveVines.isBerries())
                    expAmount = Float.parseFloat(Cfg.getValue("experience.farming.caveVineBlock").toString());
                break;
            case COCOA:
                if (ageable.getMaximumAge() == ageable.getAge())
                    expAmount = Float.parseFloat(Cfg.getValue("experience.farming.cocoa").toString());
                break;
            case KELP:
                if (PDCUtil.isUnnatural(block)) return;

                expAmount = Float.parseFloat(Cfg.getValue("experience.farming.kelp").toString());
                break;
            case MELON:
                if (PDCUtil.isUnnatural(block)) return;

                expAmount = Float.parseFloat(Cfg.getValue("experience.farming.melon").toString());
                break;
            case PUMPKIN:
                if (PDCUtil.isUnnatural(block)) return;

                expAmount = Float.parseFloat(Cfg.getValue("experience.farming.pumpkin").toString());
                break;
            case NETHER_WART:
                if (ageable.getMaximumAge() == ageable.getAge())
                    expAmount = Float.parseFloat(Cfg.getValue("experience.farming.netherWart").toString());
                break;
            case SUGAR_CANE:
                if (PDCUtil.isUnnatural(block)) return;

                expAmount = Float.parseFloat(Cfg.getValue("experience.farming.sugarCane").toString());
                break;
            case SWEET_BERRY_BUSH:
                if (ageable.getMaximumAge() == ageable.getAge())
                    expAmount = Float.parseFloat(Cfg.getValue("experience.farming.sweetBerryBushBlock").toString());
                break;
            default:
                return;
        }

        if (SkillsPlayerManager.isSkillPointGained(p, expAmount)) {
            Bukkit.getPluginManager().callEvent(new SkillPointGainEvent(SkillsPlayerManager.getSkillsPlayer(p)));
        }
        SkillsPlayerManager.addPlayerExperience(event.getPlayer(), SkillsManager.FARMING_SKILL_ID, expAmount);
    }
}
