package io.github.alathra.alathraskills.listeners.skills;

import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.skills.mining.util.*;
import io.github.alathra.alathraskills.skills.mining.util.helper.MiningData;
import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class MiningSkillsListener implements Listener {

    // Calls "Ore in the Rough", "VeinBreaker" and "Proud Prospector" skills
    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();
        Player player = event.getPlayer();

        // Creative mode check
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        // PDC check
        if (PDCUtil.isUnnatural(block)) {
            return;
        }

        if (!event.isDropItems())
            return;

        SkillsPlayer skillsPlayer = SkillsPlayerManager.getSkillsPlayer(player);
        if (skillsPlayer == null)
            return;

        int i = 0;

        // ORE IN THE ROUGH SKILL
        if (MiningData.getNaturalStoneBlocks().contains(material)) {
            boolean[] oreInTheRough = new boolean[SkillsManager.oreInTheRoughIds.length];

            for (int id : SkillsManager.oreInTheRoughIds) {
                oreInTheRough[i] = skillsPlayer.playerHasSkill(id);
                i++;
            }

            i = 0;
            for (boolean hasSkill : oreInTheRough) {
                if (hasSkill) {
                    OreInTheRough.run(block, OreInTheRough.MAX_LEVEL - i);
                    break;
                }
                i++;
            }
        }

        // PROUD PROSPECTOR & VEIN BREAKER SKILL
        if (MiningData.getOres().contains(material)) {
            boolean[] proudProspector = new boolean[SkillsManager.proudProspectorIds.length];
            boolean[] veinBreaker = new boolean[SkillsManager.veinBreakerIds.length];

            i = 0;
            for (int id : SkillsManager.proudProspectorIds) {
                proudProspector[i] = skillsPlayer.playerHasSkill(id);
                i++;
            }

            i = 0;
            for (int id : SkillsManager.veinBreakerIds) {
                veinBreaker[i] = skillsPlayer.playerHasSkill(id);
                i++;
            }

            i = 0;
            for (boolean hasSkill : proudProspector) {
                if (hasSkill) {
                    ProudProspector.run(event, ProudProspector.MAX_LEVEL - i);
                    break;
                }
                i++;
            }

            i = 0;
            for (boolean hasSkill : veinBreaker) {
                if (hasSkill) {
                    VeinBreaker.run(block, player, OreInTheRough.MAX_LEVEL - i);
                    break;
                }
                i++;
            }
        }
    }

    // Calls the "Spelunker" skill
    @EventHandler
    public void FallDamageListener(EntityDamageEvent event) {

        // If entity is not a player
        if (!(event.getEntity() instanceof Player player)) {
            return;
        }

        // Creative mode check
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        // If the damage is not fall damage
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }

        SkillsPlayer skillsPlayer = SkillsPlayerManager.getSkillsPlayer(player);
        if (skillsPlayer == null)
            return;

        boolean[] spelunker = new boolean[SkillsManager.spelunkerIds.length];

        int i = 0;
        for (int id : SkillsManager.spelunkerIds) {
            spelunker[i] = skillsPlayer.playerHasSkill(id);
            i++;
        }

        i = 0;
        int j = 0;
        for (boolean hasSkill : spelunker) {
            if (hasSkill) {
                Spelunker.run(event, player, Spelunker.MAX_LEVEL - j);
                break;
            }

            // Handles Spelunker being in both branches
            if (i != 0) j++;
            i++;
        }
    }

    // calls "Easy Picking" skill
    @EventHandler
    public void PlayerInteractListener(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (event.getClickedBlock() == null) {
            return;
        }

        Player player = event.getPlayer();

        // Creative mode check
        if (player.getGameMode() == GameMode.CREATIVE) {
            return;
        }

        if (!MiningData.getPickaxes().contains(player.getInventory().getItemInMainHand().getType())) {
            return;
        }

        SkillsPlayer skillsPlayer = SkillsPlayerManager.getSkillsPlayer(player);
        if (skillsPlayer == null)
            return;

        boolean[] easyPicking = new boolean[SkillsManager.easyPickingIds.length];

        int i = 0;
        for (int id : SkillsManager.easyPickingIds) {
            easyPicking[i] = skillsPlayer.playerHasSkill(id);
            i++;
        }

        i = 0;
        for (boolean hasSkill : easyPicking) {
            if (hasSkill) {
                EasyPicking.run(player, event.getClickedBlock(), EasyPicking.MAX_LEVEL - i);
                break;
            }
            i++;
        }
    }
}
