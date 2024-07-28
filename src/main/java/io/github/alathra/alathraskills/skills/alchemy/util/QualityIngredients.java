package io.github.alathra.alathraskills.skills.alchemy.util;

import io.github.alathra.alathraskills.AlathraSkills;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.event.inventory.BrewEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class QualityIngredients {
    // NOTES
    // Amplifier starts at 0 (i.e. level 1 is "0")
    // Duration is in ticks (i.e. 3:00 = 3600 ticks)
    public static void run(BrewEvent event, int skillLevel) {
        HashMap<ItemStack, Integer> resultSlots = new HashMap<>();
        List<ItemStack> results = event.getResults();

        // Run at next tick
        Bukkit.getScheduler().runTask(AlathraSkills.getInstance(), () -> {
            for (int i = 0; i < 3; i++) {
                ItemStack item = event.getContents().getItem(i);
                if (item == null) {
                    continue;
                }
                if (results.contains(item)) {
                    results.remove(item); // removes first occurrence
                    resultSlots.put(item, i);
                }
            }

            for (Map.Entry<ItemStack, Integer> entry : resultSlots.entrySet()) {
                // Get base potion information
                PotionMeta potionMeta = (PotionMeta) entry.getKey().getItemMeta();
                if (potionMeta == null) {
                    return;
                }
                if (potionMeta.getBasePotionType().getPotionEffects().isEmpty()) {
                    return;
                }
                PotionEffect basePotionEffect = potionMeta.getBasePotionType().getPotionEffects().get(0);
                Material baseMaterial = entry.getKey().getType();
                PotionEffectType baseEffectType = basePotionEffect.getType();
                int baseAmplifier = basePotionEffect.getAmplifier();
                int baseDuration = basePotionEffect.getDuration();
                Color baseColor = potionMeta.getColor();

                // Create new potion, replace in inventory slot
                ItemStack newPotion = new ItemStack(baseMaterial);
                PotionEffect newPotionEffect = new PotionEffect(baseEffectType, baseDuration + getDurationExtension(skillLevel), baseAmplifier);
                potionMeta.addCustomEffect(newPotionEffect, true);
                potionMeta.setColor(baseColor);
                newPotion.setItemMeta(potionMeta);
                event.getContents().setItem(entry.getValue(), newPotion);
            }
        });

    }

    private static int getDurationExtension(int skillLevel) {
        // In ticks. (seconds x 20 = ticks)
        return switch (skillLevel) {
            case 1 -> 15 * 20;
            case 2 -> 31 * 20;
            case 3 -> 45 * 20;
            case 4 -> 60 * 20;
            case 5 -> 30 * 20;
            default -> 0;
        };
    }
}
