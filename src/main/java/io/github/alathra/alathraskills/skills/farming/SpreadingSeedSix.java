package io.github.alathra.alathraskills.skills.farming;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import io.github.alathra.alathraskills.skills.farming.util.FarmingBlockUtil;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.data.Ageable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SpreadingSeedSix extends Skill {

    private static double SPREADING_SEED_SIX_CHANCE = 0.60;
    private SkillsManager skillsManager;

    public SpreadingSeedSix(int id, String name, String description) {
        super(id, "Spreading Seed 6", "Get a chance to sow seeds in a wider area!");

        ItemStack icon = new ItemStack(Material.IRON_HOE);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</green></bold>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 6</yellow>").build(),
            ColorParser.of("<red><italics>" + super.getDescription() + "</gray></italics>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(3));
    }

    // Called on BlockPlaceEvent on an instance of farmland when the player places a seed
    public static void run (Block initialCrop, Player player) {
        if (Math.random() >= SPREADING_SEED_SIX_CHANCE)
            return;

        for (Block block : FarmingBlockUtil.get3by3Blocks(initialCrop)) {
            if (block.getRelative(BlockFace.DOWN).getType() == Material.FARMLAND) {
                // If there is not space to plant the seed, continue looping
                if (block.getType() != Material.AIR) {
                    continue;
                }
                // Loop through player's inventory for seed type and remove item
                switch (initialCrop.getType()) {
                    case WHEAT:
                        processInventoryForSeedsAndPlaceCrop(Material.WHEAT_SEEDS, Material.WHEAT, player, block);
                        break;
                    case BEETROOT_SEEDS:
                        processInventoryForSeedsAndPlaceCrop(Material.BEETROOT_SEEDS, Material.BEETROOTS, player, block);
                        break;
                    case CARROTS:
                        processInventoryForSeedsAndPlaceCrop(Material.CARROT, Material.CARROTS, player, block);
                        break;
                    case POTATOES:
                        processInventoryForSeedsAndPlaceCrop(Material.POTATO, Material.POTATOES, player, block);
                        break;
                }
            }
        }
    }

    private static void processInventoryForSeedsAndPlaceCrop(Material seed, Material crop, Player player, Block block) {
        if (player.getInventory().contains(seed)) {
            for (int i = 0; i < player.getInventory().getContents().length; i++) {
                if (player.getInventory().getItem(i) == null) {
                    continue;
                }
                if (player.getInventory().getItem(i).getType() == seed) {
                    ItemStack itemStack = player.getInventory().getItem(i);
                    if (itemStack.getAmount() == 1) {
                        player.getInventory().remove(itemStack);
                    } else {
                        itemStack.setAmount(itemStack.getAmount() - 1);
                        player.getInventory().setItem(i, itemStack);
                    }
                }
            }
            // place a crop of age 0 (no growth)
            Ageable ageableBlock = (Ageable) crop.createBlockData();
            ageableBlock.setAge(0);
            block.setBlockData(ageableBlock);
        }
    }
}
