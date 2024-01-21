package io.github.Alathra.AlathraSkills.utility;

import io.github.Alathra.AlathraSkills.AlathraSkills;
import io.github.Alathra.AlathraSkills.config.ConfigHandler;
import com.github.milkdrinkers.Crate.Config;
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
