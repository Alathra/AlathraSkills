package io.github.alathra.alathraskills.skills.farming;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ReadyToEatOne extends Skill {

    private static double READY_TO_EAT_ONE_CHANCE = 0.025;
    private SkillsManager skillsManager;

    public ReadyToEatOne(int id) {
        super(id, "Ready to Eat 1", "Get a chance to drop extra food when harvesting!");

        ItemStack icon = new ItemStack(Material.LEAD);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</green></bold>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 1</yellow>").build(),
            ColorParser.of("<red><italics>" + super.getDescription() + "</gray></italics>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(3));
    }

    // Call this on the BlockBreakEvent if wheat, carrots, potatoes or beetroot broken
    // crop is the block being broken
    public static void run(Block crop) {
        if (Math.random() >= READY_TO_EAT_ONE_CHANCE)
            return;

        switch (crop.getType()) {
            case WHEAT, BEETROOTS:
                crop.getWorld().dropItem(crop.getLocation(), new ItemStack(Material.BREAD));
                break;
            case CARROTS:
                crop.getWorld().dropItem(crop.getLocation(), new ItemStack(Material.GOLDEN_CARROT));
                break;
            case POTATOES:
                crop.getWorld().dropItem(crop.getLocation(), new ItemStack(Material.BAKED_POTATO));
                break;
        }

    }

}
