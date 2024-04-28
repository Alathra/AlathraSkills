package io.github.alathra.alathraskills.listeners.skills;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.skills.farming.util.*;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.*;
import org.bukkit.event.entity.EntityBreedEvent;

public class FarmingSkillsListener implements Listener {

    private SkillsPlayerManager skillsPlayerManager = AlathraSkills.getSkillsPlayerManager();

    // calls "Ready to Eat" skill and "Faster Harvest"
    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        // Check for creative mode and cancel
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        // if potato, carrot, beetroot or wheat crop
        if (FarmingData.getStandardCrops().contains(block.getType())) {
            ReadyToEat.run(block, 2);
        }

        // if block broken is one of the breakable crops (specifically defined)
        if (FarmingData.getBreakableCrops().contains(block.getType())) {
            FastHarvest.run(block, player, 7);
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
            WideSpread.run(block, player, 7);
        }
    }

    // calls "Green Thumb" skill
    @EventHandler
    public void BlockFertilizeListener(BlockFertilizeEvent event) {

        // If fired by API and not the initial bone meal, return
        if (event.getPlayer() == null) {
            return;
        }

        // Check for creative mode and cancel
        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }

        Block block = event.getBlock();

        // if the block grown is a desired bonemealable crop (specifically defined)
        if (FarmingData.getBonemealableCrops().contains(block.getType())) {
            GreenThumb.run(block, 6);
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
            QualityCrops.run(entity, 6);
        }
    }

}
