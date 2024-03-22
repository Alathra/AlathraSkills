package io.github.alathra.alathraskills.skills.woodcutting;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;
import java.util.Random;

public class TrimmerOneSkill extends Skill {

    private SkillsManager skillsManager;

    public TrimmerOneSkill(int id) {
        super(id, "Trimmer 1", "Clear those leaves, now with an extra chance at apples!");

        ItemStack icon = new ItemStack(Material.OAK_LEAVES);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</green></bold>").build());
        meta.lore(Collections.singletonList(ColorParser.of("<gray><italics>" + super.getDescription() + "</gray></italics>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(3));
    }

    public static void runTrimmerOneSkill(Block eventBlock) {
        int random = new Random().nextInt(50);
        if (random != 0)
            return;

        Location location = eventBlock.getLocation();
        World world = eventBlock.getWorld();

        world.dropItemNaturally(location, new ItemStack(Material.APPLE));
    }
}
