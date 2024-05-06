package io.github.alathra.alathraskills.skills.woodcutting.util;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class SaveTheTrees {
    private SkillsManager skillsManager;

    public static void run(Block block, Player player) {

        String blockString = block.getType().toString();

        // Removes "STRIPPED_".
        if (blockString.contains("STRIPPED"))
            blockString = blockString.substring(9);

        String[] materialArray = blockString.split("_");

        // Handles dark oak.
        if (materialArray.length > 2 && materialArray[2] != null)
            materialArray[0] = materialArray[0].concat("_").concat(materialArray[1]);

        Material sapling = Material.getMaterial(materialArray[0].concat("_SAPLING"));

        if (sapling == null) {
            return;
        }

        if (!player.getInventory().contains(sapling)) {
            return;
        }

        processInventoryAndPlaceSapling(player, sapling, block);

    }

    public static void processInventoryAndPlaceSapling(Player player, Material sapling, Block block) {

        // Find sapling in inventory
        for (int i = 0; i < player.getInventory().getContents().length; i++) {
            // If nothing in this inventory slot, continue looping
            if (player.getInventory().getItem(i) == null) {
                continue;
            }
            if (player.getInventory().getItem(i).getType() == sapling) {
                ItemStack itemStack = player.getInventory().getItem(i);
                if (itemStack.getAmount() == 1) {
                    player.getInventory().setItem(i, null);
                } else {
                    itemStack.setAmount(itemStack.getAmount() - 1);
                    player.getInventory().setItem(i, itemStack);
                }
                break;
            }
        }

        // Place sapling
        new BukkitRunnable() {
            public void run() {
                block.setType(sapling);
                block.getWorld().playSound(block.getLocation(), block.getBlockSoundGroup().getPlaceSound(), 1.0f, 1.0f);
            }
        }.runTaskLater(AlathraSkills.getInstance(), 2);
    }

}
