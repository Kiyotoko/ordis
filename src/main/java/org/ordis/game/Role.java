package org.ordis.game;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

import java.util.function.Predicate;

public class Role {
    private final @Nonnull Faction faction;
    private final @Nonnull Awakening awakening;
    private final @Nonnull Alignment alignment;

    public Role(@Nonnull Faction faction, @Nonnull Awakening awakening, @Nonnull Alignment alignment) {
        this.faction = faction;
        this.awakening = awakening;
        this.alignment = alignment;
    }

    @Nonnull
    public Faction getFaction() {
        return faction;
    }

    @Nonnull
    public Awakening getAwakening() {
        return awakening;
    }

    @Nonnull
    public Alignment getAlignment() {
        return alignment;
    }


    private @Nullable Consumer<Player> onStartup;

    private @Nullable Consumer<Player> onMorning;

    private @Nullable Consumer<Player> onEvening;

    private @Nullable Consumer<Player> onPreMidnight;

    private @Nullable Consumer<Player> onMidnight;

    private @Nullable Consumer<Player> onPostMidnight;

    private @Nullable Predicate<Player> canVote;

    private @Nullable Predicate<Player> canActivate;

    private @Nullable BiConsumer<Player, Integer> ability;

    public void setOnStartup(@Nullable Consumer<Player> onStartup) {
        this.onStartup = onStartup;
    }

    @Nullable
    public Consumer<Player> getOnStartup() {
        return onStartup;
    }

    public void setOnMorning(@Nullable Consumer<Player> onMorning) {
        this.onMorning = onMorning;
    }

    @Nullable
    public Consumer<Player> getOnMorning() {
        return onMorning;
    }

    public void setOnEvening(@Nullable Consumer<Player> onEvening) {
        this.onEvening = onEvening;
    }

    @Nullable
    public Consumer<Player> getOnEvening() {
        return onEvening;
    }

    public void setOnPreMidnight(@Nullable Consumer<Player> onPreMidnight) {
        this.onPreMidnight = onPreMidnight;
    }

    @Nullable
    public Consumer<Player> getOnPreMidnight() {
        return onPreMidnight;
    }

    public void setOnMidnight(@Nullable Consumer<Player> onMidnight) {
        this.onMidnight = onMidnight;
    }

    @Nullable
    public Consumer<Player> getOnMidnight() {
        return onMidnight;
    }

    public void setOnPostMidnight(@Nullable Consumer<Player> onPostMidnight) {
        this.onPostMidnight = onPostMidnight;
    }

    @Nullable
    public Consumer<Player> getOnPostMidnight() {
        return onPostMidnight;
    }
}
