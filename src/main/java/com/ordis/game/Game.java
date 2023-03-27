package com.ordis.game;

import java.util.HashSet;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import com.ordis.base.Cycle;
import com.ordis.base.Recurring;

import net.dv8tion.jda.api.entities.PrivateChannel;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;

public abstract class Game implements Recurring {
    private final HashSet<Player> players = new HashSet<>();

    private final HashSet<TextChannel> openChannels = new HashSet<>();

    private final Cycle cycle = new Cycle();

    public Game(TextChannel channel, @Nonnull User... users) {
        openChannels.add(channel);
        for (User user : users)
            if (user != null)
                players.add(new Player(user));
    }

    public void forAllOpenChannels(Consumer<TextChannel> consumer) {
        getOpenChannels().forEach(consumer);
    }

    public void forAllPrivateChannels(Consumer<PrivateChannel> consumer) {
        getPlayers().forEach(p -> p.getUser().openPrivateChannel().queue(consumer));
    }

    public HashSet<Player> getPlayers() {
        return players;
    }

    public HashSet<TextChannel> getOpenChannels() {
        return openChannels;
    }

    public Cycle getCycle() {
        return cycle;
    }

    public static class TikTakToe extends Game {
        private char[] field = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8' };

        public TikTakToe(TextChannel channel, User user) {
            super(channel, user);
        }

        @Override
        public boolean finished() {
            return false;
        }

        @SuppressWarnings("null")
        @Nonnull
        String getField() {
            final StringBuilder builder = new StringBuilder();
            builder.append("```");
            for (int i = 0; i < field.length; i++) {
                builder.append(i % 3 == 0 ? "\n" : " | ");
                builder.append(field[i]);
            }
            builder.append("```");
            return builder.toString();
        }

        @Override
        public void start() {
            forAllOpenChannels(c -> {
                c.sendMessage(getField()).queue();
            });
        }

        @Override
        public void end() {

        }
    }
}
