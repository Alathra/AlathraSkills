package io.github.alathra.alathraskills.skills.mining.util.helper;

import org.bukkit.Material;
import org.bukkit.Tag;

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

    public static ArrayList<Material> getOres() {
        ArrayList<Material> ores = new ArrayList<>();
        ores.add(Material.COAL_ORE);
        ores.add(Material.DEEPSLATE_COAL_ORE);
        ores.add(Material.COPPER_ORE);
        ores.add(Material.DEEPSLATE_COPPER_ORE);
        ores.add(Material.IRON_ORE);
        ores.add(Material.DEEPSLATE_IRON_ORE);
        ores.add(Material.GOLD_ORE);
        ores.add(Material.DEEPSLATE_GOLD_ORE);
        ores.add(Material.DIAMOND_ORE);
        ores.add(Material.DEEPSLATE_DIAMOND_ORE);
        ores.add(Material.EMERALD_ORE);
        ores.add(Material.DEEPSLATE_EMERALD_ORE);
        ores.add(Material.LAPIS_ORE);
        ores.add(Material.DEEPSLATE_LAPIS_ORE);
        ores.add(Material.REDSTONE_ORE);
        ores.add(Material.DEEPSLATE_REDSTONE_ORE);
        return ores;
    }
}
