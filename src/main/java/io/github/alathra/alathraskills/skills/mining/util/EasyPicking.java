package io.github.alathra.alathraskills.skills.mining.util;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.utility.Cfg;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class EasyPicking {

    private static final HashMap<UUID, Long> cooldowns = new HashMap<>();
    public static int MAX_LEVEL = 7;
    private static Plugin instance = AlathraSkills.getInstance();

    public static void run(Player player, Block block, int skillLevel) {
        if (!isOnCooldown(player, skillLevel)) {
            addCooldown(player);
            // Add haste effect
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, getDuration(skillLevel) * 20, 0));
            player.sendActionBar(ColorParser.of("<dark_grey>Easy Picking <green><bold>activated</bold></green></dark_grey>").build());
            // Notify when cooldown is over
            Bukkit.getScheduler().runTaskLater(instance,
                () -> player.sendActionBar(ColorParser.of("<dark_grey>Easy Picking <green><bold>available</bold></green></dark_grey>").build()), getCooldownTime(skillLevel) * 20
            );
        }
    }

    // Method to check if a player is on cooldown
    public static boolean isOnCooldown(Player player, int skillLevel) {
        return cooldowns.containsKey(player.getUniqueId()) && System.currentTimeMillis() - cooldowns.get(player.getUniqueId()) < getCooldownTime(skillLevel) * 1000;
    }

    // Method to add a player to cooldown
    public static void addCooldown(Player player) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    }

    // In seconds
    private static int getDuration(int skillLevel) {
        switch (skillLevel) {
            case 1 -> {
                return Integer.parseInt(Cfg.getValue("skills.mining.easyPickings.duration.l1").toString());
            }
            case 2 -> {
                return Integer.parseInt(Cfg.getValue("skills.mining.easyPickings.duration.l2").toString());
            }
            case 3 -> {
                return Integer.parseInt(Cfg.getValue("skills.mining.easyPickings.duration.l3").toString());
            }
            case 4 -> {
                return Integer.parseInt(Cfg.getValue("skills.mining.easyPickings.duration.l4").toString());
            }
            case 5 -> {
                return Integer.parseInt(Cfg.getValue("skills.mining.easyPickings.duration.l5").toString());
            }
            case 6 -> {
                return Integer.parseInt(Cfg.getValue("skills.mining.easyPickings.duration.l6").toString());
            }
            case 7 -> {
                return Integer.parseInt(Cfg.getValue("skills.mining.easyPickings.duration.l7").toString());
            }
            default -> {
                return 0;
            }
        }
    }

    // In seconds
    private static long getCooldownTime(int skillLevel) {
        switch (skillLevel) {
            case 1 -> {
                return Integer.parseInt(Cfg.getValue("skills.mining.easyPickings.cooldown.l1").toString());
            }
            case 2 -> {
                return Integer.parseInt(Cfg.getValue("skills.mining.easyPickings.cooldown.l2").toString());
            }
            case 3 -> {
                return Integer.parseInt(Cfg.getValue("skills.mining.easyPickings.cooldown.l3").toString());
            }
            case 4 -> {
                return Integer.parseInt(Cfg.getValue("skills.mining.easyPickings.cooldown.l4").toString());
            }
            case 5 -> {
                return Integer.parseInt(Cfg.getValue("skills.mining.easyPickings.cooldown.l5").toString());
            }
            case 6 -> {
                return Integer.parseInt(Cfg.getValue("skills.mining.easyPickings.cooldown.l6").toString());
            }
            case 7 -> {
                return Integer.parseInt(Cfg.getValue("skills.mining.easyPickings.cooldown.l7").toString());
            }
            default -> {
                return 0;
            }
        }
    }

}
