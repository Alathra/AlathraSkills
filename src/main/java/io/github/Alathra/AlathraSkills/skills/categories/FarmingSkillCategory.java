package io.github.Alathra.AlathraSkills.skills.categories;

import java.util.Collections;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.github.milkdrinkers.colorparser.ColorParser;

import io.github.Alathra.AlathraSkills.skills.SkillCategory;

public class FarmingSkillCategory extends SkillCategory {

	public FarmingSkillCategory(int id) {
		super(id, "Farming", "Out here in the fields!");
		ItemStack icon = new ItemStack(Material.WHEAT, 1);
		ItemMeta meta = icon.getItemMeta();
		meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</bold></green>").build());
		meta.lore(Collections.singletonList(ColorParser.of("<gray><italics>" + super.getDescription() + "</italics></green>").build()));
		icon.setItemMeta(meta);
		super.setIcon(icon);
	}
}
