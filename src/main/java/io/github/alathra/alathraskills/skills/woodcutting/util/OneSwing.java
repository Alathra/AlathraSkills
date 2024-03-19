package io.github.alathra.alathraskills.skills.woodcutting.util;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;

public class OneSwing {

    public static void fellTree(Block block) {
        Material material = block.getType();

        if (!Tag.LOGS.isTagged(material) && !Tag.LEAVES.isTagged(material))
            return;

        block.breakNaturally();
        for (BlockFace face : BlockFace.values())
            fellTree(block.getRelative(face));
    }

}
