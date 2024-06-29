package io.github.alathra.alathraskills.listeners.skills;

import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.skills.woodcutting.util.*;
import io.github.alathra.alathraskills.skills.woodcutting.util.helper.WoodcuttingData;
import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.GameMode;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.world.StructureGrowEvent;
import org.bukkit.inventory.EquipmentSlot;

public class WoodcuttingSkillsListener implements Listener {

    // calls "Precise Chop", "Save the Trees" and "One Swing"
    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE)
            return;
        if (PDCUtil.isUnnatural(block))
            return;
        if (event.isCancelled())
            return;
        if (!event.isDropItems())
            return;

        SkillsPlayer skillsPlayer = SkillsPlayerManager.getSkillsPlayer(player);
        if (skillsPlayer == null)
            return;

        if (Tag.LOGS.isTagged(block.getType())) {
            int i = 0;
            if (skillsPlayer.isSkillEnabled(SkillsManager.preciseChopIds[0])) {
                boolean[] preciseChop = new boolean[SkillsManager.preciseChopIds.length];

                for (int id : SkillsManager.preciseChopIds) {
                    preciseChop[i] = skillsPlayer.playerHasSkill(id);
                    i++;
                }

                i = 0;
                for (boolean hasSkill : preciseChop) {
                    if (hasSkill) {
                        PreciseChop.run(block, PreciseChop.MAX_LEVEL - i);
                        break;
                    }
                    i++;
                }
            }

            if (skillsPlayer.isSkillEnabled(SkillsManager.oneSwingIds[0])) {
                boolean[] oneSwing = new boolean[SkillsManager.oneSwingIds.length];

                i = 0;

                for (int id : SkillsManager.oneSwingIds) {
                    oneSwing[i] = skillsPlayer.playerHasSkill(id);
                    i++;
                }

                i = 0;
                for (boolean hasSkill : oneSwing) {
                    if (hasSkill) {
                        OneSwing.run(player, block, OneSwing.MAX_LEVEL - i);
                        break;
                    }
                    i++;
                }
            }

            if (Tag.DIRT.isTagged(block.getRelative(BlockFace.DOWN).getType())) {
                if (!skillsPlayer.playerHasSkill(301)) return;
                if (!skillsPlayer.isSkillEnabled(SkillsManager.saveTheTreesId)) return;
                SaveTheTrees.run(block, player);
            }
        }

    }

    // calls "Trimmer"
    @EventHandler
    public void BlockDamageListener(BlockDamageEvent event) {


        if (event.getPlayer().getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (!Tag.LEAVES.isTagged(event.getBlock().getType())) {
            return;
        }

        if (!Tag.ITEMS_AXES.isTagged(event.getItemInHand().getType())) {
            return;
        }

        if (PDCUtil.isUnnatural(event.getBlock())) {
            return;
        }

        SkillsPlayer skillsPlayer = SkillsPlayerManager.getSkillsPlayer(event.getPlayer());
        if (skillsPlayer == null)
            return;

        if (skillsPlayer.isSkillEnabled(SkillsManager.trimmerIds[0])) {
            boolean[] trimmer = new boolean[SkillsManager.trimmerIds.length];

            int i = 0;

            for (int id : SkillsManager.trimmerIds) {
                trimmer[i] = skillsPlayer.playerHasSkill(id);
                i++;
            }

            for (boolean hasSkill : trimmer) {
                if (hasSkill) {
                    Trimmer.run(event, Trimmer.MAX_LEVEL - i);
                    break;
                }
                i++;
            }
        }
    }

    // used to activate "One Swing"
    @EventHandler
    public void RightClickListener(PlayerInteractEvent event) {

        Player player = event.getPlayer();

        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        // event only needs to be run once
        if (event.getHand() == EquipmentSlot.OFF_HAND) {
            return;
        }
        // if no item in main hand
        if (!event.hasItem()) {
            return;
        }
        // if the item in main hand is not an axe
        if (!Tag.ITEMS_AXES.isTagged(event.getItem().getType())) {
            return;
        }
        // if not a right click, return;
        if (event.getAction() == Action.RIGHT_CLICK_BLOCK || event.getAction() == Action.RIGHT_CLICK_AIR) {
            // make sure stripping logs does not activate skill
            if (event.getClickedBlock() != null) {
                if (Tag.LOGS.isTagged(event.getClickedBlock().getType())) {
                    return;
                }
            }

            boolean[] oneSwing = new boolean[SkillsManager.oneSwingIds.length];

            SkillsPlayer skillsPlayer = SkillsPlayerManager.getSkillsPlayer(player);
            if (skillsPlayer == null)
                return;

            int i = 0;
            for (int id : SkillsManager.oneSwingIds) {
                oneSwing[i] = skillsPlayer.playerHasSkill(id);
                i++;
            }

            i = 0;
            for (boolean hasSkill : oneSwing) {
                if (hasSkill) {
                    OneSwing.activate(player, OneSwing.MAX_LEVEL - i);
                    break;
                }
                i++;
            }
        }
    }

    // used to call "One with the Forest"
    @EventHandler
    public void onTreeGrow(StructureGrowEvent event) {
        Player player = event.getPlayer();

        // if naturally grown
        if (!event.isFromBonemeal()) {
            return;
        }

        // if a non-player grows the tree with bonemeal
        if (player == null) {
            return;
        } else {
            if (player.getGameMode() == GameMode.CREATIVE) {
                return;
            }
        }

        // if structure grown is not a tree
        if (WoodcuttingData.getNonTrees().contains(event.getSpecies())) {
            return;
        }


        SkillsPlayer skillsPlayer = SkillsPlayerManager.getSkillsPlayer(player);
        if (skillsPlayer == null)
            return;

        if (skillsPlayer.isSkillEnabled(SkillsManager.oneWithTheForestIds[0])) {
            boolean[] oneWithTheForest = new boolean[SkillsManager.oneWithTheForestIds.length];

            int i = 0;
            for (int id : SkillsManager.oneWithTheForestIds) {
                oneWithTheForest[i] = skillsPlayer.playerHasSkill(id);
                i++;
            }

            i = 0;
            for (boolean hasSkill : oneWithTheForest) {
                if (hasSkill) {
                    OneWithTheForest.run(event, OneWithTheForest.MAX_LEVEL - i);
                    break;
                }
                i++;
            }
        }
    }
}
