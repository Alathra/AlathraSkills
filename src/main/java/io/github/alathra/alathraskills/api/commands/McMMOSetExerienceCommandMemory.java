package io.github.alathra.alathraskills.api.commands;

import org.bukkit.entity.Player;

import com.github.milkdrinkers.colorparser.ColorParser;
import com.gmail.nossr50.api.exceptions.McMMOPlayerNotFoundException;
import com.gmail.nossr50.datatypes.player.McMMOPlayer;
import com.gmail.nossr50.util.player.UserManager;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.arguments.IntegerArgument;
import dev.jorel.commandapi.arguments.PlayerArgument;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.alathra.alathraskills.api.SkillsPlayerManager;

public class McMMOSetExerienceCommandMemory {

    public McMMOSetExerienceCommandMemory() {
        new CommandAPICommand("testSetExperience_memory_mcmmo")
        	.withArguments(new PlayerArgument("targetPlayer"), new IntegerArgument("skillCategoryID"))
            .withFullDescription("Set Experience For a Given Skill Category in Memory based on McMMO Data.")
            .withShortDescription("Set Experience from McMMO")
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

        McMMOPlayer playerMcMMO;
        try {
        	playerMcMMO = UserManager.getPlayer((Player) args.get("targetPlayer"));        	
        } catch (McMMOPlayerNotFoundException e){
            player.sendMessage(
                    ColorParser.of("Player does not exist in the context of McMMO.")
                        .parseLegacy() // Parse legacy color codes
                        .build()
            );
            return;        	
        }
        float skillLevel;
        switch (((Integer) args.get("skillCategoryID")).intValue()) {
        case 1:
        	skillLevel = 1.0f * playerMcMMO.getHerbalismManager().getSkillLevel();
        	break;
        case 2:
        	skillLevel = 1.0f * playerMcMMO.getMiningManager().getSkillLevel();
        	break;
        case 3:
        	skillLevel = 1.0f * playerMcMMO.getWoodcuttingManager().getSkillLevel();
        	break;
        default:
            player.sendMessage(
                    ColorParser.of("Please provide a valid skill category ID (1, 2, 3)")
                        .parseLegacy() // Parse legacy color codes
                        .build()
            );
            return;
        }
        float maxSkillLevel = 200.0f;
        float conversionFactor = 7000.0f;
        float finalExperience = (skillLevel / maxSkillLevel) * conversionFactor;
        SkillsPlayerManager.setPlayerExperience(player, (Integer) args.get("skillCategoryID"), finalExperience);
        String returnString =
        		"Player with ID " +
				player.getUniqueId() +
				" has had an experience value of " +
				finalExperience +
				" set in skill category " +
				args.get("skillCategoryID") +
				".";
        player.sendMessage(
                ColorParser.of(returnString)
                    .parseLegacy() // Parse legacy color codes
                    .build()
        );
    }
}
