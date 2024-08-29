package io.github.alathra.alathraskills.skills.fishing.onemanstrash;

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

public class OneMansTrashFour extends Skill {
    public OneMansTrashFour(int id, int cost) {
        super(id, "One Man's Trash 4", "Sometimes you fish up bait with your fish.", cost);

        ItemStack icon = new ItemStack(Material.PAPER, 4);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of(GuiHelper.COMMON_TITLE + super.getName()).build().decoration(TextDecoration.ITALIC, false));

        List<Component> loreList = new ArrayList<>();
        loreList.add(ColorParser.of(GuiHelper.LORETEXT + "Level 16").build().decoration(TextDecoration.ITALIC, false));
        loreList.add(ColorParser.of(GuiHelper.LORETEXT + "Cost: " + super.getCost() + " skill points").build().decoration(TextDecoration.ITALIC, false));
        loreList.addAll(SkillDescriptionUtil.descriptionLineBreaker(super.getDescription(), 40, GuiHelper.LORETEXT));
        meta.lore(loreList);

        meta.setCustomModelData(50001);

        icon.setItemMeta(meta);
        super.setIcon(icon);

        super.setCategory(AlathraSkills.getSkillsManager().skillCategories.get(4));
    }
}
