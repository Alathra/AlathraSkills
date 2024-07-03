package io.github.alathra.alathraskills.skills.woodcutting.util.helper;

import io.github.alathra.alathraskills.utility.PDCUtil;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

public class ChopWorker {

    public static final BlockFace[][] logfaces = {{BlockFace.UP},
        {BlockFace.UP, BlockFace.NORTH}, {BlockFace.UP, BlockFace.EAST}, {BlockFace.UP, BlockFace.SOUTH}, {BlockFace.UP, BlockFace.WEST},
        {BlockFace.UP, BlockFace.NORTH_EAST}, {BlockFace.UP, BlockFace.SOUTH_EAST}, {BlockFace.UP, BlockFace.NORTH_WEST}, {BlockFace.UP, BlockFace.SOUTH_WEST},
        {BlockFace.NORTH}, {BlockFace.EAST}, {BlockFace.WEST}, {BlockFace.SOUTH},
        {BlockFace.NORTH_EAST}, {BlockFace.SOUTH_EAST}, {BlockFace.NORTH_WEST}, {BlockFace.SOUTH_WEST}};
    public static final BlockFace[] leaffaces = {BlockFace.UP, BlockFace.NORTH, BlockFace.EAST, BlockFace.WEST, BlockFace.SOUTH};
    static final Random random = new Random();

    public static boolean isTree(Block block) {
        if (!isLog(block)) return false;
        Block nextblock = block.getRelative(BlockFace.SELF);
        int searchlimit = 200;
        List<Block> searched = new ArrayList<>();
        while (searchlimit > 0) {
            // Decrement limit first
            searchlimit--;
            // Prevent loop back to log
            if (!searched.contains(nextblock)) {
                searched.add(nextblock);
                // Next log
                boolean found = false;
                for (BlockFace[] blockfacess : logfaces) {
                    Block newblock = nextblock;
                    for (BlockFace blockfacesss : blockfacess) {
                        newblock = newblock.getRelative(blockfacesss);
                    }
                    if (isLog(newblock)) {
                        nextblock = newblock;
                        found = true;
                        break;
                    }
                }
                if (found) continue;
            }

            // Fall to here means not more upper block
            // nextblock is the current highest block
            int leavescount = 0;
            for (BlockFace leafface : leaffaces) {
                if (isLeaves(nextblock.getRelative(leafface)) || isWarts(nextblock.getRelative(leafface))) {
                    leavescount++;
                }
            }
            if (leavescount >= 2) {
                return true;
            } else {
                // Pine fix
                for (BlockFace leafface : leaffaces) {
                    if (isLeaves(nextblock.getRelative(BlockFace.UP).getRelative(leafface)) || isWarts(nextblock.getRelative(BlockFace.UP).getRelative(leafface))) {
                        leavescount++;
                    }
                }
                if (leavescount >= 4) {
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }

    public static List<Block> getLogsToPop(Block block) {
        Set<Block> logs = new HashSet<>();
        Set<Block> logsundone = new HashSet<>();
        logsundone.add(block);
        while (logsundone.size() > 0) {
            Block log = logsundone.iterator().next();
            logsundone.remove(log);
            if (PDCUtil.isUnnatural(log)) {
                continue;
            }
            logs.add(log);

            for (BlockFace[] blockfacess : logfaces) {
                Block newblock = log;
                for (BlockFace blockfacesss : blockfacess) {
                    newblock = newblock.getRelative(blockfacesss);
                    if (PDCUtil.isUnnatural(newblock)) {
                        continue;
                    }
                }
                if (isLog(newblock)) {
                    if (!logs.contains(newblock) && !logsundone.contains(newblock)) {
                        if (PDCUtil.isUnnatural(newblock)) {
                            continue;
                        }
                        logsundone.add(newblock);
                    }
                }
            }
            if (logsundone.size() + logs.size() > 100) {
                return new ArrayList<>();
            }
        }
        return new ArrayList<>(logs);
    }

    public static boolean isLog(Block block) {
        return switch (block.getType()) {
            case OAK_LOG, SPRUCE_LOG, BIRCH_LOG, ACACIA_LOG, DARK_OAK_LOG, JUNGLE_LOG, CRIMSON_STEM, WARPED_STEM, MANGROVE_LOG, OAK_WOOD, SPRUCE_WOOD, BIRCH_WOOD, ACACIA_WOOD, DARK_OAK_WOOD, JUNGLE_WOOD, CRIMSON_HYPHAE, WARPED_HYPHAE, MANGROVE_WOOD, CHERRY_LOG, CHERRY_WOOD ->
                    true;
            default -> false;
        };
    }

    public static boolean isLeaves(Block block) {
        return switch (block.getType()) {
            case OAK_LEAVES, SPRUCE_LEAVES, BIRCH_LEAVES, ACACIA_LEAVES, DARK_OAK_LEAVES, JUNGLE_LEAVES, AZALEA_LEAVES, FLOWERING_AZALEA_LEAVES, MANGROVE_LEAVES, CHERRY_LEAVES ->
                    true;
            default -> false;
        };
    }

    public static boolean isWarts(Block block) {
        return switch (block.getType()) {
            case NETHER_WART_BLOCK, WARPED_WART_BLOCK, SHROOMLIGHT -> true;
            default -> false;
        };
    }

    public static boolean isLeavesOrVines(Block block) {
        return switch (block.getType()) {
            case OAK_LEAVES, SPRUCE_LEAVES, BIRCH_LEAVES, ACACIA_LEAVES, DARK_OAK_LEAVES, JUNGLE_LEAVES, AZALEA_LEAVES, FLOWERING_AZALEA_LEAVES, MANGROVE_LEAVES, CHERRY_LEAVES, VINE ->
                    true;
            default -> false;
        };
    }

    public static boolean isWartsOrVines(Block block) {
        return switch (block.getType()) {
            case NETHER_WART_BLOCK, WARPED_WART_BLOCK, SHROOMLIGHT, WEEPING_VINES, WEEPING_VINES_PLANT -> true;
            default -> false;
        };
    }

    public static boolean checkPermission(Player p) {
        if (p.hasPermission("choptree.chop")) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isTool(ItemStack item) {
        if (item == null) return false;
        return false;
    }

    public static boolean checkDurability(ItemStack item, int logs) {
        if (item.getType().getMaxDurability() == 0) return true;
        short extradurability = extraDurability(item, logs);
        short itemcanhandle = itemCanHandle(item);
        if (isDurabilityOk(itemcanhandle, extradurability)) {
            applyDurability(item, extradurability);
            return true;
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    public static short itemCanHandle(ItemStack item) {
        return (short) (item.getType().getMaxDurability() - item.getDurability());
    }

    public static short extraDurability(ItemStack item, int logs) {
        if (1 <= 0.001D) return 1;
        int durability = logs;
        if (item.getEnchantments().containsKey(Enchantment.DURABILITY)) {
            float durabilityf = ((float) durability) / (item.getEnchantmentLevel(Enchantment.DURABILITY) + 1);
            float probability = durabilityf - (int) durabilityf;
            if (random.nextFloat() < probability) {
                durability = (int) durabilityf + 1;
            } else {
                durability = (int) durabilityf;
            }
        }
        return (short) durability;
    }

    public static boolean isDurabilityOk(short item, short amount) {
        return item > amount;
    }

    @SuppressWarnings("deprecation")
    public static void applyDurability(ItemStack item, short amount) {
        item.setDurability((short) (item.getDurability() + amount));
    }


}
