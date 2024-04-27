package io.github.alathra.alathraskills.skills.farming.util;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

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
            case 1 -> 0.10;
            case 2 -> 0.20;
            case 3 -> 0.30;
            case 4 -> 0.40;
            case 5 -> 0.50;
            case 6 -> 0.60;
            default -> 0;
        };
    }
}
