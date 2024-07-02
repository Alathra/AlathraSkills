package io.github.alathra.alathraskills.utility;

import com.github.milkdrinkers.Crate.Config;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.config.ConfigHandler;
import org.jetbrains.annotations.NotNull;

/**
 * Convenience class for accessing {@link ConfigHandler#getConfig}
 */
public abstract class Cfg {
    /**
     * Convenience method for {@link ConfigHandler#getConfig} to getConnection {@link Config}
     */
    @NotNull
    public static Config get() {
        return AlathraSkills.getInstance().getConfigHandler().getConfig();
    }

    /**
     * Returns the value associated with the key in the config
     *
     * @param key Key a value is being retrieved for
     * @return The value associated with the provided key
     */
    @NotNull @Deprecated
    public static Object getValue(String key) {
        return AlathraSkills.getInstance().getConfigHandler().getConfigValue(key);
    }

}
