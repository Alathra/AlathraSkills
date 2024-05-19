package io.github.alathra.alathraskills.skills.mining.spelunker;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SpelunkerFour extends Skill {

    private static SkillsManager skillsManager;

    public SpelunkerFour(int id) {
        super(id, "Spelunker 4", "Reduce incoming fall damage.");

        ItemStack icon = new ItemStack(Material.IRON_HELMET, 4);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<dark_aqua><bold>" + super.getName() + "</dark_aqua>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 13</yellow>").build(),
            ColorParser.of("<orange>" + super.getDescription() + "</orange>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(2));
    }
}
