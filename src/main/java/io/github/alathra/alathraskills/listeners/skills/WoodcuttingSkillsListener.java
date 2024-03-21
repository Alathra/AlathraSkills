package io.github.alathra.alathraskills.listeners.skills;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.skills.woodcutting.*;
import io.github.alathra.alathraskills.skills.woodcutting.util.OneSwing;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class WoodcuttingSkillsListener implements Listener {

    private SkillsManager skillsManager = AlathraSkills.getSkillsManager();
    private SkillsPlayerManager skillsPlayerManager = AlathraSkills.getSkillsPlayerManager();

    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();
        Player player = event.getPlayer();

        SkillsPlayer skillsPlayer = skillsPlayerManager.getSkillPlayers().get(player.getUniqueId());

        // TODO Check if block was placed by player

        if (Tag.LOGS.isTagged(material)) {
            if (skillsPlayer.getPlayerSkills().get(301))
                SaveTheTreesSkill.saveTheTreeSkillRun(block);

            if (skillsPlayer.getPlayerSkills().get(302)) {
                if (skillsPlayer.getPlayerSkills().get(303)) {
                    PreciseChopTwoSkill.preciseChopTwoSkillRun(block);
                }
                PreciseChopOneSkill.preciseChopOneSkillRun(block);
            }

            if (skillsPlayer.getPlayerSkills().get(305)) {
                if (skillsManager.oneSwingActive(player)) {
                    OneSwingOneSkill.runOneSwingSkill(player, block);
                }

                if (skillsManager.oneSwingRunning(player)) {
                    OneSwing.fellTree(block);
                }
            }
        }

        if (Tag.LEAVES.isTagged(material)) {
            if (skillsPlayer.getPlayerSkills().get(304))
                TrimmerOneSkill.trimmerOneSkillRun(block);
        }
    }


    @EventHandler
    public void BlockDamageListener(BlockDamageEvent event) {
        SkillsPlayer skillsPlayer = skillsPlayerManager.getSkillPlayers().get(event.getPlayer().getUniqueId());

        if (!Tag.LEAVES.isTagged(event.getBlock().getType()))
            return;

        if (!Tag.ITEMS_AXES.isTagged(event.getItemInHand().getType()))
            return;

        if (!skillsPlayer.getPlayerSkills().get(304))
            return;

        event.setInstaBreak(true);
    }

    @EventHandler
    public void RightClickListener(PlayerInteractEvent event) {
        SkillsPlayer skillsPlayer = skillsPlayerManager.getSkillPlayers().get(event.getPlayer().getUniqueId());

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

        if (skillsPlayer.getPlayerSkills().get(305))
            OneSwingOneSkill.readyOneSwingOneSkill(player);
    }
}
