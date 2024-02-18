package io.github.Alathra.AlathraSkills.skills;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public abstract class SkillCategory {
	private int id;
	private String name;
	private String description;
	
	private ItemStack icon;
	private ArrayList<Skill> skills;
	
	public SkillCategory(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
	}
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public ItemStack getIcon() {
		return icon;
	}
	public void setIcon(ItemStack icon) {
		this.icon = icon;
	}
	public ArrayList<Skill> getSkills() {
		return skills;
	}
	public void setSkills(ArrayList<Skill> skills) {
		this.skills = skills;
	}

	public ArrayList<Skill> getUnlockedSkills() {
		return unlockedSkills;
	}

	public void setUnlockedSkills(ArrayList<Skill> unlockedSkills) {
		this.unlockedSkills = unlockedSkills;
	}
}
