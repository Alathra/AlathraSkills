package io.github.alathra.alathraskills.skills.mining.spelunker;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SpelunkerThree extends Skill {

    private static SkillsManager skillsManager;

    public SpelunkerThree(int id) {
        super(id, "Spelunker 3", "Reduce incoming fall damage.");

        ItemStack icon = new ItemStack(Material.IRON_HELMET);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<cyan><bold>" + super.getName() + "</cyan>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 3</yellow>").build(),
            ColorParser.of("<orange>" + super.getDescription() + "</orange>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(2));
    }
}
