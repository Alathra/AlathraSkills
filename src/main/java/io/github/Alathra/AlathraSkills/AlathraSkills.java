package io.github.Alathra.AlathraSkills;

import com.github.milkdrinkers.colorparser.ColorParser;

import io.github.Alathra.AlathraSkills.api.SkillsManager;
import io.github.Alathra.AlathraSkills.command.CommandHandler;
import io.github.Alathra.AlathraSkills.config.ConfigHandler;
import io.github.Alathra.AlathraSkills.db.DatabaseHandler;
import io.github.Alathra.AlathraSkills.hooks.VaultHook;
import io.github.Alathra.AlathraSkills.listener.ListenerHandler;
import io.github.Alathra.AlathraSkills.utility.Logger;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

public class AlathraSkills extends JavaPlugin {
    private static AlathraSkills instance;
    private ConfigHandler configHandler;
    private DatabaseHandler databaseHandler;
    private CommandHandler commandHandler;
    private ListenerHandler listenerHandler;
    private static VaultHook vaultHook;
    
    // Internal managers
    private static SkillsManager skillsManager;

    public static AlathraSkills getInstance() {
        return instance;
    }

    @Override
    public void onLoad() {
        instance = this;
        configHandler = new ConfigHandler(instance);
        databaseHandler = new DatabaseHandler(instance);
        commandHandler = new CommandHandler(instance);
        listenerHandler = new ListenerHandler(instance);
        vaultHook = new VaultHook(instance);
        skillsManager = new SkillsManager(instance);

        configHandler.onLoad();
        databaseHandler.onLoad();
        commandHandler.onLoad();
        listenerHandler.onLoad();
        vaultHook.onLoad();
        skillsManager.onLoad();
    }

    @Override
    public void onEnable() {
        configHandler.onEnable();
        databaseHandler.onEnable();
        commandHandler.onEnable();
        listenerHandler.onEnable();
        vaultHook.onEnable();
        skillsManager.onEnable();

        if (vaultHook.isVaultLoaded()) {
            Logger.get().info(ColorParser.of("<green>Vault has been found on this server. Vault support enabled.").build());
        } else {
            Logger.get().warn(ColorParser.of("<yellow>Vault is not installed on this server. Vault support has been disabled.").build());
        }
        
    }

    @Override
    public void onDisable() {
        configHandler.onDisable();
        databaseHandler.onDisable();
        commandHandler.onDisable();
        listenerHandler.onDisable();
        vaultHook.onDisable();
        skillsManager.onDisable();
    }

    /**
     * Gets data handler.
     *
     * @return the data handler
     */
    @NotNull
    public DatabaseHandler getDataHandler() {
        return databaseHandler;
    }

    /**
     * Gets config handler.
     *
     * @return the config handler
     */
    @NotNull
    public ConfigHandler getConfigHandler() {
        return configHandler;
    }

    /**
     * Gets vault hook.
     *
     * @return the vault hook
     */
    @NotNull
    public static VaultHook getVaultHook() {
        return vaultHook;
    }
    
    @NotNull
    public static SkillsManager getSkillsManager() {
        return skillsManager;
    }
}
