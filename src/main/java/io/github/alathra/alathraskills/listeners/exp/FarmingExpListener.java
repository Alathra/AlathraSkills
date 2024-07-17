package io.github.alathra.alathraskills.listeners.exp;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.api.SkillsPlayer;
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

        if (event.isCancelled())
            return;

        Player p = event.getPlayer();

        Block block = event.getHarvestedBlock();

        float expAmount = 0.0f;

        switch (block.getType()) {
            case CAVE_VINES:
                expAmount = Cfg.get().getFloat("experience.farming.caveVine");
                break;
            case SWEET_BERRY_BUSH:
                expAmount = Cfg.get().getFloat("experience.farming.sweetBerryBush");
                break;
            default:
                return;
        }

        AlathraSkills.getSkillsPlayerManager().gainExp(p, SkillsManager.FARMING_SKILL_ID, expAmount);
    }

    @EventHandler
    public void FarmHarvestingListener(BlockBreakEvent event) {

        if (event.isCancelled())
            return;

        Player p = event.getPlayer();

        Block block = event.getBlock();
        float expAmount = 0.0f;

        Ageable ageable = null;

        if (block.getBlockData() instanceof Ageable)
            ageable = (Ageable) block.getBlockData();

        // TODO: Check for PDC data where relevant

        switch (block.getType()) {
            case BAMBOO:
                if (PDCUtil.isUnnatural(block)) return;

                expAmount = Cfg.get().getFloat("experience.farming.bamboo");
                break;
            case BROWN_MUSHROOM, RED_MUSHROOM:
                if (PDCUtil.isUnnatural(block)) return;

                expAmount = Cfg.get().getFloat("experience.farming.mushroom");
                break;
            case CACTUS:
                if (PDCUtil.isUnnatural(block)) return;

                expAmount = Cfg.get().getFloat("experience.farming.cactus");
                break;
            case CARROTS:
                if (ageable.getMaximumAge() == ageable.getAge())
                    expAmount = Cfg.get().getFloat("experience.farming.carrot");
                break;
            case POTATOES:
                if (ageable.getMaximumAge() == ageable.getAge())
                    expAmount = Cfg.get().getFloat("experience.farming.potato");
                break;
            case BEETROOTS:
                if (ageable.getMaximumAge() == ageable.getAge())
                    expAmount = Cfg.get().getFloat("experience.farming.beetroot");
                break;
            case WHEAT:
                if (ageable.getMaximumAge() == ageable.getAge())
                    expAmount = Cfg.get().getFloat("experience.farming.wheat");
                break;
            case CAVE_VINES:
                CaveVines caveVines = (CaveVines) block.getBlockData();
                if (caveVines.isBerries())
                    expAmount = Cfg.get().getFloat("experience.farming.caveVineBlock");
                break;
            case COCOA:
                if (ageable.getMaximumAge() == ageable.getAge())
                    expAmount = Cfg.get().getFloat("experience.farming.cocoa");
                break;
            case KELP:
                if (PDCUtil.isUnnatural(block)) return;

                expAmount = Cfg.get().getFloat("experience.farming.kelp");
                break;
            case MELON:
                if (PDCUtil.isUnnatural(block)) return;

                expAmount = Cfg.get().getFloat("experience.farming.melon");
                break;
            case PUMPKIN:
                if (PDCUtil.isUnnatural(block)) return;

                expAmount = Cfg.get().getFloat("experience.farming.pumpkin");
                break;
            case NETHER_WART:
                if (ageable.getMaximumAge() == ageable.getAge())
                    expAmount = Cfg.get().getFloat("experience.farming.netherWart");
                break;
            case SUGAR_CANE:
                if (PDCUtil.isUnnatural(block)) return;

                expAmount = Cfg.get().getFloat("experience.farming.sugarCane");
                break;
            case SWEET_BERRY_BUSH:
                if (ageable.getMaximumAge() == ageable.getAge())
                    expAmount = Cfg.get().getFloat("experience.farming.sweetBerryBushBlock");
                break;
            default:
                return;
        }

        AlathraSkills.getSkillsPlayerManager().gainExp(p, SkillsManager.FARMING_SKILL_ID, expAmount);
    }
}
