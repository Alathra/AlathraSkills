package io.github.alathra.alathraskills.skills.mining;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import io.github.alathra.alathraskills.skills.mining.util.VeinBreaker;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collections;
import java.util.List;

public class OneSpelunkerOneSkill extends Skill {

    private static SkillsManager skillsManager;

    public OneSpelunkerOneSkill(int id) {
        super(id, "Spelunker 1", "Reduce fall damage by 1 heart!");

        ItemStack icon = new ItemStack(Material.IRON_HELMET);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<cyan><bold>" + super.getName() + "</cyan></bold>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 1</yellow>").build(),
            ColorParser.of("<orange><italics>" + super.getDescription() + "</orange></italics>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(2));
    }
}
