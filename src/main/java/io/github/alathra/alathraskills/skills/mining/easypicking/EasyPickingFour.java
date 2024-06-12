package io.github.alathra.alathraskills.skills.mining.easypicking;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class EasyPickingFour extends Skill {

    private static SkillsManager skillsManager;

    public EasyPickingFour(int id, int cost) {
        super(id, "Easy Picking 4", "Right click to get haste.", cost);

        ItemStack icon = new ItemStack(Material.GOLDEN_PICKAXE, 4);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<dark_aqua><bold>" + super.getName() + "</dark_aqua>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 15</yellow>").build(),
            ColorParser.of("<red>" + super.getDescription() + "</red>").build(),
            ColorParser.of("<yellow>Cost: " + super.getCost() + " skill points").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(2));
    }
}
