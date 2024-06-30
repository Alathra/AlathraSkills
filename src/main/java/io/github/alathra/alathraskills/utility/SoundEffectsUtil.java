package io.github.alathra.alathraskills.utility;

import io.github.alathra.alathraskills.AlathraSkills;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;

public class SoundEffectsUtil {

    // Setting the sound category for all the sound effects
    // Not entirely sure what to do here, so I put it under "Records" for now
    private static final SoundCategory SOUND_CATEGORY = SoundCategory.RECORDS;

    public static void playLevelUpSound(Player player) {
        AlathraSkills instance = AlathraSkills.getInstance();

        // Amethyst resonate
        player.playSound(player.getLocation(), Sound.BLOCK_AMETHYST_BLOCK_RESONATE, SOUND_CATEGORY, 3.0f, 1.0f);
        // Level-up jingle
        Bukkit.getScheduler().runTaskLater(instance,
            //G13
            () -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, SOUND_CATEGORY, 1.0f, 1.059463f), 8L
        );
        Bukkit.getScheduler().runTaskLater(instance,
            //A15
            () -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, SOUND_CATEGORY, 1.0f, 1.189207f), 16L
        );
        Bukkit.getScheduler().runTaskLater(instance,
            //B17
            () -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, SOUND_CATEGORY, 1.0f, 1.334840f), 20L
        );
        Bukkit.getScheduler().runTaskLater(instance,
            //A15
            () -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, SOUND_CATEGORY, 1.0f, 1.189207f), 28L
        );
        Bukkit.getScheduler().runTaskLater(instance,
            //G13
            () -> player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, SOUND_CATEGORY, 1.0f, 1.059463f), 32L
        );
        Bukkit.getScheduler().runTaskLater(instance,
            //C18
            () ->
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, SOUND_CATEGORY, 2.0f, 1.414214f), 40L
        );
        Bukkit.getScheduler().runTaskLater(instance,
            //D20
            () ->
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, SOUND_CATEGORY, 2.0f, 1.587401f), 48L
        );
        Bukkit.getScheduler().runTaskLater(instance,
            //E22
            () ->
                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_BASS, SOUND_CATEGORY, 2.0f, 1.781797f), 56L
        );

    }
}