package io.github.alathra.alathraskills.listeners.skills;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.skills.woodcutting.*;
import io.github.alathra.alathraskills.skills.woodcutting.util.OneSwing;
import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class WoodcuttingSkillsListener implements Listener {

    private SkillsPlayerManager skillsPlayerManager = AlathraSkills.getSkillsPlayerManager();

    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();
        Player player = event.getPlayer();

        SkillsPlayer skillsPlayer = skillsPlayerManager.getSkillPlayers().get(player.getUniqueId());

        if (PDCUtil.isUnnatural(block))
            return;

        if (Tag.LOGS.isTagged(material)) {
            if (skillsPlayer.getPlayerSkills().get(301).isSelected())
                SaveTheTreesSkill.runSaveTheTreesSkill(block);

            if (skillsPlayer.getPlayerSkills().get(302).isSelected()) {
                if (skillsPlayer.getPlayerSkills().get(303).isSelected()) {
                    PreciseChopTwoSkill.runPreciseChopTwoSkill(block);
                } else {
                    PreciseChopOneSkill.runPreciseChopOneSkill(block);
                }
            }

            if (OneSwing.oneSwingRunning(player)) {
                OneSwing.fellTree(block);
            } else if (OneSwing.oneSwingActive(player)) {
                if (skillsPlayer.getPlayerSkills().get(305).isSelected()) {
                    if (skillsPlayer.getPlayerSkills().get(309).isSelected()) {
                        OneSwing.runOneSwing(player, block, 2);
                    } else {
                        OneSwing.runOneSwing(player, block, 1);
                    }
                }
            }


        }

        if (Tag.LEAVES.isTagged(material)) {
            if (skillsPlayer.getPlayerSkills().get(304).isSelected()) {
                if (skillsPlayer.getPlayerSkills().get(307).isSelected()) {
                    TrimmerTwoSkill.runTrimmerTwoSkill(block);
                } else {
                    TrimmerOneSkill.runTrimmerOneSkill(block);
                }
            }

            if (skillsPlayer.getPlayerSkills().get(306).isSelected()) {
                if (skillsPlayer.getPlayerSkills().get(308).isSelected()) {
                    GroundskeeperTwoSkill.runGroundskeeperTwoSkill(event);
                } else {
                    GroundskeeperOneSkill.runGroundskeeperOneSkill(event);
                }
            }
        }
    }


    @EventHandler
    public void BlockDamageListener(BlockDamageEvent event) {
        SkillsPlayer skillsPlayer = skillsPlayerManager.getSkillPlayers().get(event.getPlayer().getUniqueId());

        if (!Tag.LEAVES.isTagged(event.getBlock().getType()))
            return;

        if (!Tag.ITEMS_AXES.isTagged(event.getItemInHand().getType()))
            return;

        if (!skillsPlayer.getPlayerSkills().get(304).isSelected())
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

            if (!Tag.ITEMS_AXES.isTagged(event.getMaterial()))
                return;

            // Return if right click logs
            if (Tag.LOGS.isTagged(event.getClickedBlock().getType()))
                return;

            Player player = event.getPlayer();

            if (skillsPlayer.getPlayerSkills().get(305).isSelected()) {
                if (OneSwing.hasOneSwingCooldown(player)) {
                    player.sendActionBar(ColorParser.of("<dark_red>One Swing isn't ready yet. Cooldown remaining: " + OneSwing.getRemainingCooldown(player) + " seconds.").build());
                    return;
                }
                OneSwing.readyOneSwing(player);
            }
        }
    }
}
