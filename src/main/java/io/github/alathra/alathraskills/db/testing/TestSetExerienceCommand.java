package io.github.alathra.alathraskills.db.testing;

import org.bukkit.entity.Player;

import com.github.milkdrinkers.colorparser.ColorParser;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.FloatArgument;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.alathra.alathraskills.db.DatabaseQueries;

public class TestSetExerienceCommand {

    public TestSetExerienceCommand() {
        new CommandAPICommand("testSetExperience")
        	.withArguments(new IntegerArgument("skillCategoryID"), new FloatArgument("Experience"))
            .withFullDescription("Set Experience For a Given Skill Category.")
            .withShortDescription("Set Experience")
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
        }
        if (args.get(1) == null) {
            player.sendMessage(
                    ColorParser.of("Provide a value after the skill category to indicate experience amount.")
                        .parseLegacy() // Parse legacy color codes
                        .build()
            );
            return;
        }
        // TODO Make Async
        DatabaseQueries.saveSkillCategoryExperience(player, (Integer) args.get("skillCategoryID"), (float) args.get("Experience"));
        String returnString =
        		"Player with ID " +
				player.getUniqueId() +
				" has had an experience value of " +
				args.get("Experience") +
				" set in skill category " +
				args.get("skillCategoryID") +
				".";
        player.sendMessage(
                ColorParser.of(returnString)
                    .parseLegacy() // Parse legacy color codes
                    .build()
        );
    }
}
