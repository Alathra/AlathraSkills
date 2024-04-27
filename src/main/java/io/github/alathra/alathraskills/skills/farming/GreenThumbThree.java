package io.github.alathra.alathraskills.skills.farming;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import io.github.alathra.alathraskills.skills.farming.util.FarmingData;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GreenThumbThree extends Skill {

    private static double GREEN_THUMB_THREE_CHANCE = 0.20;
    private SkillsManager skillsManager;

    public GreenThumbThree(int id, String name, String description) {
        super(id, "Green Thumb 3", "Get a chance to bone meal crops in a wider area!");

        ItemStack icon = new ItemStack(Material.IRON_HOE);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</green></bold>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 3</yellow>").build(),
            ColorParser.of("<red><italics>" + super.getDescription() + "</gray></italics>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(3));
    }

    // Called on BlockFertilizeEvent
    public static void runFastHarvestOne(Block initialCrop) {
        if (Math.random() >= GREEN_THUMB_THREE_CHANCE)
            return;

        if (FarmingData.getBonemealableCrops().contains(initialCrop.getRelative(BlockFace.NORTH).getType())) {
            initialCrop.applyBoneMeal(BlockFace.NORTH);
        }

        if (FarmingData.getBonemealableCrops().contains(initialCrop.getRelative(BlockFace.SOUTH).getType())) {
            initialCrop.applyBoneMeal(BlockFace.SOUTH);
        }

        if (FarmingData.getBonemealableCrops().contains(initialCrop.getRelative(BlockFace.EAST).getType())) {
            initialCrop.applyBoneMeal(BlockFace.EAST);
        }

        if (FarmingData.getBonemealableCrops().contains(initialCrop.getRelative(BlockFace.WEST).getType())) {
            initialCrop.applyBoneMeal(BlockFace.WEST);
        }

        if (FarmingData.getBonemealableCrops().contains(initialCrop.getRelative(BlockFace.NORTH_EAST).getType())) {
            initialCrop.applyBoneMeal(BlockFace.NORTH_EAST);
        }

        if (FarmingData.getBonemealableCrops().contains(initialCrop.getRelative(BlockFace.NORTH_WEST).getType())) {
            initialCrop.applyBoneMeal(BlockFace.NORTH_WEST);
        }

        if (FarmingData.getBonemealableCrops().contains(initialCrop.getRelative(BlockFace.SOUTH_EAST).getType())) {
            initialCrop.applyBoneMeal(BlockFace.SOUTH_EAST);
        }

        if (FarmingData.getBonemealableCrops().contains(initialCrop.getRelative(BlockFace.SOUTH_WEST).getType())) {
            initialCrop.applyBoneMeal(BlockFace.SOUTH_WEST);
        }


    }
}
