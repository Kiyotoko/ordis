package com.ordis.game;

import javax.annotation.Nonnull;

import com.ordis.base.Phase;

import net.dv8tion.jda.api.MessageBuilder;

public class Turn extends Phase {
    @Nonnull
    private final Game game;

    public Turn(@Nonnull Game game) {
        this.game = game;
    }

    @Override
    public void start() {
        if (hasProlog())
            game.forAllOpenChannels(c -> {
                MessageBuilder builder = getProlog();
                if (builder != null)
                    c.sendMessage(builder.build());
            });
    }

    @Override
    public void end() {
        if (hasEpilog())
            game.forAllOpenChannels(c -> {
                MessageBuilder builder = getEpilog();
                if (builder != null)
                    c.sendMessage(builder.build());
            });
    }

    @Nonnull
    public Game getGame() {
        return game;
    }
}
