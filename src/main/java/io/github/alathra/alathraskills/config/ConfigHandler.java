package io.github.alathra.alathraskills.config;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.Reloadable;
import com.github.milkdrinkers.Crate.Config;

import java.util.HashMap;

import javax.inject.Singleton;

/**
 * A class that generates/loads & provides access to a configuration file.
 */
@Singleton
public class ConfigHandler implements Reloadable {
    private final AlathraSkills plugin;
    private Config cfg;
    private HashMap<String, Object> configMap;

    /**
     * Instantiates a new Config handler.
     *
     * @param plugin the plugin instance
     */
    public ConfigHandler(AlathraSkills plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onLoad() {
        cfg = new Config("config", plugin.getDataFolder().getPath(), plugin.getResource("config.yml")); // Create a config file from the template in our resources folder
        configMap = new HashMap<String, Object>();
        generateMap();
    }
    
    private void generateMap() {
        for (String i : cfg.singleLayerKeySet()) {
	        generateMapHelper(i, cfg.getFileData().get(i));
        }
    }

    private void generateMapHelper(String prefix, Object value) {
    	if (cfg.singleLayerKeySet(prefix).size() == 0) {
    		configMap.put(prefix, value);
    	} else {
            for (Object j : cfg.singleLayerKeySet(prefix)) {
            	String key = prefix + "." + j;
    			if (key.length() < 500) {
    		        generateMapHelper(key, cfg.getFileData().get(key));				
    			}
    		}    		
    	}
    }

    @Override
    public void onEnable() {
    }

    @Override
    public void onDisable() {
    }

    /**
     * Gets examplePlugin config object.
     *
     * @return the examplePlugin config object
     */
    public Config getConfig() {
        return cfg;
    }
    
    /**
     * Gets the config value associated with a given key
     * 
     * @param key The key associated with the value we are getting
     * from the config
     * @throws NullPointerException When map isn't initialized
     */
    public Object getConfigValue(String key) throws NullPointerException {
    	if (configMap == null) {
    		throw new NullPointerException("Map not initialized");
    	} else {
    		return configMap.get(key);
    	}
    }
}
