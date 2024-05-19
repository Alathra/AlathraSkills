package io.github.alathra.alathraskills.skills.mining.veinbreaker;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class VeinBreakerTwo extends Skill {

    private static SkillsManager skillsManager;

    public VeinBreakerTwo(int id) {
        super(id, "Vein Breaker 2", "10% chance to break all adjacent ores of the same kind.");

        ItemStack icon = new ItemStack(Material.DIAMOND_PICKAXE);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<cyan><bold>" + super.getName() + "</cyan></bold>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 4</yellow>").build(),
            ColorParser.of("<orange>" + super.getDescription() + "</orange>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(2));
    }
}
