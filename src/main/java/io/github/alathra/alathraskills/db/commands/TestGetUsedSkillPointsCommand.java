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

public class TestGetUsedSkillPointsCommand {

    public TestGetUsedSkillPointsCommand() {
        new CommandAPICommand("testGetUsedSkillPoints_db")
        	.withArguments(new PlayerArgument("targetPlayer"))
            .withFullDescription("Get Used Skill Points For a Given Player.")
            .withShortDescription("Get Used Skill Points")
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
        }

        AlathraSkills instance = AlathraSkills.getInstance();

        Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
            Integer dbReturnValue = DatabaseQueries.getUsedSkillPoints((Player) args.get("targetPlayer"));
            String returnString =
                "Player with ID " +
                    ((Player) args.get("targetPlayer")).getUniqueId() +
                    " has " +
                    Float.toString(dbReturnValue) +
                    " used skill points.";
            player.sendMessage(
                ColorParser.of(returnString)
                    .parseLegacy() // Parse legacy color codes
                    .build()
            );
        });
    }
}
