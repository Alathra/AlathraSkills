package io.github.alathra.alathraskills.listeners.skills;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.skills.woodcutting.*;
import io.github.alathra.alathraskills.skills.woodcutting.util.Groundskeeper;
import io.github.alathra.alathraskills.skills.woodcutting.util.OneSwing;
import io.github.alathra.alathraskills.skills.woodcutting.util.PreciseChop;
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

    // TODO: clean up skill check logic
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
                    if (skillsPlayer.getPlayerSkills().get(310).isSelected()) {
                        PreciseChop.runPreciseChopSkill(block, 0.2);
                    }
                    PreciseChop.runPreciseChopSkill(block, 0.1);
                } else {
                    PreciseChop.runPreciseChopSkill(block, 0.05);
                }
            }

            if (OneSwing.oneSwingRunning(player)) {
                OneSwing.fellTree(block);
            } else if (OneSwing.oneSwingActive(player)) {
                if (skillsPlayer.getPlayerSkills().get(305).isSelected()) {
                    if (skillsPlayer.getPlayerSkills().get(309).isSelected()) {
                        if (skillsPlayer.getPlayerSkills().get(3112).isSelected()) {
                            if (skillsPlayer.getPlayerSkills().get(3114).isSelected()) {
                                if (skillsPlayer.getPlayerSkills().get(3115).isSelected()) {
                                    OneSwing.runOneSwing(player, block, 5);
                                } else {
                                    OneSwing.runOneSwing(player, block, 4);
                                }
                            } else {
                                OneSwing.runOneSwing(player, block, 3);
                            }
                        } else {
                            OneSwing.runOneSwing(player, block, 2);
                        }
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
                    if (skillsPlayer.getPlayerSkills().get(3111).isSelected()) {
                        if (skillsPlayer.getPlayerSkills().get(3113).isSelected()) {
                            if (skillsPlayer.getPlayerSkills().get(3116).isSelected()) {
                                Groundskeeper.runGroundskeeperSkill(event, 3, 3, 3, 0.55, 7);
                            } else {
                                Groundskeeper.runGroundskeeperSkill(event, 2, 3, 2, 0.45, 3);
                            }
                        } else {
                            Groundskeeper.runGroundskeeperSkill(event, 2, 3, 2, 0.35, 3);
                        }
                    } else {
                        Groundskeeper.runGroundskeeperSkill(event, 2, 2, 2, 0.25, 2);
                    }
                } else {
                    Groundskeeper.runGroundskeeperSkill(event, 2, 2, 2, 0.15, 2);
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
