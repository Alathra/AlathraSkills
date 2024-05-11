package io.github.alathra.alathraskills.listeners.skills;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.skills.woodcutting.util.*;
import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.Material;
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

public class WoodcuttingSkillsListener implements Listener {

    private SkillsPlayerManager skillsPlayerManager = AlathraSkills.getSkillsPlayerManager();

    // calls "Precise Chop", "Save the Trees" and "Groundskeeper"
    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (PDCUtil.isUnnatural(block))
            return;

        if (Tag.LOGS.isTagged(block.getType())) {
            PreciseChop.run(block, 7);
            if (Tag.DIRT.isTagged(block.getRelative(BlockFace.DOWN).getType())) {
                SaveTheTrees.run(block, player);
            }
        }

        if (event.getPlayer().getInventory().getItemInMainHand().getType() == Material.SHEARS) {
            if (Tag.LEAVES.isTagged(block.getType())) {
                Groundskeeper.run(block, 7);
            }
        }

    }

    // calls "Trimmer"
    @EventHandler
    public void BlockDamageListener(BlockDamageEvent event) {

        if (!Tag.LEAVES.isTagged(event.getBlock().getType())) {
            return;
        }

        if (!Tag.ITEMS_AXES.isTagged(event.getItemInHand().getType())) {
            return;
        }

        if (PDCUtil.isUnnatural(event.getBlock())) {
            return;
        }

        // run Trimmer
        Trimmer.run(event, 7);
    }

    @EventHandler
    public void RightClickListener(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }
        Block block = event.getClickedBlock();
        if (Tag.LOGS.isTagged(block.getType())) {
        }
    }
}
