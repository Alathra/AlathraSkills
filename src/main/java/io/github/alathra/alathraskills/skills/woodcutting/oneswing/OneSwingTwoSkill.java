package io.github.alathra.alathraskills.skills.woodcutting.oneswing;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collections;
import java.util.List;

public class OneSwingTwoSkill extends Skill {

    private static SkillsManager skillsManager;
    
    public OneSwingTwoSkill(int id) {
        super(id, "One Swing 2", "Extended time, and a slower cooldown!");

        ItemStack icon = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</green></bold>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 9</yellow>").build(),
            ColorParser.of("<red><italics>" + super.getDescription() + "</gray></italics>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(3));
    }
}
