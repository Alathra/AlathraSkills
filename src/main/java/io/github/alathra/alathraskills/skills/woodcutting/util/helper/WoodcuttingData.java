package io.github.alathra.alathraskills.skills.woodcutting.util.helper;

import org.bukkit.TreeType;

import java.util.ArrayList;

public class WoodcuttingData {

    public static ArrayList<TreeType> getNonTrees() {
        ArrayList<TreeType> nonTrees = new ArrayList<>();
        nonTrees.add(TreeType.BROWN_MUSHROOM);
        nonTrees.add(TreeType.RED_MUSHROOM);
        nonTrees.add(TreeType.CHORUS_PLANT);
        nonTrees.add(TreeType.CRIMSON_FUNGUS);
        nonTrees.add(TreeType.WARPED_FUNGUS);
        return nonTrees;
    }
}
