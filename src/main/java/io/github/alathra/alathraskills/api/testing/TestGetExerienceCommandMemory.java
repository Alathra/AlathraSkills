package io.github.alathra.alathraskills.api.testing;

import org.bukkit.entity.Player;

import com.github.milkdrinkers.colorparser.ColorParser;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import io.github.alathra.alathraskills.db.DatabaseQueries;

public class TestGetExerienceCommandMemory {

    public TestGetExerienceCommandMemory() {
        new CommandAPICommand("testGetExperience_memory")
        	.withArguments(new PlayerArgument("targetPlayer"), new IntegerArgument("skillCategoryID"))
            .withFullDescription("Get Experience For a Given Skill Category.")
            .withShortDescription("Get Experience")
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
        }        if (args.get("skillCategoryID") == null) {
            player.sendMessage(
                    ColorParser.of("Provide a value after the target player to indicate skill category.")
                        .parseLegacy() // Parse legacy color codes
                        .build()
            );
            return;
        }
        float dbReturnValue = SkillsPlayerManager.getSkillCategoryExperience((Player) args.get("targetPlayer"), (Integer) args.get("skillCategoryID"));
        String returnString =
        		"Player with ID " +
				((Player) args.get("targetPlayer")).getUniqueId() +
				" has an experience value of " +
				Float.toString(dbReturnValue) +
				" in skill category " +
				args.get("skillCategoryID") +
				".";
        player.sendMessage(
                ColorParser.of(returnString)
                    .parseLegacy() // Parse legacy color codes
                    .build()
        );
    }
}
