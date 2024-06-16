package io.github.alathra.alathraskills.skills.categories;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.skills.SkillCategory;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class WoodcuttingSkillCategory extends SkillCategory {

    public WoodcuttingSkillCategory(int id) {
        super(id, "Woodcutting", "Unleash your inner lumberjack!");
        ItemStack icon = new ItemStack(Material.OAK_LOG, 1);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</bold></green>").build());
        meta.lore(Collections.singletonList(ColorParser.of("<yellow>" + super.getDescription() + "</yellow>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);
    }

}
