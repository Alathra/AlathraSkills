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

public class TestHasSkillCommand {

    public TestHasSkillCommand() {
        new CommandAPICommand("testHasSkill_db")
            .withArguments(new PlayerArgument("targetPlayer"), new IntegerArgument("skill"))
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
        }
        if (args.get("skill") == null) {
            player.sendMessage(
                ColorParser.of("Provide a value after the targetPlayer to indicate the skill to check.")
                    .parseLegacy() // Parse legacy color codes
                    .build()
            );
            return;
        }

        AlathraSkills instance = AlathraSkills.getInstance();

        Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
            boolean dbReturnValue = DatabaseQueries.doesPlayerHaveSkill((Player) args.get("targetPlayer"), (Integer) args.get("skill"));
            String returnString =
                "Player with ID " +
                    ((Player) args.get("targetPlayer")).getUniqueId() +
                    " does ";
            if (!dbReturnValue) {
                returnString += "not ";
            }
            returnString +=
                "have the skill " +
                    args.get("skill") +
                    ".";
            player.sendMessage(
                ColorParser.of(returnString)
                    .parseLegacy() // Parse legacy color codes
                    .build()
            );
        });
    }
}
