package io.github.alathra.alathraskills.skills.categories;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.gui.GuiHelper;
import io.github.alathra.alathraskills.skills.SkillCategory;
import net.kyori.adventure.text.format.TextDecoration;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class AlchemySkillCategory extends SkillCategory {

    public AlchemySkillCategory(int id) {
        super(id, "Alchemy", "Witches get stitches!");
        ItemStack icon = new ItemStack(Material.POTION, 1);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of(GuiHelper.COMMON_TITLE + super.getName()).build().decoration(TextDecoration.ITALIC, false).decoration(TextDecoration.ITALIC, false));
        meta.lore(Collections.singletonList(ColorParser.of("<color:#a8a8a8>" + super.getDescription()).build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);
    }
}
