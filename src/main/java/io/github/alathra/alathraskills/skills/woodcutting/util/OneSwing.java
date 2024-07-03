package io.github.alathra.alathraskills.skills.woodcutting.util;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.skills.woodcutting.util.helper.ChopWorker;
import io.github.alathra.alathraskills.utility.Cfg;
import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.UUID;

public class OneSwing {

    private static final HashSet<UUID> preActives = new HashSet<>();
    private static final HashSet<UUID> actives = new HashSet<>();
    private static final HashMap<UUID, Long> cooldowns = new HashMap<>();
    public static final int MAX_LEVEL = 7;

    public static void run(Player player, Block block, int skillLevel) {
        if (!isActive(player)) {
            return;
        }

        if (ChopWorker.isLog(block) && ChopWorker.isTree(block)) {
            ArrayList<Block> blocks = new ArrayList<>(ChopWorker.getLogsToPop(block));
            BlockBreakEvent event = new BlockBreakEvent(block, player);
            event.setDropItems(false);
            Bukkit.getPluginManager().callEvent(event);
            if (event.isCancelled()) {
                return;
            }
            ItemStack tool = player.getInventory().getItemInMainHand();
            block.breakNaturally();
            for (Block log : blocks) {
                BlockBreakEvent event2 = new BlockBreakEvent(log, player);
                event2.setDropItems(false);
                Bukkit.getPluginManager().callEvent(event2);
                if (event2.isCancelled()) {
                    continue;
                }
                if (PDCUtil.isUnnatural(log)) {
                    continue;
                }
                log.getWorld().playSound(block.getLocation(), Sound.BLOCK_WOOD_BREAK, 0.95F, 0.6F + Math.min(0.015F * (float) (8 - skillLevel), 1.8F));
                log.breakNaturally(tool);
                for (BlockFace face : ChopWorker.leaffaces) {
                    Block neighbor = log.getRelative(face);
                    if (ChopWorker.isLeavesOrVines(neighbor)) {
                        BlockBreakEvent event3 = new BlockBreakEvent(neighbor, player);
                        event3.setDropItems(false);
                        Bukkit.getPluginManager().callEvent(event3);
                        if (event3.isCancelled()) {
                            continue;
                        }
                        if (PDCUtil.isUnnatural(neighbor)) {
                            continue;
                        }
                        neighbor.breakNaturally(tool);

                    }
                }

            }
        }
        actives.remove(player.getUniqueId());
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
        // Notify when cooldown is over
        Bukkit.getScheduler().runTaskLater(instance,
            () -> player.sendActionBar(ColorParser.of("<dark_grey>One Swing <green><bold>available</bold></green></dark_grey>").build()), getCooldownTime(skillLevel) * 20
        );
    }

    // Method to check if a player is on cooldown
    private static boolean isOnCooldown(Player player, int skillLevel) {
        return cooldowns.containsKey(player.getUniqueId()) && System.currentTimeMillis() - cooldowns.get(player.getUniqueId()) < getCooldownTime(skillLevel) * 1000;
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
                return Cfg.get().getInt("skills.woodcutting.oneSwing.duration.l1");
            }
            case 2 -> {
                return Cfg.get().getInt("skills.woodcutting.oneSwing.duration.l2");
            }
            case 3 -> {
                return Cfg.get().getInt("skills.woodcutting.oneSwing.duration.l3");
            }
            case 4 -> {
                return Cfg.get().getInt("skills.woodcutting.oneSwing.duration.l4");
            }
            case 5 -> {
                return Cfg.get().getInt("skills.woodcutting.oneSwing.duration.l5");
            }
            case 6 -> {
                return Cfg.get().getInt("skills.woodcutting.oneSwing.duration.l6");
            }
            case 7 -> {
                return Cfg.get().getInt("skills.woodcutting.oneSwing.duration.l7");
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
                return Cfg.get().getInt("skills.woodcutting.oneSwing.cooldown.l1");
            }
            case 2 -> {
                return Cfg.get().getInt("skills.woodcutting.oneSwing.cooldown.l2");
            }
            case 3 -> {
                return Cfg.get().getInt("skills.woodcutting.oneSwing.cooldown.l3");
            }
            case 4 -> {
                return Cfg.get().getInt("skills.woodcutting.oneSwing.cooldown.l4");
            }
            case 5 -> {
                return Cfg.get().getInt("skills.woodcutting.oneSwing.cooldown.l5");
            }
            case 6 -> {
                return Cfg.get().getInt("skills.woodcutting.oneSwing.cooldown.l6");
            }
            case 7 -> {
                return Cfg.get().getInt("skills.woodcutting.oneSwing.cooldown.l7");
            }
            default -> {
                return 0;
            }
        }
    }

}
