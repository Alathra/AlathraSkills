package io.github.alathra.alathraskills.skills.woodcutting.trimmer;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class TrimmerFive extends Skill {

    private SkillsManager skillsManager;

    public TrimmerFive(int id) {
        super(id, "Trimmer 5", "Get an even better chance at apples.");

        ItemStack icon = new ItemStack(Material.OAK_LEAVES, 5);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</green>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 16</yellow>").build(),
            ColorParser.of("<red>" + super.getDescription() + "</red>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(3));
    }
}
