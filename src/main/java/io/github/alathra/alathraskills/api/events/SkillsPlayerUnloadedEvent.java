package io.github.alathra.alathraskills.api.events;

import io.github.alathra.alathraskills.api.SkillsPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

import java.util.UUID;

public class SkillsPlayerUnloadedEvent extends Event {

    private static final HandlerList HANDLERS = new HandlerList();
    private UUID uuid;
    private SkillsPlayer skillsPlayer;

    public SkillsPlayerUnloadedEvent(UUID uuid, SkillsPlayer skillsPlayer) {
        this.uuid = uuid;
        this.skillsPlayer = skillsPlayer;
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

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
