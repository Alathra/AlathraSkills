package io.github.alathra.alathraskills.skills.farming;

import com.github.milkdrinkers.colorparser.ColorParser;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsManager;
import io.github.alathra.alathraskills.skills.Skill;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Ageable;
import org.bukkit.entity.LivingEntity;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class QualityCropsFour extends Skill {

    private static double QUALILY_CROPS_FOUR_CHANCE = 0.40;
    private SkillsManager skillsManager;

    public QualityCropsFour(int id) {
        super(id, "Quality Crops 1", "Get a chance to breed more animals!");

        ItemStack icon = new ItemStack(Material.LEAD);
        ItemMeta meta = icon.getItemMeta();
        meta.displayName(ColorParser.of("<green><bold>" + super.getName() + "</green></bold>").build());
        meta.lore(List.of(ColorParser.of("<yellow>Level 2</yellow>").build(),
            ColorParser.of("<red><italics>" + super.getDescription() + "</gray></italics>").build()));
        icon.setItemMeta(meta);
        super.setIcon(icon);

        skillsManager = AlathraSkills.getSkillsManager();
        super.setCategory(skillsManager.skillCategories.get(3));
    }

    // Call this on the EntityBreedEvent if LivingEntity instance of animal
    // animalBaby is `event`.getEntity()`
    public static void runQualityCropsOne(LivingEntity animalBaby) {
        if (Math.random() >= QUALILY_CROPS_FOUR_CHANCE)
            return;

        World world = animalBaby.getWorld();
        Location locaiton = animalBaby.getLocation();

        LivingEntity newBaby = (LivingEntity) world.spawnEntity(locaiton, animalBaby.getType(), CreatureSpawnEvent.SpawnReason.BREEDING);
        if(newBaby instanceof Ageable) {
            Ageable newBabyAgeable = (Ageable) newBaby;
            newBabyAgeable.setBaby();
        } else {
            // Kill the entity if it is not ageable for some reason
            newBaby.setHealth(0.0);
        }

    }

}
