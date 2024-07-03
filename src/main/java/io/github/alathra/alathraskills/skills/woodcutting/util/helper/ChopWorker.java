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
        List<Block> searched = new ArrayList<Block>();
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
        Set<Block> logs = new HashSet<Block>();
        Set<Block> logsundone = new HashSet<Block>();
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
                return new ArrayList<Block>();
            }
        }
        return new ArrayList<Block>(logs);
    }

    public static boolean isLog(Block block) {
        switch (block.getType()) {
            case OAK_LOG:
            case SPRUCE_LOG:
            case BIRCH_LOG:
            case ACACIA_LOG:
            case DARK_OAK_LOG:
            case JUNGLE_LOG:
            case CRIMSON_STEM:
            case WARPED_STEM:
            case MANGROVE_LOG:
            case OAK_WOOD:
            case SPRUCE_WOOD:
            case BIRCH_WOOD:
            case ACACIA_WOOD:
            case DARK_OAK_WOOD:
            case JUNGLE_WOOD:
            case CRIMSON_HYPHAE:
            case WARPED_HYPHAE:
            case MANGROVE_WOOD:
            case CHERRY_LOG:
            case CHERRY_WOOD:
                return true;
            default:
                return false;
        }
    }

    public static boolean isLeaves(Block block) {
        switch (block.getType()) {
            case OAK_LEAVES:
            case SPRUCE_LEAVES:
            case BIRCH_LEAVES:
            case ACACIA_LEAVES:
            case DARK_OAK_LEAVES:
            case JUNGLE_LEAVES:
            case AZALEA_LEAVES:
            case FLOWERING_AZALEA_LEAVES:
            case MANGROVE_LEAVES:
            case CHERRY_LEAVES:
                return true;
            default:
                return false;
        }
    }

    public static boolean isWarts(Block block) {
        switch (block.getType()) {
            case NETHER_WART_BLOCK:
            case WARPED_WART_BLOCK:
            case SHROOMLIGHT:
                return true;
            default:
                return false;
        }
    }

    public static boolean isLeavesOrVines(Block block) {
        switch (block.getType()) {
            case OAK_LEAVES:
            case SPRUCE_LEAVES:
            case BIRCH_LEAVES:
            case ACACIA_LEAVES:
            case DARK_OAK_LEAVES:
            case JUNGLE_LEAVES:
            case AZALEA_LEAVES:
            case FLOWERING_AZALEA_LEAVES:
            case MANGROVE_LEAVES:
            case CHERRY_LEAVES:
            case VINE:
                return true;
            default:
                return false;
        }
    }

    public static boolean isWartsOrVines(Block block) {
        switch (block.getType()) {
            case NETHER_WART_BLOCK:
            case WARPED_WART_BLOCK:
            case SHROOMLIGHT:
            case WEEPING_VINES:
            case WEEPING_VINES_PLANT:
                return true;
            default:
                return false;
        }
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
        if (true) {
            if (item.getEnchantments().containsKey(Enchantment.DURABILITY)) {
                float durabilityf = ((float) durability) / (item.getEnchantmentLevel(Enchantment.DURABILITY) + 1);
                float probability = durabilityf - (int) durabilityf;
                if (random.nextFloat() < probability) {
                    durability = (int) durabilityf + 1;
                } else {
                    durability = (int) durabilityf;
                }
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
