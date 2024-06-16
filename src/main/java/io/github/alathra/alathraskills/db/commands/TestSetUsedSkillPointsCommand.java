package io.github.alathra.alathraskills.db.commands;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.db.DatabaseQueries;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TestSetUsedSkillPointsCommand {

    public TestSetUsedSkillPointsCommand() {
        new CommandAPICommand("testSetUsedSkillPoints_db")
            .withArguments(new PlayerArgument("targetPlayer"), new IntegerArgument("usedSkillPoints"))
            .withFullDescription("Set Used Skill Points For a Given Player.")
            .withShortDescription("Set Used Skill Points")
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
        if (args.get("usedSkillPoints") == null) {
            player.sendMessage(
                ColorParser.of("Provide a value after the player indicate number of used skill points.")
                    .parseLegacy() // Parse legacy color codes
                    .build()
            );
        }

        AlathraSkills instance = AlathraSkills.getInstance();

        Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
            DatabaseQueries.setUsedSkillPoints((Player) args.get("targetPlayer"), (Integer) args.get("usedSkillPoints"));
            String returnString =
                "Player with ID " +
                    player.getUniqueId() +
                    " has " +
                    args.get("usedSkillPoints") +
                    " set for their number of used skill points. ";
            player.sendMessage(
                ColorParser.of(returnString)
                    .parseLegacy() // Parse legacy color codes
                    .build()
            );
        });
    }
}
