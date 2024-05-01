package io.github.alathra.alathraskills.listeners.skills;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.skills.mining.util.OreInTheRough;
import io.github.alathra.alathraskills.skills.mining.util.Spelunker;
import io.github.alathra.alathraskills.skills.mining.util.VeinBreaker;
import io.github.alathra.alathraskills.skills.mining.util.helper.MiningData;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class MiningSkillsListener implements Listener {

    private SkillsPlayerManager skillsPlayerManager = AlathraSkills.getSkillsPlayerManager();

    // Calls "Ore in the Rough" skill
    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();
        Player player = event.getPlayer();

        SkillsPlayer skillsPlayer = skillsPlayerManager.getSkillPlayers().get(player.getUniqueId());


        // ORE IN THE ROUGH SKILL
        if (MiningData.getNaturalStoneBlocks().contains(material)) {
            OreInTheRough.run(block, 5);
        }

        boolean tagged = false;
        for (Tag tag : VeinBreaker.oreTags) {
            if (tag.isTagged(material)) {
                tagged = true;
                break;
            }
        }
        if (tagged) {
            if (skillsPlayer.getPlayerSkills().get(201).isSelected()) {
                if (VeinBreaker.veinBreakerRunning(player)) {
                    VeinBreaker.breakVein(block, player, 1);
                } else if (VeinBreaker.veinBreakerActive(player)) {
                    if (skillsPlayer.getPlayerSkills().get(202).isSelected()) {
                        if (skillsPlayer.getPlayerSkills().get(204).isSelected()) {
                            if (skillsPlayer.getPlayerSkills().get(2110).isSelected()) {
                                VeinBreaker.runVeinBreaker(player, block, 3);
                            } else {
                                VeinBreaker.runVeinBreaker(player, block, 2);
                            }
                        } else {
                            VeinBreaker.runVeinBreaker(player, block, 1);
                        }
                    }
                }
            }
        }
    }


    @EventHandler
    public void BlockDamageListener(BlockDamageEvent event) {
        SkillsPlayer skillsPlayer = skillsPlayerManager.getSkillPlayers().get(event.getPlayer().getUniqueId());

        if (!Tag.ITEMS_PICKAXES.isTagged(event.getItemInHand().getType()))
            return;

        boolean tagged = false;
        for (Tag tag : VeinBreaker.oreTags) {
            if (tag.isTagged(event.getBlock().getType())) {
                tagged = true;
                break;
            }
        }
        if (!tagged)
            return;

        if (!skillsPlayer.getPlayerSkills().get(204).isSelected())
            return;

        event.setInstaBreak(true);
    }

    @EventHandler
    public void RightClickListener(PlayerInteractEvent event) {
        SkillsPlayer skillsPlayer = skillsPlayerManager.getSkillPlayers().get(event.getPlayer().getUniqueId());

        if (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if (event.getHand() == EquipmentSlot.OFF_HAND)
                return;

            if (!event.hasItem())
                return;

            if (!Tag.ITEMS_PICKAXES.isTagged(event.getMaterial()))
                return;

            // Return if right click logs

            Player player = event.getPlayer();

            boolean tagged = false;
            for (Tag tag : VeinBreaker.oreTags) {
                if (tag.isTagged(event.getClickedBlock().getType())) {
                    tagged = true;
                    break;
                }
            }
            if (!tagged)
                return;

            if (skillsPlayer.getPlayerSkills().get(202).isSelected()) {
                if (VeinBreaker.hasVeinBreakerCooldown(player)) {
                    player.sendActionBar(ColorParser.of("<dark_red>Vein Breaker isn't ready yet. Cooldown remaining: " + VeinBreaker.getRemainingCooldown(player) + " seconds.").build());
                    return;
                }
                VeinBreaker.readyVeinBreaker(player);
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

        // If the damage is not fall damage
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }

        Spelunker.run(event, player, 4);

    }
}
