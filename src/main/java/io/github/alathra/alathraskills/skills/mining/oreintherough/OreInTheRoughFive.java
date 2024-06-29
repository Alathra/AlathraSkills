package io.github.alathra.alathraskills.skills.mining.oreintherough;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import io.github.alathra.alathraskills.skills.SkillDescriptionUtil;
import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class OreInTheRoughFive extends Skill {

    private static SkillsManager skillsManager;

    public OreInTheRoughFive(int id, int cost) {
        super(id, "Ore in the Rough 5", "Sometimes you find raw ores when breaking stone or deepslate.", cost);

        ItemStack icon = new ItemStack(Material.RAW_GOLD, 5);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<dark_aqua><bold>" + super.getName() + "</dark_aqua>").build());

        List<Component> loreList = new ArrayList<>();
        loreList.add(ColorParser.of("<yellow>Level 19</yellow>").build());
        loreList.addAll(SkillDescriptionUtil.descriptionLineBreaker(super.getDescription(), 40, "<red>"));
        loreList.add(ColorParser.of("<yellow>Cost: " + super.getCost() + " skill points").build());
        meta.lore(loreList);

        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(2));
    }
}
