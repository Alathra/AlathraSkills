package io.github.alathra.alathraskills.skills.mining.easypicking;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class EasyPickingOne extends Skill {

    private static SkillsManager skillsManager;

    public EasyPickingOne(int id) {
        super(id, "Easy Picking 1", "Right click to get haste.");

        ItemStack icon = new ItemStack(Material.GOLDEN_PICKAXE);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<dark_aqua><bold>" + super.getName() + "</dark_aqua>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 5</yellow>").build(),
            ColorParser.of("<orange><italics>" + super.getDescription() + "</orange></italics>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(2));
    }
}
