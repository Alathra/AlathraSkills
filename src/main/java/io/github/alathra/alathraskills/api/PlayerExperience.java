package io.github.alathra.alathraskills.api;

import org.bukkit.OfflinePlayer;

import java.util.HashMap;

public class PlayerExperience {


    private OfflinePlayer p;
    private HashMap<Integer, Float> experienceValues;

    public PlayerExperience(OfflinePlayer p, HashMap<Integer, Float> experienceValues) {
        this.setP(p);
        this.setExperienceValues(experienceValues);
    }


    public OfflinePlayer getP() {
        return p;
    }

    public void setP(OfflinePlayer p) {
        this.p = p;
    }

    public HashMap<Integer, Float> getExperienceValues() {
        return experienceValues;
    }

    public void setExperienceValues(HashMap<Integer, Float> experienceValues) {
        this.experienceValues = experienceValues;
    }

}
