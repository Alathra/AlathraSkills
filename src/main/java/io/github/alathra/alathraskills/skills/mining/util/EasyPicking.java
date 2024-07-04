package io.github.alathra.alathraskills.skills.mining.util;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.utility.Cfg;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.UUID;

public class EasyPicking {

    private static final HashMap<UUID, Long> cooldowns = new HashMap<>();
    public static final int MAX_LEVEL = 7;

    public static void run(Player player, Block block, int skillLevel) {
        if (!isOnCooldown(player, skillLevel)) {
            addCooldown(player);
            // Add haste effect
            player.addPotionEffect(new PotionEffect(PotionEffectType.FAST_DIGGING, getDuration(skillLevel) * 20, 0));
            player.sendActionBar(ColorParser.of("<dark_grey>Easy Picking <green><bold>activated</bold></green></dark_grey>").build());
            // Notify when cooldown is over
            Bukkit.getScheduler().runTaskLater(AlathraSkills.getInstance(),
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
        return switch (skillLevel) {
            case 1 -> Cfg.get().getInt("skills.mining.easyPickings.duration.l1");
            case 2 -> Cfg.get().getInt("skills.mining.easyPickings.duration.l2");
            case 3 -> Cfg.get().getInt("skills.mining.easyPickings.duration.l3");
            case 4 -> Cfg.get().getInt("skills.mining.easyPickings.duration.l4");
            case 5 -> Cfg.get().getInt("skills.mining.easyPickings.duration.l5");
            case 6 -> Cfg.get().getInt("skills.mining.easyPickings.duration.l6");
            case 7 -> Cfg.get().getInt("skills.mining.easyPickings.duration.l7");
            default -> throw new IllegalStateException("Unexpected value: " + skillLevel);
        };
    }

    // In seconds
    private static long getCooldownTime(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> Cfg.get().getInt("skills.mining.easyPickings.cooldown.l1");
            case 2 -> Cfg.get().getInt("skills.mining.easyPickings.cooldown.l2");
            case 3 -> Cfg.get().getInt("skills.mining.easyPickings.cooldown.l3");
            case 4 -> Cfg.get().getInt("skills.mining.easyPickings.cooldown.l4");
            case 5 -> Cfg.get().getInt("skills.mining.easyPickings.cooldown.l5");
            case 6 -> Cfg.get().getInt("skills.mining.easyPickings.cooldown.l6");
            case 7 -> Cfg.get().getInt("skills.mining.easyPickings.cooldown.l7");
            default -> throw new IllegalStateException("Unexpected value: " + skillLevel);
        };
    }

}
