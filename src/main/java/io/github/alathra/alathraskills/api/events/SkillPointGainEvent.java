package io.github.alathra.alathraskills.api.events;

import io.github.alathra.alathraskills.api.SkillsPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

public class SkillPointGainEvent extends Event {
    private static final HandlerList HANDLERS = new HandlerList();
    private final SkillsPlayer skillsPlayer;

    public SkillPointGainEvent(SkillsPlayer skillsPlayer) {
        this.skillsPlayer = skillsPlayer;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

    public SkillsPlayer getSkillsPlayer() {
        return skillsPlayer;
    }

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS;
    }
}
