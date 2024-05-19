package io.github.alathra.alathraskills.skills.mining.oreintherough;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class OreInTheRoughOne extends Skill {

    private static SkillsManager skillsManager;

    public OreInTheRoughOne(int id) {
        super(id, "Ore in the Rough 1", "Have a small chance to find precious metals when mining stone type blocks.");

        ItemStack icon = new ItemStack(Material.RAW_GOLD);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<dark_aqua><bold>" + super.getName() + "</dark_aqua>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 11</yellow>").build(),
            ColorParser.of("<orange>" + super.getDescription() + "</orange>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(2));
    }
}
