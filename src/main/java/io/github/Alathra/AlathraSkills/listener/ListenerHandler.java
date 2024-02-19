package io.github.alathra.alathraskills.listener;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.Reloadable;

/**
 * A class to handle registration of event listeners.
 */
public class ListenerHandler implements Reloadable {
    private final AlathraSkills plugin;

    public ListenerHandler(AlathraSkills plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onLoad() {
    }

    @Override
    public void onEnable() {
        // Register listeners here
        //plugin.getServer().getPluginManager().registerEvents(new PlayerJoinListener(plugin), plugin);
        plugin.getServer().getPluginManager().registerEvents(new VaultListener(), plugin);
    }

    @Override
    public void onDisable() {
    }
}
