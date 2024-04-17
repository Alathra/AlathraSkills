package io.github.alathra.alathraskills.command;

import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.Reloadable;
import io.github.alathra.alathraskills.api.testing.TestAddSkillCommandMemory;
import io.github.alathra.alathraskills.api.testing.TestDeleteSkillCommandMemory;
import io.github.alathra.alathraskills.api.testing.TestGetAllSkillsCommandMemory;
import io.github.alathra.alathraskills.api.testing.TestGetExerienceCommandMemory;
import io.github.alathra.alathraskills.api.testing.TestSetExerienceCommandMemory;
import io.github.alathra.alathraskills.db.testing.TestDeleteAllSkillsCommand;
import io.github.alathra.alathraskills.db.testing.TestGetAllSkillsCommand;
import io.github.alathra.alathraskills.db.testing.TestGetExerienceCommand;
import io.github.alathra.alathraskills.db.testing.TestHasSkillCommand;
import io.github.alathra.alathraskills.db.testing.TestSetExerienceCommand;
import io.github.alathra.alathraskills.db.testing.TestSetSkillCommand;
import dev.jorel.commandapi.CommandAPI;
import dev.jorel.commandapi.CommandAPIBukkitConfig;

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
        new ExampleCommand();
        new TestSetExerienceCommandMemory();
        new TestAddSkillCommandMemory();
        new TestDeleteSkillCommandMemory();
        new TestGetAllSkillsCommandMemory();
        new TestGetExerienceCommandMemory();
    }

    @Override
    public void onDisable() {
        CommandAPI.onDisable();
    }
}