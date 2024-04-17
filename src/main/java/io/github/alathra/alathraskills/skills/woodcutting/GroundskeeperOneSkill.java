package io.github.alathra.alathraskills.skills.woodcutting;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class GroundskeeperOneSkill extends Skill {

    private static SkillsManager skillsManager;


    public GroundskeeperOneSkill(int id) {
        super(id, "Groundskeeper 1", "Quickly shear your leaves, with an extra chance at leaves!");

        ItemStack icon = new ItemStack(Material.SHEARS);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</green></bold>").build());
        meta.lore(Collections.singletonList(ColorParser.of("<gray><italics>" + super.getDescription() + "</gray></italics>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(3));
    }




    public static void runGroundskeeperOneSkill(BlockBreakEvent event) {
        Block block = event.getBlock();
        Material material = block.getType();
        Location location = block.getLocation();

        List<Block> blockList = new ArrayList<>();
        blockList.add(block.getRelative(1, 0, 0));
        blockList.add(block.getRelative(0, 1, 0));
        blockList.add(block.getRelative(0, 0, 1));
        blockList.add(block.getRelative(1, 0, 1));
        blockList.add(block.getRelative(1, 1, 0));
        blockList.add(block.getRelative(0, 1, 1));
        blockList.add(block.getRelative(1, 1, 1));

        blockList.forEach(Block::breakNaturally);

        int random = new Random().nextInt(20);
        if (random < 3)
            location.getWorld().dropItemNaturally(location, new ItemStack(material, 2));
    }
}
