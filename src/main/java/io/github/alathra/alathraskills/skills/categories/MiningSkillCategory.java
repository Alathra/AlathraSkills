package io.github.alathra.alathraskills.skills.categories;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.gui.GuiHelper;
import io.github.alathra.alathraskills.skills.SkillCategory;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class MiningSkillCategory extends SkillCategory {

    public MiningSkillCategory(int id) {
        super(id, "Mining", "I prefer the darkness!");
        ItemStack icon = new ItemStack(Material.DIAMOND_PICKAXE, 1);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of(GuiHelper.COMMON_TITLE + super.getName()).build());
        meta.lore(Collections.singletonList(ColorParser.of("<yellow>" + super.getDescription() + "</yellow>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);
    }

}
