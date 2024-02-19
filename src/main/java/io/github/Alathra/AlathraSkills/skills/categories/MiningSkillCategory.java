package io.github.alathra.alathraskills.skills.categories;

import java.util.Collections;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.milkdrinkers.colorparser.ColorParser;

import io.github.alathra.alathraskills.skills.SkillCategory;

public class MiningSkillCategory extends SkillCategory {

	public MiningSkillCategory(int id) {
		super(id, "Mining", "You know we had this one!");
		ItemStack icon = new ItemStack(Material.DIAMOND_ORE, 1);
		ItemMeta meta = icon.getItemMeta();
		meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</bold></green>").build());
		meta.lore(Collections.singletonList(ColorParser.of("<gray><italics>" + super.getDescription() + "</italics></green>").build()));
		icon.setItemMeta(meta);
		super.setIcon(icon);
	}

}
