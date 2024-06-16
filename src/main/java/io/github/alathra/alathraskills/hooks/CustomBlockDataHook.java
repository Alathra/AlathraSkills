package io.github.alathra.alathraskills.hooks;

import com.github.milkdrinkers.customblockdata.CustomBlockData;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.Reloadable;

public class CustomBlockDataHook implements Reloadable {

    private final AlathraSkills plugin;

    public CustomBlockDataHook(AlathraSkills plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onLoad() {

    }

    @Override
    public void onEnable() {
        CustomBlockData.registerListener(plugin);

    }

    @Override
    public void onDisable() {

    }

}
