package io.github.alathra.alathraskills.api.events;

import io.github.alathra.alathraskills.api.SkillsPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SkillsPlayerLoadedEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private final UUID uuid;
    private final SkillsPlayer skillsPlayer;

    public SkillsPlayerLoadedEvent(UUID uuid, SkillsPlayer skillsPlayer) {
        this.uuid = uuid;
        this.skillsPlayer = skillsPlayer;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public UUID getUuid() {
        return uuid;
    }

    public SkillsPlayer getSkillsPlayer() {
        return skillsPlayer;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
