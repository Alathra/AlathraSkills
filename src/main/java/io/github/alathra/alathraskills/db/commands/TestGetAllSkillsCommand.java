package io.github.alathra.alathraskills.db.commands;

import java.util.Iterator;
import java.util.UUID;

import io.github.alathra.alathraskills.AlathraSkills;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.jooq.Result;

import com.github.milkdrinkers.colorparser.ColorParser;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.alathra.alathraskills.db.DatabaseQueries;
import io.github.alathra.alathraskills.db.schema.tables.records.PlayerSkillinfoRecord;

public class TestGetAllSkillsCommand {

    public TestGetAllSkillsCommand() {
        new CommandAPICommand("testGetAllSkills_db")
        	.withArguments(new PlayerArgument("targetPlayer"))
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

        AlathraSkills instance = AlathraSkills.getInstance();

        Bukkit.getScheduler().runTaskAsynchronously(instance, () -> {
            Result<PlayerSkillinfoRecord> dbReturnValue = DatabaseQueries.fetchPlayerSkills((Player) args.get("targetPlayer"));
            String finalSkillString = "";
            if (dbReturnValue != null) {
                for (Iterator<PlayerSkillinfoRecord> iterator = dbReturnValue.iterator(); iterator.hasNext();) {
                    PlayerSkillinfoRecord playerSkillinfoRecord = iterator.next();
                    finalSkillString += playerSkillinfoRecord.getSkillid() + ", ";
                }
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
        });
    }
}
