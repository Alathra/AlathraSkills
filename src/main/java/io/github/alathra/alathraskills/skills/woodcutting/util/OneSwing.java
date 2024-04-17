package io.github.alathra.alathraskills.skills.woodcutting.util;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitTask;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class OneSwing {

    private static HashMap<UUID, Instant> cooldowns = new HashMap<>();

    private static List<UUID> activeOneSwing = new ArrayList<>();

    private static List<UUID> runningOneSwing = new ArrayList<>();

    private static Plugin instance = AlathraSkills.getInstance();
    private static BukkitTask deactivateSkillTask;

    public static void fellTree(Block block) {
        Material material = block.getType();

        if (!Tag.LOGS.isTagged(material) && !Tag.LEAVES.isTagged(material))
            return;

        block.breakNaturally();
        for (BlockFace face : BlockFace.values())
            fellTree(block.getRelative(face));
    }

    public static void readyOneSwing(Player player) {
        if(oneSwingActive(player)) {
            setOneSwingActive(player);

            player.sendActionBar(ColorParser.of("<dark_grey>One Swing is <green><bold>ready</bold><dark_grey>.").build());

            deactivateSkillTask = Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(instance, () -> {
                setOneSwingNotActive(player);
                player.sendActionBar(ColorParser.of("<dark_grey>One Swing is <dark_red><bold>not ready</bold><dark_grey>.").build());
            }, 100L);
        } else {
            setOneSwingNotActive(player);
            Bukkit.getServer().getScheduler().cancelTask(deactivateSkillTask.getTaskId());

            player.sendActionBar(ColorParser.of("<dark_grey>One Swing is <dark_red><bold>not ready</bold><dark_grey>.").build());
        }
    }

    public static void runOneSwing(Player player, Block block, int oneSwingLevel) {
        OneSwing.setOneSwingRunning(player);
        OneSwing.setOneSwingNotActive(player);
        Bukkit.getServer().getScheduler().cancelTask(deactivateSkillTask.getTaskId());
        OneSwing.setOneSwingCooldown(player, oneSwingLevel);

        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(instance, () -> OneSwing.setOneSwingNotRunning(player), oneSwingDuration(oneSwingLevel));
        OneSwing.fellTree(block);
    }

    private static long oneSwingDuration(int oneSwingLevel) {
        switch (oneSwingLevel) {
            case 1 -> {
                return 60L;
            }
            case 2 -> {
                return 100L;
            }
            case 3 -> {
                return 200L;
            }
            case 4 -> {
                return 260L;
            }
            case 5 -> {
                return 300L;
            }
            case 6 -> {
                return 360L;
            }
            case 7 -> {
                return 600L;
            }
            default -> {
                return 0;
            }
        }
    }

    public static void setOneSwingCooldown(UUID uuid, int oneSwingLevel) {
        switch (oneSwingLevel) {
            case 1 -> cooldowns.put(uuid, Instant.now().plusSeconds(600));
            case 2 -> cooldowns.put(uuid, Instant.now().plusSeconds(570));
            case 3 -> cooldowns.put(uuid, Instant.now().plusSeconds(540));
            case 4 -> cooldowns.put(uuid, Instant.now().plusSeconds(510));
            case 5 -> cooldowns.put(uuid, Instant.now().plusSeconds(480));
            case 6 -> cooldowns.put(uuid, Instant.now().plusSeconds(420));
            case 7 -> cooldowns.put(uuid, Instant.now().plusSeconds(240));
        }
    }

    public static void setOneSwingCooldown(Player player, int oneSwingLevel) {
        setOneSwingCooldown(player.getUniqueId(), oneSwingLevel);
    }

    public static void clearOneSwingCooldown(UUID uuid) {
        cooldowns.remove(uuid);
    }

    public static void clearOneSwingCooldown(Player player) {
        clearOneSwingCooldown(player.getUniqueId());
    }

    public static boolean hasOneSwingCooldown(UUID uuid) {
        return cooldowns.get(uuid).isAfter(Instant.now());
    }

    public static boolean hasOneSwingCooldown(Player player) {
        return hasOneSwingCooldown(player.getUniqueId());
    }

    public static int getRemainingCooldown(UUID uuid) {
        return cooldowns.get(uuid).compareTo(Instant.now());
    }

    public static int getRemainingCooldown(Player player) {
        return getRemainingCooldown(player.getUniqueId());
    }

    public static void setOneSwingActive(UUID uuid) {
        activeOneSwing.add(uuid);
    }

    public static void setOneSwingActive(Player player) {
        setOneSwingActive(player.getUniqueId());
    }

    public static boolean oneSwingActive(UUID uuid) {
        return activeOneSwing.contains(uuid);
    }

    public static boolean oneSwingActive(Player player) {
        return oneSwingActive(player.getUniqueId());
    }

    public static void setOneSwingNotActive(UUID uuid) {
        activeOneSwing.remove(uuid);
    }

    public static void setOneSwingNotActive(Player player) {
        setOneSwingNotActive(player.getUniqueId());
    }

    public static void setOneSwingRunning(UUID uuid) {
        runningOneSwing.add(uuid);
    }

    public static void setOneSwingRunning(Player player) {
        setOneSwingRunning(player.getUniqueId());
    }

    public static boolean oneSwingRunning(UUID uuid) {
        return runningOneSwing.contains(uuid);
    }

    public static boolean oneSwingRunning(Player player) {
        return oneSwingRunning(player.getUniqueId());
    }

    public static void setOneSwingNotRunning(UUID uuid) {
        runningOneSwing.remove(uuid);
    }

    public static void setOneSwingNotRunning(Player player) {
        setOneSwingNotRunning(player.getUniqueId());
    }

}
