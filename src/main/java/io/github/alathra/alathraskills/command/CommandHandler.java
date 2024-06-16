package io.github.alathra.alathraskills.command;

import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.Reloadable;
import io.github.alathra.alathraskills.api.commands.*;
import io.github.alathra.alathraskills.db.commands.*;

/**
 * A class to handle registration of commands.
 */
public class CommandHandler implements Reloadable {
    private final AlathraSkills plugin;

    public CommandHandler(AlathraSkills plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onLoad() {
        CommandAPI.onLoad(new CommandAPIBukkitConfig(plugin).shouldHookPaperReload(true).silentLogs(true));
    }

    @Override
    public void onEnable() {
        CommandAPI.onEnable();

        // Register commands here
        new TestGetExerienceCommand();
        new TestSetExerienceCommand();
        new TestDeleteAllSkillsCommand();
        new TestGetAllSkillsCommand();
        new TestSetSkillCommand();
        new TestHasSkillCommand();
        new AlathraSkillsCommand();
        new TestSetExerienceCommandMemory();
        new TestAddSkillCommandMemory();
        new TestDeleteSkillCommandMemory();
        new TestGetAllSkillsCommandMemory();
        new TestGetExerienceCommandMemory();

        new TestGetUsedSkillPointsCommand();
        new TestGetUsedSkillPointsCommandMemory();
        new TestSetUsedSkillPointsCommand();
        new TestSetUsedSkillPointsCommandMemory();
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }
}