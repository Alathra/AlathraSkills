package io.github.alathra.alathraskills.skills.farming.readytoeat;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class ReadyToEatTwo extends Skill {

    private SkillsManager skillsManager;

    public ReadyToEatTwo(int id, int cost) {
        super(id, "Ready to Eat 2", "Breaking a fully grown crop has a chance to have it also drop its full food counter part . Wheat drops bread, potatoes drop cooked potatoes, carrots drop golden carrots, and beetroot drops bread. ", cost);

        ItemStack icon = new ItemStack(Material.BREAD, 2);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</green>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 2</yellow>").build(),
            ColorParser.of("<red>" + super.getDescription() + "</red>").build(),
            ColorParser.of("<yellow>Cost: " + super.getCost() + " skill points").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(1));
    }
}
