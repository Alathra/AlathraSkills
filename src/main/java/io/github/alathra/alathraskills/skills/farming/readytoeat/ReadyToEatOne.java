package io.github.alathra.alathraskills.skills.farming.readytoeat;

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

public class ReadyToEatOne extends Skill {

    private SkillsManager skillsManager;

    public ReadyToEatOne(int id, int cost) {
        super(id, "Ready to Eat 1", "Breaking a fully grown crop has a chance to have it also drop its full food counter part.", cost);

        ItemStack icon = new ItemStack(Material.BREAD);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</green>").build());

        List<Component> loreList = new ArrayList<>();
        loreList.add(ColorParser.of("<yellow>Level 13</yellow>").build());
        loreList.addAll(SkillDescriptionUtil.descriptionLineBreaker(super.getDescription(), 40, "<red>"));
        loreList.add(ColorParser.of("<yellow>Cost: " + super.getCost() + " skill points").build());
        meta.lore(loreList);

        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(1));
    }
}
