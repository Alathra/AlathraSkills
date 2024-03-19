package io.github.alathra.alathraskills.skills.woodcutting;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class SaveTheTreesSkill extends Skill {
    private SkillsManager skillsManager;

    public SaveTheTreesSkill(int id) {
        super(id, "Save the trees", "Save the trees and your time with automatic replanting!");

        ItemStack icon = new ItemStack(Material.OAK_SAPLING);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</green></bold>").build());
        meta.lore(Collections.singletonList(ColorParser.of("<gray><italics>" + super.getDescription() + "</gray></italics>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(3));
    }

    public static void saveTheTreeSkillRun(Block eventBlock) {
        Material blockUnder = eventBlock.getRelative(0, -1, 0).getType();
        if (!Tag.DIRT.isTagged(blockUnder))
            return;

        String blockString = eventBlock.getType().toString();

        // Removes "STRIPPED_".
        if (blockString.contains("STRIPPED"))
            blockString = blockString.substring(9);

        String[] materialArray = blockString.split("_");

        // Handles dark oak.
        if (materialArray.length > 2 && materialArray[2] != null)
            materialArray[0] = materialArray[0].concat("_").concat(materialArray[1]);

        String sapling = materialArray[0].concat("_SAPLING");

        eventBlock.setType(Material.getMaterial(sapling));
    }
}
