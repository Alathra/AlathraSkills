package io.github.alathra.alathraskills.skills.farming;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import io.github.alathra.alathraskills.skills.farming.util.FarmingBlockUtil;
import io.github.alathra.alathraskills.skills.farming.util.FarmingData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GreenThumb extends Skill {

    private static double GREEN_THUMB_ONE_CHANCE = 0.10;
    private SkillsManager skillsManager;

    public GreenThumb(int id, String name, String description) {
        super(id, "Green Thumb 1", "Get a chance to bone meal crops in a wider area!");

        ItemStack icon = new ItemStack(Material.IRON_HOE);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</green></bold>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 1</yellow>").build(),
            ColorParser.of("<red><italics>" + super.getDescription() + "</gray></italics>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(3));
    }

    // Called on BlockFertilizeEvent
    public static void runFastHarvestOne(Block initialCrop) {
        if (Math.random() >= GREEN_THUMB_ONE_CHANCE)
            return;

        for (Block crop : FarmingBlockUtil.getPlusBlocks(initialCrop)) {
            // If adjacent block is a breakable crop
            if (FarmingData.getBonemealableCrops().contains(crop.getType())) {
            }
        }

    }
}
