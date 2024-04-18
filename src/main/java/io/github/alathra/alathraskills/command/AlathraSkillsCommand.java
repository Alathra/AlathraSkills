package io.github.alathra.alathraskills.command;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import dev.triumphteam.gui.guis.Gui;
import io.github.alathra.alathraskills.gui.GuiHelper;
import org.bukkit.command.CommandSender;

public class AlathraSkillsCommand {
    public AlathraSkillsCommand() {
        new CommandAPICommand("alathraskills")
            .withShortDescription("The main command of AlathraSkills.")
            .withPermission("alathraskills.alathraskills")
            .executesPlayer((player, commandArguments) -> {
                Gui gui = GuiHelper.buildGui(GuiHelper.GuiType.MAIN);
                GuiHelper.populateMainGui(gui, player);
                gui.open(player);
            })
            .register();
    }
}
