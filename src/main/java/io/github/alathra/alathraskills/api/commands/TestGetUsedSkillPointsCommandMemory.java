package io.github.alathra.alathraskills.api.commands;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import org.bukkit.entity.Player;

public class TestGetUsedSkillPointsCommandMemory {

    public TestGetUsedSkillPointsCommandMemory() {
        new CommandAPICommand("testGetUsedSkillPoints_memory")
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
        Integer dbReturnValue = AlathraSkills.getSkillsPlayerManager().getUsedSkillPoints((Player) args.get("targetPlayer"));
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
    }
}
