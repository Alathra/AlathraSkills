package io.github.alathra.alathraskills.skills.farming.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

import io.github.alathra.alathraskills.utility.Cfg;

public class QualityCrops {

    // Call this on the EntityBreedEvent if LivingEntity instance of animal
    // animalBaby is `event`.getEntity()`
    public static void run(LivingEntity animalBaby, int skillLevel) {
        if (Math.random() >= getChance(skillLevel))
            return;

        World world = animalBaby.getWorld();
        Location location = animalBaby.getLocation();

        LivingEntity newBaby = (LivingEntity) world.spawnEntity(location, animalBaby.getType(), CreatureSpawnEvent.SpawnReason.BREEDING);
        if(newBaby instanceof Ageable newBabyAgeable) {
            newBabyAgeable.setBaby();
        } else {
            // Kill the entity if it is not ageable for some reason
            newBaby.setHealth(0.0);
        }
    }

    private static double getChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> Cfg.get().get("skills.farming.qualityCrops.chance.l1", 0.10);
            case 2 -> Cfg.get().get("skills.farming.qualityCrops.chance.l2", 0.20);
            case 3 -> Cfg.get().get("skills.farming.qualityCrops.chance.l3", 0.30);
            case 4 -> Cfg.get().get("skills.farming.qualityCrops.chance.l4", 0.40);
            case 5 -> Cfg.get().get("skills.farming.qualityCrops.chance.l5", 0.50);
            case 6 -> Cfg.get().get("skills.farming.qualityCrops.chance.l6", 0.60);
            default -> 0;
        };
    }
}
