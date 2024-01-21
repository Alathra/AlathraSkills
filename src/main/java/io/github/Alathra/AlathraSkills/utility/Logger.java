package io.github.Alathra.AlathraSkills.utility;


import io.github.Alathra.AlathraSkills.AlathraSkills;
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
