package io.github.alathra.alathraskills.db.commands;

import io.github.alathra.alathraskills.AlathraSkills;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.milkdrinkers.colorparser.ColorParser;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.alathra.alathraskills.db.DatabaseQueries;

public class TestGetExerienceCommand {

    public TestGetExerienceCommand() {
        new CommandAPICommand("testGetExperience_db")
        	.withArguments(new PlayerArgument("targetPlayer"), new IntegerArgument("skillCategoryID"))
            .withFullDescription("Get Experience For a Given Skill Category.")
            .withShortDescription("Get Experience")
            .withPermission("alathraskills.get")
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

        AlathraSkills instance = AlathraSkills.getInstance();

        Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
            float dbReturnValue = DatabaseQueries.getSkillCategoryExperienceFloat((Player) args.get("targetPlayer"), (Integer) args.get("skillCategoryID"));
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
        });
    }
}
