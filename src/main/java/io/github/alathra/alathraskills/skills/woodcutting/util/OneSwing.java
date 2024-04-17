package io.github.alathra.alathraskills.skills.woodcutting.util;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class OneSwing {

    private static HashMap<UUID, Instant> cooldowns = new HashMap<>();

    private List<UUID> activeOneSwing = new ArrayList<>();

    private List<UUID> runningOneSwing = new ArrayList<>();

    public static void fellTree(Block block) {
        Material material = block.getType();

        if (!Tag.LOGS.isTagged(material) && !Tag.LEAVES.isTagged(material))
            return;

        block.breakNaturally();
        for (BlockFace face : BlockFace.values())
            fellTree(block.getRelative(face));
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

    public void setOneSwingActive(UUID uuid) {
        activeOneSwing.add(uuid);
    }

    public static void setOneSwingActive(Player player) {
        setOneSwingActive(player.getUniqueId());
    }

    public boolean oneSwingActive(UUID uuid) {
        return activeOneSwing.contains(uuid);
    }

    public static boolean oneSwingActive(Player player) {
        return oneSwingActive(player.getUniqueId());
    }

    public void setOneSwingNotActive(UUID uuid) {
        activeOneSwing.remove(uuid);
    }

    public static void setOneSwingNotActive(Player player) {
        setOneSwingNotActive(player.getUniqueId());
    }

    public void setOneSwingRunning(UUID uuid) {
        runningOneSwing.add(uuid);
    }

    public static void setOneSwingRunning(Player player) {
        setOneSwingRunning(player.getUniqueId());
    }

    public boolean oneSwingRunning(UUID uuid) {
        return runningOneSwing.contains(uuid);
    }

    public static boolean oneSwingRunning(Player player) {
        return oneSwingRunning(player.getUniqueId());
    }

    public void setOneSwingNotRunning(UUID uuid) {
        runningOneSwing.remove(uuid);
    }

    public static void setOneSwingNotRunning(Player player) {
        setOneSwingNotRunning(player.getUniqueId());
    }

}
