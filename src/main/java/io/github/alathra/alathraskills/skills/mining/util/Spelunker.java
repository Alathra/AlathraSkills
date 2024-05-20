package io.github.alathra.alathraskills.skills.mining.util;

import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

import io.github.alathra.alathraskills.utility.Cfg;

public class Spelunker {

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
            case 1 -> Double.parseDouble(Cfg.getValue("skills.mining.spelunker.damageReduction.l1").toString());
            case 2 -> Double.parseDouble(Cfg.getValue("skills.mining.spelunker.damageReduction.l2").toString());
            case 3 -> Double.parseDouble(Cfg.getValue("skills.mining.spelunker.damageReduction.l3").toString());
            case 4 -> Double.parseDouble(Cfg.getValue("skills.mining.spelunker.damageReduction.l4").toString());
            default -> 0;
        };
    }
}
