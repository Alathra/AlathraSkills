package io.github.alathra.alathraskills.skills.mining.proudprospector;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ProudProspectorSix extends Skill {

    private static SkillsManager skillsManager;

    public ProudProspectorSix(int id) {
        super(id, "Proud Prospector 5", "Have a small chance to get double drops from ore.");

        ItemStack icon = new ItemStack(Material.IRON_HELMET);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<cyan><bold>" + super.getName() + "</cyan></bold>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 20</yellow>").build(),
            ColorParser.of("<orange><italics>" + super.getDescription() + "</orange></italics>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(2));
    }
}
