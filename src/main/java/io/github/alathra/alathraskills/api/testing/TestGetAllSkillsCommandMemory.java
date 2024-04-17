package io.github.alathra.alathraskills.api.testing;

import java.util.Map.Entry;
import java.util.stream.Stream;

import org.bukkit.entity.Player;

import com.github.milkdrinkers.colorparser.ColorParser;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.alathra.alathraskills.api.SkillDetails;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;

public class TestGetAllSkillsCommandMemory {

    public TestGetAllSkillsCommandMemory() {
        new CommandAPICommand("testGetAllSkills_memory")
        	.withArguments(new PlayerArgument("targetPlayer"))
            .withFullDescription("Get all Skills for a Given Player.")
            .withShortDescription("Get Skills")
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

        Stream<Entry<Integer, SkillDetails>> allSkills =
        		SkillsPlayerManager.getAllSkills((Player) args.get("targetPlayer"));
    	String finalSkillString = "";
        if (allSkills != null) {
        	finalSkillString = allSkills.map(skill -> skill.getKey() + ", ").reduce(finalSkillString, (a, b) -> a+b);
        	if (finalSkillString.length() > 2) {
        		finalSkillString =finalSkillString.substring(0, finalSkillString.length() - 2);
        	}
        }
        String returnString =
        	"Player with ID " +
        	((Player) args.get("targetPlayer")).getUniqueId();
        if (finalSkillString.length() > 0) {
			returnString +=
				" has the following skills: " +
				finalSkillString;			
        } else {
			returnString +=
				" does not have any skills.";			
        }
        player.sendMessage(
                ColorParser.of(returnString)
                    .parseLegacy() // Parse legacy color codes
                    .build()
        );
    }
}
