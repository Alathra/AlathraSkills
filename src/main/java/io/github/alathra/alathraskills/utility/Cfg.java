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


}
