package io.github.alathra.alathraskills.skills.mining.util;

import io.github.alathra.alathraskills.utility.Cfg;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class Spelunker {

    public static final int MAX_LEVEL = 4;

    public static void run(EntityDamageEvent event, Player player, int skilLLevel) {
        double damageReduction = getDamageReduction(skilLLevel);
        if (event.getFinalDamage() <= damageReduction) {
            event.setDamage(0.0);
        } else {
            event.setDamage(event.getFinalDamage() - damageReduction);
        }
    }

    private static double getDamageReduction(int skillLevel) {
        // Hearts are measured by 1/2 heart, i.e. 2.0 = 1 heart
        return switch (skillLevel) {
            case 1 -> Cfg.get().getDouble("skills.mining.spelunker.damageReduction.l1");
            case 2 -> Cfg.get().getDouble("skills.mining.spelunker.damageReduction.l2");
            case 3 -> Cfg.get().getDouble("skills.mining.spelunker.damageReduction.l3");
            case 4 -> Cfg.get().getDouble("skills.mining.spelunker.damageReduction.l4");
            default -> 0;
        };
    }
}
