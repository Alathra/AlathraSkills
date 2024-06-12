package io.github.alathra.alathraskills.skills.woodcutting.oneswing;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class OneSwingFive extends Skill {

    private static SkillsManager skillsManager;

    public OneSwingFive(int id, int cost) {
        super(id, "One Swing 5", "Longer effect and lower cooldown.", cost);

        ItemStack icon = new ItemStack(Material.DIAMOND_AXE, 5);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</green>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 15</yellow>").build(),
            ColorParser.of("<red>" + super.getDescription() + "</red>").build(),
            ColorParser.of("<yellow>Cost: " + super.getCost() + " skill points").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(3));
    }
}
