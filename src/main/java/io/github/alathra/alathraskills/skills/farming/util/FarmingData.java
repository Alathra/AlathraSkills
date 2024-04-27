package io.github.alathra.alathraskills.skills.farming.util;

import org.bukkit.Material;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class FarmingData {
    public static ArrayList<Material> getBonemealableCrops() {
        ArrayList<Material> crops = new ArrayList<>();
        crops.add(Material.BAMBOO);
        crops.add(Material.BAMBOO_SAPLING);
        crops.add(Material.BEETROOTS);
        crops.add(Material.CARROTS);
        crops.add(Material.CAVE_VINES);
        crops.add(Material.COCOA);
        crops.add(Material.KELP);
        crops.add(Material.MELON_STEM);
        crops.add(Material.POTATOES);
        crops.add(Material.PUMPKIN_STEM);
        crops.add(Material.SWEET_BERRY_BUSH);
        crops.add(Material.WHEAT);
        return crops;
    }

    public static ArrayList<Material> getBreakableCrops() {
        ArrayList<Material> crops = new ArrayList<>();
        crops.add(Material.BAMBOO);
        crops.add(Material.BEETROOTS);
        crops.add(Material.CARROTS);
        crops.add(Material.CAVE_VINES);
        crops.add(Material.COCOA);
        crops.add(Material.KELP);
        crops.add(Material.POTATOES);
        crops.add(Material.WHEAT);
        crops.add(Material.CACTUS);
        crops.add(Material.SUGAR_CANE);
        crops.add(Material.NETHER_WART);
        crops.add(Material.MELON);
        crops.add(Material.PUMPKIN);
        return crops;
    }

    public static ArrayList<Material> getSpreadableSeeds() {
        ArrayList<Material> seeds = new ArrayList<>();
        seeds.add(Material.WHEAT_SEEDS);
        seeds.add(Material.BEETROOT_SEEDS);
        seeds.add(Material.CARROT);
        seeds.add(Material.POTATO);
        return seeds;
    }
}
