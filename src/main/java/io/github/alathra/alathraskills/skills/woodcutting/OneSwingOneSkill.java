package io.github.alathra.alathraskills.skills.woodcutting;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import io.github.alathra.alathraskills.skills.woodcutting.util.OneSwing;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.util.Collections;

public class OneSwingOneSkill extends Skill {

    private static SkillsManager skillsManager;
    private static Plugin instance;
    private static BukkitTask deactivateSkillTask;

    public OneSwingOneSkill(int id) {
        super(id, "One Swing 1", "Chop the whole tree down, in one fell swoop!");

        ItemStack icon = new ItemStack(Material.NETHERITE_AXE);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</green></bold>").build());
        meta.lore(Collections.singletonList(ColorParser.of("<gray><italics>" + super.getDescription() + "</gray></italics>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        instance = AlathraSkills.getInstance();
        super.setCategory(skillsManager.skillCategories.get(3));
    }

    public static void readyOneSwingOneSkill(Player player) {
        if(skillsManager.oneSwingActive(player)) {
            skillsManager.setOneSwingActive(player);

            player.sendActionBar(ColorParser.of("<dark_grey>One Swing is <green><bold>ready</bold><dark_grey>.").build());

            deactivateSkillTask = Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(instance, () -> {
                skillsManager.setOneSwingNotActive(player);
                player.sendActionBar(ColorParser.of("<dark_grey>One Swing is <dark_red><bold>not ready</bold><dark_grey>.").build());
            }, 100L);
        } else {
            skillsManager.setOneSwingNotActive(player);
            Bukkit.getServer().getScheduler().cancelTask(deactivateSkillTask.getTaskId());

            player.sendActionBar(ColorParser.of("<dark_grey>One Swing is <dark_red><bold>not ready</bold><dark_grey>.").build());
        }
    }

    public static void runOneSwingOneSkill(Player player, Block block) {
        skillsManager.setOneSwingRunning(player);
        skillsManager.setOneSwingNotActive(player);
        Bukkit.getServer().getScheduler().cancelTask(deactivateSkillTask.getTaskId());

        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(instance, () -> skillsManager.setOneSwingNotRunning(player), 60L);
        OneSwing.fellTree(block);
    }
}
