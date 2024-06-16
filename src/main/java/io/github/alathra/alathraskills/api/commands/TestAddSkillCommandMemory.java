package io.github.alathra.alathraskills.api.commands;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import org.bukkit.entity.Player;

public class TestAddSkillCommandMemory {

    public TestAddSkillCommandMemory() {
        new CommandAPICommand("testAddSkill_memory")
            .withArguments(new PlayerArgument("targetPlayer"), new IntegerArgument("skill"))
            .withFullDescription("Add Skill for a Given Player.")
            .withShortDescription("Add Skill")
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
        if (args.get("skill") == null) {
            player.sendMessage(
                ColorParser.of("Provide a value after the target player to indicate the skill to add.")
                    .parseLegacy() // Parse legacy color codes
                    .build()
            );
        }
        SkillsPlayerManager.addPlayerSkill((Player) args.get("targetPlayer"), (Integer) args.get("skill"));
        String returnString =
            "Player with ID " +
                ((Player) args.get("targetPlayer")).getUniqueId() +
                " has had the skill " +
                args.get("skill") +
                " added in memory.";
        player.sendMessage(
            ColorParser.of(returnString)
                .parseLegacy() // Parse legacy color codes
                .build()
        );
    }
}
