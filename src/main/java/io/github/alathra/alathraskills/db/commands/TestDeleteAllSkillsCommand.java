package io.github.alathra.alathraskills.db.commands;

import org.bukkit.entity.Player;

import com.github.milkdrinkers.colorparser.ColorParser;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.alathra.alathraskills.db.DatabaseQueries;

public class TestDeleteAllSkillsCommand {

    public TestDeleteAllSkillsCommand() {
        new CommandAPICommand("testDeleteSkills_db")
        	.withArguments(new PlayerArgument("targetPlayer"))
            .withFullDescription("Deletes all skills for this player")
            .withShortDescription("Get Experience")
            .withPermission("alathraskills.set")
            .executesPlayer(this::runCommand)
            .register();
    }

    private void runCommand(Player player, CommandArguments args) {
        if (args.get("targetPlayer") == null) {
            player.sendMessage(
                    ColorParser.of("Provide a value after the command to indicate target player.")
                        .parseLegacy() // Parse legacy color codes
                        .build()
            );
            return;
        }
        // TODO Make Async
        DatabaseQueries.deletePlayerSkills((Player) args.get("targetPlayer"));
        String returnString =
        		"Player with ID " +
				((Player) args.get("targetPlayer")).getUniqueId() +
				" has had all of their skills deleted.";
        player.sendMessage(
                ColorParser.of(returnString)
                    .parseLegacy() // Parse legacy color codes
                    .build()
        );
    }
}
