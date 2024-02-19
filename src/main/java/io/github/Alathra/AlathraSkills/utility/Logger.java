package io.github.alathra.alathraskills.utility;


import io.github.alathra.alathraskills.AlathraSkills;
import net.kyori.adventure.text.logger.slf4j.ComponentLogger;
import org.jetbrains.annotations.NotNull;

/**
 * A class that provides shorthand access to {@link AlathraSkills#getComponentLogger}.
 */
public class Logger {
    /**
     * Get component logger. Shorthand for:
     *
     * @return the component logger {@link AlathraSkills#getComponentLogger}.
     */
    @NotNull
    public static ComponentLogger get() {
        return AlathraSkills.getInstance().getComponentLogger();
    }
}
