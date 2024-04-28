package io.github.alathra.alathraskills.skills.mining.util.helper;

import org.bukkit.Material;

import java.util.ArrayList;

public class MiningData {

    public static ArrayList<Material> getNaturalStoneBlocks() {
        ArrayList<Material> stoneBlocks = new ArrayList<>();
        stoneBlocks.add(Material.STONE);
        stoneBlocks.add(Material.DEEPSLATE);
        stoneBlocks.add(Material.GRANITE);
        stoneBlocks.add(Material.DIORITE);
        stoneBlocks.add(Material.ANDESITE);
        stoneBlocks.add(Material.CALCITE);
        stoneBlocks.add(Material.TUFF);
        stoneBlocks.add(Material.DRIPSTONE_BLOCK);
        return stoneBlocks;
    }
}
