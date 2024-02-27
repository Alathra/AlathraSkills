package io.github.alathra.alathraskills.db.testing;

import org.bukkit.entity.Player;

import com.github.milkdrinkers.colorparser.ColorParser;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.alathra.alathraskills.db.DatabaseQueries;

public class TestGetExerienceCommand {

    public TestGetExerienceCommand() {
        new CommandAPICommand("testGetExperience")
        	.withArguments(new IntegerArgument("skillCategoryID"))
            .withFullDescription("Get Experience For a Given Skill Category.")
            .withShortDescription("Get Experience")
            .withPermission("example.command")
            .executesPlayer(this::runCommand)
            .register();
    }

    private void runCommand(Player player, CommandArguments args) {
        if (args.get(0) == null) {
            player.sendMessage(
                    ColorParser.of("Provide a value after the command to indicate skill category.")
                        .parseLegacy() // Parse legacy color codes
                        .build()
            );
            return;
        }
        // TODO Make Async
        float dbReturnValue = DatabaseQueries.getSkillCategoryExperienceFloat(player, (Integer) args.get(0));
        String returnString =
        		"Player with ID " +
				player.getUniqueId() +
				" has an experience value of " +
				Float.toString(dbReturnValue) +
				" in skill category " +
				args.get(0) +
				".";
        player.sendMessage(
                ColorParser.of(returnString)
                    .parseLegacy() // Parse legacy color codes
                    .build()
        );
    }
}
