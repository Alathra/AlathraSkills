package io.github.alathra.alathraskills.skills.categories;

import java.util.Collections;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.milkdrinkers.colorparser.ColorParser;

import io.github.alathra.alathraskills.skills.SkillCategory;

public class WoodcuttingSkillCategory extends SkillCategory {

	public WoodcuttingSkillCategory(int id) {
		super(id, "Woodcutting", "Unleash your inner lumberjack!");
		ItemStack icon = new ItemStack(Material.OAK_LOG, 1);
		ItemMeta meta = icon.getItemMeta();
		meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</bold></green>").build());
		meta.lore(Collections.singletonList(ColorParser.of("<gray><italics>" + super.getDescription() + "</italics></green>").build()));
		icon.setItemMeta(meta);
		super.setIcon(icon);
	}

}
