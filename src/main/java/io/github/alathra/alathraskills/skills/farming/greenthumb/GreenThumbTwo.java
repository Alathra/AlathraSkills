package io.github.alathra.alathraskills.skills.farming.greenthumb;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class GreenThumbTwo extends Skill {

    private SkillsManager skillsManager;

    public GreenThumbTwo(int id) {
        super(id, "Green Thumb 2", "Get a chance to bone meal crops in a wider area!");

        ItemStack icon = new ItemStack(Material.IRON_HOE, 2);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</green>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 2</yellow>").build(),
            ColorParser.of("<red>" + super.getDescription() + "</red>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(3));
    }
}
