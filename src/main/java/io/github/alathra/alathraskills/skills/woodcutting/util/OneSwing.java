package io.github.alathra.alathraskills.skills.woodcutting.util;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class OneSwing {

    private static Plugin instance = AlathraSkills.getInstance();

    private static final HashSet<UUID> preActives = new HashSet<>();

    private static final HashSet<UUID> actives = new HashSet<>();

    private static final HashMap<UUID, Long> cooldowns = new HashMap<>();

    public static void run(Player player, Block block, int skillLevel) {
        if (!isActive(player)) {
            return;
        }
        // TODO: Choppity chop!
    }

    // Either pre-activate, activate or do nothing depending on current state
    public static void activate(Player player, int skillLevel) {
        // Do nothing
        if (isActive(player) || isOnCooldown(player, skillLevel)) {
            return;
        }
        // Pre-activate
        if (!isPreactive(player)) {
            setPreactive(player);
            return;
        }
        // Activate
        addCooldown(player);
        player.sendActionBar(ColorParser.of("<dark_grey>One Swing <green><bold>activated</bold></green></dark_grey>").build());
        setActive(player, skillLevel);
    }

    // Method to check if a player is on cooldown
    private static boolean isOnCooldown(Player player, int skillLevel) {
        return cooldowns.containsKey(player.getUniqueId()) && System.currentTimeMillis() - cooldowns.get(player.getUniqueId()) < getCooldownTime(skillLevel)*1000;
    }

    private static void setPreactive(Player player) {
        // If player found in pre-actives, reset
        preActives.remove(player.getUniqueId());
        // Add the player to pre-actives set
        preActives.add(player.getUniqueId());

        // After duration is over, remove the player from actives set
        // After 5 seconds
        new BukkitRunnable() {
            public void run() {
                actives.remove(player.getUniqueId());
            }
        }.runTaskLaterAsynchronously(AlathraSkills.getInstance(), 100);
    }

    private static void setActive(Player player, int skillLevel) {
        if (!preActives.contains(player.getUniqueId())) {
            return;
        }

        // If player found in actives, reset
        actives.remove(player.getUniqueId());
        // Add the player to actives set
        actives.add(player.getUniqueId());
        // After duration is over, remove the player from actives set
        new BukkitRunnable() {
            public void run() {
                actives.remove(player.getUniqueId());
            }
        }.runTaskLaterAsynchronously(AlathraSkills.getInstance(), (long) getDuration(skillLevel) * 20);
    }

    private static boolean isActive(Player player) {
        return actives.contains(player.getUniqueId());
    }

    private static boolean isPreactive(Player player) {
        return preActives.contains(player.getUniqueId());
    }

    // Method to add a player to cooldown
    private static void addCooldown(Player player) {
        cooldowns.put(player.getUniqueId(), System.currentTimeMillis());
    }

    // In seconds
    private static int getDuration(int skillLevel) {
        switch (skillLevel) {
            case 1 -> {
                return 3;
            }
            case 2 -> {
                return 5;
            }
            case 3 -> {
                return 10;
            }
            case 4 -> {
                return 13;
            }
            case 5 -> {
                return 17;
            }
            case 6 -> {
                return 21;
            }
            case 7 -> {
                return 30;
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
                return 600L;
            }
            case 2 -> {
                return 570L;
            }
            case 3 -> {
                return 540L;
            }
            case 4 -> {
                return 510L;
            }
            case 5 -> {
                return 480L;
            }
            case 6 -> {
                return 420L;
            }
            case 7 -> {
                return 300L;
            }
            default -> {
                return 0;
            }
        }
    }

}
