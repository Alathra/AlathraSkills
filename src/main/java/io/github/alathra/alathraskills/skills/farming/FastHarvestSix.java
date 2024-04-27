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

public class FastHarvestSix extends Skill {

    private static double FAST_HARVEST_SIX_CHANCE = 0.60;
    private SkillsManager skillsManager;

    public FastHarvestSix(int id, String name, String description) {
        super(id, "Fast Harvest 6", "Get a chance to harvest crops in a wider area!");

        ItemStack icon = new ItemStack(Material.IRON_HOE);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</green></bold>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 6</yellow>").build(),
            ColorParser.of("<red><italics>" + super.getDescription() + "</gray></italics>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(3));
    }

    // Call this on the BlockBreakEvent if instance of breakable crop
    // crop is event.getBlock()
    public static void run(Block initialCrop, Player player) {
        if (Math.random() >= FAST_HARVEST_SIX_CHANCE)
            return;

        ItemStack tool = null;
        if (player.getInventory().getItemInMainHand() != null) {
            tool = player.getInventory().getItemInMainHand();
        }

        for (Block crop : FarmingBlockUtil.get3by3Blocks(initialCrop)) {
            // If adjacent block is a breakable crop
            if (FarmingData.getBreakableCrops().contains(crop.getType())) {
                // If crop has growth cycles
                if (crop.getBlockData() instanceof Ageable ageableCrop) {
                    // If crop is fully grown
                    if (ageableCrop.getAge() == ageableCrop.getMaximumAge()) {
                        crop.breakNaturally();
                        if (tool != null) {
                            crop.breakNaturally(tool);
                        } else {
                            crop.breakNaturally();
                        }
                    }
                    // Is a crop, but not fully grown so ignore
                    continue;
                } else {
                    // Is a crop that does not have growth cycles
                    if (tool != null) {
                        crop.breakNaturally(tool);
                    } else {
                        crop.breakNaturally();
                    }
                }
            }
        }

    }
}
