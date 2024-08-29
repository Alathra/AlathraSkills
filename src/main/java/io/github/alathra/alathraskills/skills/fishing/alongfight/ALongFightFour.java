package io.github.alathra.alathraskills.skills.fishing.alongfight;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.gui.GuiHelper;
import io.github.alathra.alathraskills.skills.Skill;
import io.github.alathra.alathraskills.skills.SkillDescriptionUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ALongFightFour extends Skill {
    public ALongFightFour(int id, int cost) {
        super(id, "A Long Fight 4", "You get extra time in the fishing minigames.", cost);

        ItemStack icon = new ItemStack(Material.CLOCK, 4);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of(GuiHelper.COMMON_TITLE + super.getName()).build().decoration(TextDecoration.ITALIC, false));

        List<Component> loreList = new ArrayList<>();
        loreList.add(ColorParser.of(GuiHelper.LORETEXT + "Level 12").build().decoration(TextDecoration.ITALIC, false));
        loreList.add(ColorParser.of(GuiHelper.LORETEXT + "Cost: " + super.getCost() + " skill points").build().decoration(TextDecoration.ITALIC, false));
        loreList.addAll(SkillDescriptionUtil.descriptionLineBreaker(super.getDescription(), 40, GuiHelper.LORETEXT));
        meta.lore(loreList);

        icon.setItemMeta(meta);
        super.setIcon(icon);

        super.setCategory(AlathraSkills.getSkillsManager().skillCategories.get(4));
    }
}
