package com.ordis.game;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Nonnull;

import com.ordis.base.Property;

import net.dv8tion.jda.api.entities.User;

public class Player {
    private final Map<String, Property<?>> stats = new HashMap<>();

    @Nonnull
    private final User user;

    public Player(@Nonnull User user) {
        this.user = user;
    }

    public boolean hasStats() {
        return !stats.isEmpty();
    }

    public boolean hasStat(String stat) {
        return stats.containsKey(stat);
    }

    public void setStat(String stat, @Nonnull Object value) {
        System.out.println(stats.get(stat).setUncheckedValue(value));
    }

    public Object getStat(String stat) {
        return stats.get(stat).getValue();
    }

    public Property<?> getProperty(String stat) {
        return stats.get(stat);
    }

    public Map<String, Property<?>> getStats() {
        return stats;
    }

    public User getUser() {
        return user;
    }
}
