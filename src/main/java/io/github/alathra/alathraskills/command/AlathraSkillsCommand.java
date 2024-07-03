package io.github.alathra.alathraskills.command;

import dev.jorel.commandapi.CommandAPICommand;
import io.github.alathra.alathraskills.gui.GuiHelper;

public class AlathraSkillsCommand {
    public AlathraSkillsCommand() {
        new CommandAPICommand("alathraskills")
            .withShortDescription("The main command of AlathraSkills.")
            .withPermission("alathraskills.alathraskills")
            .withAliases("as", "skills")
            .executesPlayer((player, commandArguments) -> {
                GuiHelper.openMainGui(player);
            })
            .register();
    }
}
