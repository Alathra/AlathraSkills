package io.github.alathra.alathraskills.listeners.skills;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
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

    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();
        Player player = event.getPlayer();

        // TODO Check if block was placed by player

        if (Tag.LOGS.isTagged(material)) {
            // TODO check if player has skills before calling function
            SaveTheTreesSkill.saveTheTreeSkillRun(block);
            PreciseChopOneSkill.preciseChopOneSkillRun(block);
            PreciseChopTwoSkill.preciseChopTwoSkillRun(block);

            if (skillsManager.oneSwingActive(player)) {
                OneSwingOneSkill.runOneSwingSkill(player, block);
            }

            if (skillsManager.oneSwingRunning(player)) {
                OneSwing.fellTree(block);
            }
        }

        if (Tag.LEAVES.isTagged(material)) {
            TrimmerOneSkill.trimmerOneSkillRun(block);
        }
    }


    // TODO check if player has trimmer skill
    @EventHandler
    public void BlockDamageListener(BlockDamageEvent event) {
        if (!Tag.LEAVES.isTagged(event.getBlock().getType()))
            return;

        if (!Tag.ITEMS_AXES.isTagged(event.getItemInHand().getType()))
            return;

        event.setInstaBreak(true);
    }

    @EventHandler
    public void RightClickListener(PlayerInteractEvent event) {
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

        // TODO check if player has skill
        OneSwingOneSkill.readyOneSwingOneSkill(player);
    }
}
