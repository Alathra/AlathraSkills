package io.github.alathra.alathraskills.skills.woodcutting.onewiththeforest;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class OneWithTheForestSeven extends Skill {

    private static SkillsManager skillsManager;

    public OneWithTheForestSeven(int id, int cost) {
        super(id, "One With the Forest 7", "Your bone meal affects multiple saplings, you find the saplings of other trees in leaves, and bees are more likely to appear on the trees you grow.", cost);

        ItemStack icon = new ItemStack(Material.POPPY, 7);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</green>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 20</yellow>").build(),
            ColorParser.of("<red>" + super.getDescription() + "</red>").build(),
            ColorParser.of("<yellow>Cost: " + super.getCost() + " skill points").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(3));
    }
}
