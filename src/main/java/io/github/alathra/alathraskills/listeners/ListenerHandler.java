package io.github.alathra.alathraskills.listeners;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.Reloadable;
import io.github.alathra.alathraskills.listeners.exp.FarmingExpListener;
import io.github.alathra.alathraskills.listeners.exp.MiningExpListener;
import io.github.alathra.alathraskills.listeners.exp.SkillPointGainListener;
import io.github.alathra.alathraskills.listeners.exp.WoodcuttingExpListener;
import io.github.alathra.alathraskills.listeners.pdc.PDCUnnaturalBlockHandler;
import io.github.alathra.alathraskills.listeners.player.PlayerJoinListener;
import io.github.alathra.alathraskills.listeners.player.PlayerLeaveListener;
import io.github.alathra.alathraskills.listeners.player.SkillsPlayerLoadedListener;
import io.github.alathra.alathraskills.listeners.player.SkillsPlayerUnloadedListener;
import io.github.alathra.alathraskills.listeners.skills.FarmingSkillsListener;
import io.github.alathra.alathraskills.listeners.skills.MiningSkillsListener;
import io.github.alathra.alathraskills.listeners.skills.WoodcuttingSkillsListener;


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
        plugin.getServer().getPluginManager().registerEvents(new PlayerJoinListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new PlayerLeaveListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new SkillsPlayerLoadedListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new SkillsPlayerUnloadedListener(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new VaultListener(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new SaplingBreakListener(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new FarmingExpListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new MiningExpListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new WoodcuttingExpListener(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new PDCUnnaturalBlockHandler(), plugin);

        plugin.getServer().getPluginManager().registerEvents(new FarmingSkillsListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new MiningSkillsListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new WoodcuttingSkillsListener(), plugin);
        plugin.getServer().getPluginManager().registerEvents(new SkillPointGainListener(), plugin);
    }

    @Override
    public void onDisable() {
    }
}
