package io.github.alathra.alathraskills.listeners.skills;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayer;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.skills.mining.util.OreInTheRough;
import io.github.alathra.alathraskills.skills.mining.util.ProudProspector;
import io.github.alathra.alathraskills.skills.mining.util.Spelunker;
import io.github.alathra.alathraskills.skills.mining.util.VeinBreaker;
import io.github.alathra.alathraskills.skills.mining.util.helper.MiningData;
import org.bukkit.Bukkit;
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

        // If the damage is not fall damage
        if (event.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }

        Spelunker.run(event, player, 4);

    }
}
