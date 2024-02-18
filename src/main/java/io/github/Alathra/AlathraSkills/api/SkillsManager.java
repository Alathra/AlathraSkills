package io.github.Alathra.AlathraSkills.api;

import java.util.HashMap;

import io.github.Alathra.AlathraSkills.AlathraSkills;
import io.github.Alathra.AlathraSkills.Reloadable;
import io.github.Alathra.AlathraSkills.skills.SkillCategory;
import io.github.Alathra.AlathraSkills.skills.categories.FarmingSkillCategory;
import io.github.Alathra.AlathraSkills.skills.categories.MiningSkillCategory;
import io.github.Alathra.AlathraSkills.skills.categories.WoodcuttingSkillCategory;

public class SkillsManager implements Reloadable {
	
	private final AlathraSkills plugin;
	
	// Id, SkillCategory
	public HashMap<Integer, SkillCategory> skillCategories = new HashMap<>();
	
	public SkillsManager(AlathraSkills plugin) {
		this.plugin = plugin;
	}

	@Override
	public void onLoad() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onEnable() {
		loadSkillCategories();
	}

	@Override
	public void onDisable() {
		// TODO Auto-generated method stub
		
	}
	
	public void loadSkillCategories() {
		skillCategories.put(1, new FarmingSkillCategory(1));
		skillCategories.put(2, new MiningSkillCategory(2));
		skillCategories.put(3, new WoodcuttingSkillCategory(3));
	}
	
}
