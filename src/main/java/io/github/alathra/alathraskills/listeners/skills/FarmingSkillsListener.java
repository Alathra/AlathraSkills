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
import org.bukkit.event.block.BlockFertilizeEvent;
import org.bukkit.event.entity.EntityBreedEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class FarmingSkillsListener implements Listener {

    private SkillsPlayerManager skillsPlayerManager = AlathraSkills.getSkillsPlayerManager();

    // calls "Ready to Eat" skill
    @EventHandler
    public void BlockBreakListener(BlockBreakEvent event) {

    }

    // calls "Spreading Seed" skill
    @EventHandler
    public void BlockPlaceListener(BlockBreakEvent event) {

    }

    // calls "Green Thumb" skill
    @EventHandler
    public void BlockFertilizeListener(BlockFertilizeEvent event) {

    }

    // calls "Quality Crops" skill
    @EventHandler
    public void AnimalBreedListener(EntityBreedEvent event) {

    }

}
