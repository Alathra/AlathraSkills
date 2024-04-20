package io.github.alathra.alathraskills.skills.woodcutting;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class OneSwingThreeSkill extends Skill {

    private static SkillsManager skillsManager;

    public OneSwingThreeSkill(int id) {
        super(id, "One Swing 3", "Even more time, and even lower cooldown.");

        ItemStack icon = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</green></bold>").build());
        meta.lore(Collections.singletonList(ColorParser.of("<gray><italics>" + super.getDescription() + "</gray></italics>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        instance = AlathraSkills.getInstance();
        super.setCategory(skillsManager.skillCategories.get(3));
    }
}
