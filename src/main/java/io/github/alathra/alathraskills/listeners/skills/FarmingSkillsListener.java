package io.github.alathra.alathraskills.listeners.skills;

import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.skills.farming.util.*;
import io.github.alathra.alathraskills.skills.farming.util.helper.FarmingData;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityBreedEvent;

public class FarmingSkillsListener implements Listener {

    // calls "Ready to Eat" skill and "Faster Harvest"
    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        // Check for creative mode and cancel
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (!event.isDropItems())
            return;

        SkillsPlayer skillsPlayer = SkillsPlayerManager.getSkillsPlayer(player);
        if (skillsPlayer == null)
            return;

        // if potato, carrot, beetroot or wheat crop
        if (FarmingData.getStandardCrops().contains(block.getType())) {
            boolean[] readyToEat = new boolean[SkillsManager.readyToEatIds.length];

            int i = 0;
            for (int id : SkillsManager.readyToEatIds) {
                readyToEat[i] = skillsPlayer.playerHasSkill(id);
                i++;
            }

            i = 0;
            int j = 0;
            for (boolean hasSkill : readyToEat) {
                if (hasSkill) {
                    ReadyToEat.run(block, ReadyToEat.MAX_LEVEL - j);
                    break;
                }

                // handles ready to eat being in both branches of the skill tree
                if (i == 1) j++;
                i++;
            }
        }

        // If player is holding a hoe
        if (Tag.ITEMS_HOES.isTagged(event.getPlayer().getInventory().getItemInMainHand().getType())) {
            // if block broken is one of the breakable crops (specifically defined)
            if (skillsPlayer.isSkillEnabled(SkillsManager.fastHarvestIds[SkillsManager.fastHarvestIds.length - 1])) {
                if (FarmingData.getBreakableCrops().contains(block.getType())) {
                    boolean[] fastHarvest = new boolean[SkillsManager.fastHarvestIds.length];

                    int i = 0;
                    for (int id : SkillsManager.fastHarvestIds) {
                        fastHarvest[i] = skillsPlayer.playerHasSkill(id);
                        i++;
                    }

                    i = 0;
                    for (boolean hasSkill : fastHarvest) {
                        if (hasSkill) {
                            FastHarvest.run(block, player, FastHarvest.MAX_LEVEL - i);
                            break;
                        }
                        i++;
                    }
                }
            }
        }
    }

    // calls "Wide Spread" skill
    @EventHandler
    public void BlockPlaceListener(BlockPlaceEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        // Check for creative mode and cancel
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        // Return if the block wasn't placed on top of farmland
        if (block.getRelative(BlockFace.DOWN).getType() != Material.FARMLAND) {
            return;
        }

        // if potato, carrot, beetroot or wheat crop placed
        if (FarmingData.getStandardCrops().contains(block.getType())) {
            SkillsPlayer skillsPlayer = SkillsPlayerManager.getSkillsPlayer(player);
            if (skillsPlayer == null)
                return;

            if (!skillsPlayer.isSkillEnabled(SkillsManager.wideSpread[SkillsManager.wideSpread.length - 1]))
                return;

            boolean[] wideSpread = new boolean[SkillsManager.wideSpread.length];

            int i = 0;
            for (int id : SkillsManager.wideSpread) {
                wideSpread[i] = skillsPlayer.playerHasSkill(id);
                i++;
            }

            i = 0;
            for (boolean hasSkill : wideSpread) {
                if (hasSkill) {
                    WideSpread.run(block, player, WideSpread.MAX_LEVEL - i);
                    break;
                }
                i++;
            }
        }
    }

    // calls "Green Thumb" skill
    @EventHandler
    public void BlockFertilizeListener(BlockFertilizeEvent event) {

        Player player = event.getPlayer();

        // If fired by API and not the initial bone meal, return
        if (player == null) {
            return;
        }

        // Check for creative mode and cancel
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        Block block = event.getBlock();

        // if the block grown is a desired bonemealable crop (specifically defined)
        if (FarmingData.getBonemealableCrops().contains(block.getType())) {
            SkillsPlayer skillsPlayer = SkillsPlayerManager.getSkillsPlayer(player);
            if (skillsPlayer == null)
                return;

            if (!skillsPlayer.isSkillEnabled(SkillsManager.greenThumbIds[SkillsManager.greenThumbIds.length - 1]))
                return;

            boolean[] greenThumb = new boolean[SkillsManager.greenThumbIds.length];

            int i = 0;
            for (int id : SkillsManager.greenThumbIds) {
                greenThumb[i] = skillsPlayer.playerHasSkill(id);
                i++;
            }

            i = 0;
            for (boolean hasSkill : greenThumb) {
                if (hasSkill) {
                    GreenThumb.run(block, GreenThumb.MAX_LEVEL - i);
                    break;
                }
                i++;
            }
        }
    }

    // calls "Quality Crops" skill
    @EventHandler
    public void AnimalBreedListener(EntityBreedEvent event) {
        LivingEntity entity = event.getEntity();

        if (event.getBreeder() instanceof Player player) {
            if (player.getGameMode() == GameMode.CREATIVE) {
                return;
            }
        }

        // if entity bred is one of the standard animals (specifically defined)
        if (FarmingData.getStandardAnimals().contains(entity.getType())) {
            SkillsPlayer skillsPlayer = SkillsPlayerManager.getSkillsPlayer((Player) event.getBreeder());
            if (skillsPlayer == null)
                return;

            if (skillsPlayer.isSkillEnabled(SkillsManager.qualityCropsIds[SkillsManager.qualityCropsIds.length - 1])) {
                boolean[] qualityCrops = new boolean[SkillsManager.qualityCropsIds.length];

                int i = 0;
                for (int id : SkillsManager.qualityCropsIds) {
                    qualityCrops[i] = skillsPlayer.playerHasSkill(id);
                    i++;
                }

                i = 0;
                for (boolean hasSkill : qualityCrops) {
                    if (hasSkill) {
                        QualityCrops.run(entity, QualityCrops.MAX_LEVEL - i);
                        break;
                    }
                    i++;
                }
            }
        }
    }

}
