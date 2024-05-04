package io.github.alathra.alathraskills.listeners.skills;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.skills.mining.util.OreInTheRough;
import io.github.alathra.alathraskills.skills.mining.util.ProudProspector;
import io.github.alathra.alathraskills.skills.mining.util.Spelunker;
import io.github.alathra.alathraskills.skills.mining.util.VeinBreaker;
import io.github.alathra.alathraskills.skills.mining.util.EasyPicking;
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

    private SkillsPlayerManager skillsPlayerManager = AlathraSkills.getSkillsPlayerManager();

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

        SkillsPlayer skillsPlayer = skillsPlayerManager.getSkillPlayers().get(player.getUniqueId());

        // ORE IN THE ROUGH SKILL
        if (MiningData.getNaturalStoneBlocks().contains(material)) {
            OreInTheRough.run(block, 5);
        }

        // PROUD PROSPECTOR & VEIN BREAKER SKILL
        if (MiningData.getOres().contains(material)) {
            ProudProspector.run(event, 6);
            VeinBreaker.run(block, player, 7);
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

        Spelunker.run(event, player, 4);

    }

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

        if (MiningData.getPickaxes().contains(player.getInventory().getItemInMainHand().getType())) {
            EasyPicking.run(player, event.getClickedBlock(), 7);
        }
    }
}
