package io.github.alathra.alathraskills.skills;

import java.util.ArrayList;

import org.bukkit.inventory.ItemStack;

public abstract class Skill {
	
	private int id;
    private int cost;
	private String name;
	private String description;
	
	private ItemStack icon;
	private SkillCategory category;
	
	// Optional 
	private ArrayList<Skill> parents;
	private ArrayList<Skill> children;
	
	public Skill(int id, String name, String description) {
		this.id = id;
		this.name = name;
		this.description = description;
        this.cost = 1;
	}

    public Skill(int id, String name, String description, int cost) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.cost = cost;
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
    public int getCost() { return cost; }
    public void setCost(int cost) { this.cost = cost; }

	public ArrayList<Skill> getParents() {
		return parents;
	}

	public void setParents(ArrayList<Skill> parents) {
		this.parents = parents;
	}

	public ArrayList<Skill> getChildren() {
		return children;
	}

	public void setChildren(ArrayList<Skill> children) {
		this.children = children;
	}
	
}
