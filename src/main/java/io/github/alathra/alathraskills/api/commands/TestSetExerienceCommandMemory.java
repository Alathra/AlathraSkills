package io.github.alathra.alathraskills.api.commands;

import io.github.alathra.alathraskills.AlathraSkills;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.github.milkdrinkers.colorparser.ColorParser;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.FloatArgument;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;

public class TestSetExerienceCommandMemory {

    public TestSetExerienceCommandMemory() {
        new CommandAPICommand("testSetExperience_memory")
        	.withArguments(new PlayerArgument("targetPlayer"), new IntegerArgument("skillCategoryID"), new FloatArgument("Experience"))
            .withFullDescription("Set Experience For a Given Skill Category in Memory.")
            .withShortDescription("Set Experience")
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
    	if (args.get("skillCategoryID") == null) {
            player.sendMessage(
                    ColorParser.of("Provide a value after the command to indicate skill category.")
                        .parseLegacy() // Parse legacy color codes
                        .build()
            );
        }
        if (args.get("Experience") == null) {
            player.sendMessage(
                    ColorParser.of("Provide a value after the skill category to indicate experience amount.")
                        .parseLegacy() // Parse legacy color codes
                        .build()
            );
            return;
        }

        AlathraSkills instance = AlathraSkills.getInstance();

        Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
            SkillsPlayerManager.setPlayerExperience((Player) args.get("targetPlayer"), (Integer) args.get("skillCategoryID"), (float) args.get("Experience"));
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
        });
    }
}
