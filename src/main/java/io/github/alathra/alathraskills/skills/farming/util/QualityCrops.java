package io.github.alathra.alathraskills.skills.farming.util;

import io.github.alathra.alathraskills.utility.Cfg;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;

public class QualityCrops {

    public static int MAX_LEVEL = 6;

    // Call this on the EntityBreedEvent if LivingEntity instance of animal
    // animalBaby is `event`.getEntity()`
    public static void run(LivingEntity animalBaby, int skillLevel) {
        if (Math.random() >= getChance(skillLevel))
            return;

        World world = animalBaby.getWorld();
        Location location = animalBaby.getLocation();

        LivingEntity newBaby = (LivingEntity) world.spawnEntity(location, animalBaby.getType(), CreatureSpawnEvent.SpawnReason.BREEDING);
        if (newBaby instanceof Ageable newBabyAgeable) {
            newBabyAgeable.setBaby();
        } else {
            // Kill the entity if it is not ageable for some reason
            newBaby.setHealth(0.0);
        }
    }

    private static double getChance(int skillLevel) {
        return switch (skillLevel) {
            case 1 -> Double.parseDouble(Cfg.getValue("skills.farming.qualityCrops.chance.l1").toString());
            case 2 -> Double.parseDouble(Cfg.getValue("skills.farming.qualityCrops.chance.l2").toString());
            case 3 -> Double.parseDouble(Cfg.getValue("skills.farming.qualityCrops.chance.l3").toString());
            case 4 -> Double.parseDouble(Cfg.getValue("skills.farming.qualityCrops.chance.l4").toString());
            case 5 -> Double.parseDouble(Cfg.getValue("skills.farming.qualityCrops.chance.l5").toString());
            case 6 -> Double.parseDouble(Cfg.getValue("skills.farming.qualityCrops.chance.l6").toString());
            default -> 0;
        };
    }
}
