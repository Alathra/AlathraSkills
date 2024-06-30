package io.github.alathra.alathraskills.api.commands;

import dev.jorel.commandapi.CommandAPICommand;
import dev.jorel.commandapi.executors.CommandArguments;
import io.github.alathra.alathraskills.utility.SoundEffectsUtil;
import org.bukkit.entity.Player;

public class TestPlaySoundEffects {

    public TestPlaySoundEffects() {
        new CommandAPICommand("testPlaySoundEffects")
            .withPermission("alathraskills.sounds")
            .executesPlayer(this::runCommand)
            .register();
    }

    private void runCommand(Player player, CommandArguments args) {
        SoundEffectsUtil.playLevelUpSound(player);
    }
}
