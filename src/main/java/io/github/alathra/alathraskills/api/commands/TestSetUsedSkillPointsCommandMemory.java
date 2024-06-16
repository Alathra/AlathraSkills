package io.github.alathra.alathraskills.api.commands;

import com.github.milkdrinkers.colorparser.ColorParser;
import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.alathra.alathraskills.AlathraSkills;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class TestSetUsedSkillPointsCommandMemory {

    public TestSetUsedSkillPointsCommandMemory() {
        new CommandAPICommand("testSetUsedSkillPoints_memory")
            .withArguments(new PlayerArgument("targetPlayer"), new IntegerArgument("usedSkillPoints"))
            .withFullDescription("Set Used Skill Points For a Given Player in Memory.")
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
                ColorParser.of("Provide a value after the player to indicate used skill points.")
                    .parseLegacy() // Parse legacy color codes
                    .build()
            );
        }

        AlathraSkills instance = AlathraSkills.getInstance();

        Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
            SkillsPlayerManager.setPlayerUsedSkillPoints((Player) args.get("targetPlayer"), (Integer) args.get("usedSkillPoints"));
            String returnString =
                "Player with ID " +
                    player.getUniqueId() +
                    " has had an used skill pionts value of " +
                    args.get("usedSkillPoints") +
                    " set.";
            player.sendMessage(
                ColorParser.of(returnString)
                    .parseLegacy() // Parse legacy color codes
                    .build()
            );
        });
    }
}
