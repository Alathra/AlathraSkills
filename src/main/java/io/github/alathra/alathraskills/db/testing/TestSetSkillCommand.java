package io.github.alathra.alathraskills.db.testing;

import org.bukkit.entity.Player;

import com.github.milkdrinkers.colorparser.ColorParser;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.FloatArgument;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.alathra.alathraskills.db.DatabaseQueries;

public class TestSetSkillCommand {

    public TestSetSkillCommand() {
        new CommandAPICommand("testSetSkill")
        	.withArguments(new PlayerArgument("targetPlayer"), new IntegerArgument("skill"))
            .withFullDescription("Set Experience For a Given Skill Category.")
            .withShortDescription("Set Experience")
            .withPermission("example.command")
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
    	if (args.get("skill") == null) {
            player.sendMessage(
                    ColorParser.of("Provide a value after the target player to indicate the skill to add.")
                        .parseLegacy() // Parse legacy color codes
                        .build()
            );
        }
        // TODO Make Async
        DatabaseQueries.saveSkillInfo((Player) args.get("targetPlayer"), (Integer) args.get("skill"));
        String returnString =
        		"Player with ID " +
				((Player) args.get("targetPlayer")).getUniqueId() +
				" has had the skill " +
				args.get("skill") +
				" added in the DB.";
        player.sendMessage(
                ColorParser.of(returnString)
                    .parseLegacy() // Parse legacy color codes
                    .build()
        );
    }
}
