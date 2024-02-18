package io.github.Alathra.AlathraSkills.skills;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public abstract class Skill {
	
	private int id;
	private String name;
	private String description;
	
	private ItemStack icon;
	private SkillCategory category;
	
	// Optional 
	private ArrayList<Skill> parents;
	private ArrayList<Skill> childrens;
	
	public Skill(int id, String name, String description, ItemStack icon, SkillCategory category) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.icon = icon;
		this.category = category;
	}
	
	public SkillCategory getCategory() {
		return category;
	}
	public void setCategory(SkillCategory category) {
		this.category = category;
	}
	public ItemStack getIcon() {
		return icon;
	}
	public void setIcon(ItemStack icon) {
		this.icon = icon;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}

	public ArrayList<Skill> getParents() {
		return parents;
	}

	public void setParents(ArrayList<Skill> parents) {
		this.parents = parents;
	}

	public ArrayList<Skill> getChildrens() {
		return childrens;
	}

	public void setChildrens(ArrayList<Skill> childrens) {
		this.childrens = childrens;
	}
	
}
