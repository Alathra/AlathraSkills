package io.github.alathra.alathraskills.listeners.skills;

import io.github.alathra.alathraskills.skills.woodcutting.PreciseChopOneSkill;
import io.github.alathra.alathraskills.skills.woodcutting.PreciseChopTwoSkill;
import io.github.alathra.alathraskills.skills.woodcutting.SaveTheTreesSkill;
import io.github.alathra.alathraskills.skills.woodcutting.TrimmerOneSkill;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockDamageEvent;

public class WoodcuttingSkillsListener implements Listener {

    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();

        // TODO Check if block was placed by player

        if (Tag.LOGS.isTagged(material)) {
            // TODO check if player has skills before calling function
            SaveTheTreesSkill.saveTheTreeSkillRun(block);
            PreciseChopOneSkill.preciseChopOneSkill(block);
            PreciseChopTwoSkill.preciseChopTwoSkill(block);
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
}
