package io.github.alathra.alathraskills.listeners.skills;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillDetails;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.skills.woodcutting.util.*;
import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.Bukkit;
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
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashMap;

public class WoodcuttingSkillsListener implements Listener {

    private SkillsPlayerManager skillsPlayerManager = AlathraSkills.getSkillsPlayerManager();

    // TODO: clean up skill check logic
    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {
        Block block = event.getBlock();
        Player player = event.getPlayer();

        if (PDCUtil.isUnnatural(block))
            return;

        if (Tag.LOGS.isTagged(block.getType())) {
            if (Tag.DIRT.isTagged(block.getRelative(BlockFace.DOWN).getType())) {
                SaveTheTrees.run(block, player);
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
        SkillsPlayer skillsPlayer = skillsPlayerManager.getSkillPlayers().get(event.getPlayer().getUniqueId());
        HashMap<Integer, SkillDetails> playerSkills = new HashMap<>(skillsPlayer.getPlayerSkills());

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

            if (playerSkills.get(305).isSelected()) {
                if (OneSwing.hasOneSwingCooldown(player)) {
                    player.sendActionBar(ColorParser.of("<dark_red>One Swing isn't ready yet. Cooldown remaining: " + OneSwing.getRemainingCooldown(player) + " seconds.").build());
                    return;
                }
                OneSwing.readyOneSwing(player);
            }
        }
    }
}
