package io.github.alathra.alathraskills.listeners.skills;

import io.github.alathra.alathraskills.skills.woodcutting.PreciseChopOneSkill;
import io.github.alathra.alathraskills.skills.woodcutting.SaveTheTreesSkill;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class WoodcuttingSkillsListener implements Listener {

    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {
        Block block = event.getBlock();

        if (!Tag.LOGS.isTagged(block.getType()))
            return;

        // TODO Check if block was placed by player

        // TODO check if player has skills before calling function
        SaveTheTreesSkill.saveTheTreeSkillRun(block);
        PreciseChopOneSkill.preciseChopOneSkill(block);
    }

}
