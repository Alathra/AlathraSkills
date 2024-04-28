package io.github.alathra.alathraskills.skills.mining.util;

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
import org.bukkit.event.block.BlockBreakEvent;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

public class VeinBreaker {

    private static HashMap<UUID, Instant> cooldowns = new HashMap<>();

    private static List<UUID> activeVeinBreaker = new ArrayList<>();

    private static List<UUID> runningVeinBreaker = new ArrayList<>();

    private static Plugin instance = AlathraSkills.getInstance();
    private static BukkitTask deactivateSkillTask;
	private static BlockFace[] blockFaces = {BlockFace.DOWN, BlockFace.UP, BlockFace.NORTH, BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH};

	public static final Tag[] oreTags = {Tag.COAL_ORES, Tag.COPPER_ORES, Tag.IRON_ORES, Tag.DIAMOND_ORES, Tag.REDSTONE_ORES, Tag.LAPIS_ORES, Tag.EMERALD_ORES};

    public static HashSet<Block> findVein(Block start, int maxSize) {
        HashSet<Block> blocks = new HashSet<>();
        HashSet<Block> last = new HashSet<>();
        HashSet<Block> toAdd = new HashSet<>();
        last.add(start);
        Tag oreTag = null;
        boolean found = true;
        while (found) {
            found = false;
            toAdd.clear();
            for (Block b : last) {
                for (BlockFace face : blockFaces) {
                    Block block = b.getRelative(face);
                    if (oreTag == null) {
                        for (Tag tag : oreTags) {
                            if (tag.isTagged(block.getType())) {
                                oreTag = tag;
                                toAdd.add(block);
                                found = true;
                            }

                        }
                    } else {
                        if (oreTag.isTagged(block.getType())) {
                            toAdd.add(block);
                            found = true;
                        }
                    }
                    continue;
                }
            }
            blocks.addAll(toAdd);
            last = toAdd;
            if (blocks.size() >= maxSize)
                return blocks;
        }
        return blocks;
    }

    public static void breakVein(Block block, Player player, int level) {
        Material material = block.getType();

        Tag oreTag = null;
        for (Tag tag : oreTags) {
            if (tag.isTagged(block.getType())) {
                oreTag = tag;
                break;
            }
        }
        if (oreTag == null) return;
        if (level <= 0) {
            level = 0;
        }
        long delay = (long)((veinBreakerDuration(7)/2)-(veinBreakerDuration(level)/2))+5;
        HashSet<Block> veins = findVein(block,10*level);
        for (final Block iterBlock : veins) {
            BlockBreakEvent event = new BlockBreakEvent(iterBlock, player);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) return;
            Bukkit.getServer().getScheduler().runTaskLater(instance, () -> {
                iterBlock.breakNaturally();
            }, delay);
        }
    }

    public static void readyVeinBreaker(Player player) {
        if(veinBreakerActive(player)) {
            setveinBreakerActive(player);

            player.sendActionBar(ColorParser.of("<dark_grey>Vein Breaker is <green><bold>ready</bold><dark_grey>.").build());

            deactivateSkillTask = Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(instance, () -> {
                setVeinBreakerNotActive(player);
                player.sendActionBar(ColorParser.of("<dark_grey>Vein Breaker is <dark_red><bold>not ready</bold><dark_grey>.").build());
            }, 100L);
        } else {
            setVeinBreakerNotActive(player);
            Bukkit.getServer().getScheduler().cancelTask(deactivateSkillTask.getTaskId());

            player.sendActionBar(ColorParser.of("<dark_grey>Vein Breaker is <dark_red><bold>not ready</bold><dark_grey>.").build());
        }
    }

    public static void runVeinBreaker(Player player, Block block, int VeinBreakerLevel) {
        VeinBreaker.setVeinBreakerRunning(player);
        VeinBreaker.setVeinBreakerNotActive(player);
        Bukkit.getServer().getScheduler().cancelTask(deactivateSkillTask.getTaskId());
        VeinBreaker.setVeinBreakerCooldown(player, VeinBreakerLevel);

        Bukkit.getServer().getScheduler().runTaskLaterAsynchronously(instance, () -> VeinBreaker.setVeinBreakerNotRunning(player), veinBreakerDuration(VeinBreakerLevel));
        VeinBreaker.breakVein(block, player, VeinBreakerLevel);
    }

    private static long veinBreakerDuration(int VeinBreakerLevel) {
        switch (VeinBreakerLevel) {
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

    public static void setVeinBreakerCooldown(UUID uuid, int VeinBreakerLevel) {
        switch (VeinBreakerLevel) {
            case 1 -> cooldowns.put(uuid, Instant.now().plusSeconds(600));
            case 2 -> cooldowns.put(uuid, Instant.now().plusSeconds(570));
            case 3 -> cooldowns.put(uuid, Instant.now().plusSeconds(540));
            case 4 -> cooldowns.put(uuid, Instant.now().plusSeconds(510));
            case 5 -> cooldowns.put(uuid, Instant.now().plusSeconds(480));
            case 6 -> cooldowns.put(uuid, Instant.now().plusSeconds(420));
            case 7 -> cooldowns.put(uuid, Instant.now().plusSeconds(240));
        }
    }

    public static void setVeinBreakerCooldown(Player player, int VeinBreakerLevel) {
        setVeinBreakerCooldown(player.getUniqueId(), VeinBreakerLevel);
    }

    public static void clearVeinBreakerCooldown(UUID uuid) {
        cooldowns.remove(uuid);
    }

    public static void clearVeinBreakerCooldown(Player player) {
        clearVeinBreakerCooldown(player.getUniqueId());
    }

    public static boolean hasVeinBreakerCooldown(UUID uuid) {
        return cooldowns.get(uuid).isAfter(Instant.now());
    }

    public static boolean hasVeinBreakerCooldown(Player player) {
        return hasVeinBreakerCooldown(player.getUniqueId());
    }

    public static int getRemainingCooldown(UUID uuid) {
        return cooldowns.get(uuid).compareTo(Instant.now());
    }

    public static int getRemainingCooldown(Player player) {
        return getRemainingCooldown(player.getUniqueId());
    }

    public static void setveinBreakerActive(UUID uuid) {
        activeVeinBreaker.add(uuid);
    }

    public static void setveinBreakerActive(Player player) {
        setveinBreakerActive(player.getUniqueId());
    }

    public static boolean veinBreakerActive(UUID uuid) {
        return activeVeinBreaker.contains(uuid);
    }

    public static boolean veinBreakerActive(Player player) {
        return veinBreakerActive(player.getUniqueId());
    }

    public static void setVeinBreakerNotActive(UUID uuid) {
        activeVeinBreaker.remove(uuid);
    }

    public static void setVeinBreakerNotActive(Player player) {
        setVeinBreakerNotActive(player.getUniqueId());
    }

    public static void setVeinBreakerRunning(UUID uuid) {
        runningVeinBreaker.add(uuid);
    }

    public static void setVeinBreakerRunning(Player player) {
        setVeinBreakerRunning(player.getUniqueId());
    }

    public static boolean veinBreakerRunning(UUID uuid) {
        return runningVeinBreaker.contains(uuid);
    }

    public static boolean veinBreakerRunning(Player player) {
        return veinBreakerRunning(player.getUniqueId());
    }

    public static void setVeinBreakerNotRunning(UUID uuid) {
        runningVeinBreaker.remove(uuid);
    }

    public static void setVeinBreakerNotRunning(Player player) {
        setVeinBreakerNotRunning(player.getUniqueId());
    }

}
