package io.github.alathra.alathraskills.command;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.events.SkillPointGainEvent;
import io.github.alathra.alathraskills.gui.GuiHelper;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class AlathraSkillsCommand {
    public AlathraSkillsCommand() {
        new CommandAPICommand("alathraskills")
            .withShortDescription("The main command of AlathraSkills.")
            .withPermission("alathraskills.alathraskills")
            .withAliases("as", "skills")
            .executesPlayer((player, commandArguments) -> {
                GuiHelper.openMainGui(player);
            })
            .withSubcommand(
                new CommandAPICommand("add")
                    .withPermission("alathraskills.add")
                    .withSubcommands(
                        new CommandAPICommand("exp")
                            .withPermission("alathraskills.add.exp")
                            .withArguments(new PlayerArgument("player"), new IntegerArgument("exp"))
                            .executes((sender, args) -> {
                                Player player = (Player) args.get("player");

                                boolean gainedSkillpoint = AlathraSkills.getSkillsPlayerManager().getSkillsPlayer(player).addNextSkillpointsProgress((Float) args.get("exp"));

                                if (gainedSkillpoint) {
                                    Bukkit.getPluginManager().callEvent(new SkillPointGainEvent(AlathraSkills.getSkillsPlayerManager().getSkillsPlayer(player)));
                                }
                            }),
                        new CommandAPICommand("skillpoint")
                            .withPermission("alathraskills.add.skillpoint")
                            .withArguments(new PlayerArgument("player"), new IntegerArgument("skillpoints"))
                            .executes((sender, args) -> {
                                Player player = (Player) args.get("player");
                                AlathraSkills.getSkillsPlayerManager().getSkillsPlayer(player).addTotalSkillpoints((int) args.get("skillpoints"));
                            })
                    )
            )
            .register();
    }
}
