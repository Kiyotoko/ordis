package com.ordis.base;

import java.util.LinkedList;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.errorprone.annotations.ForOverride;

import net.dv8tion.jda.api.MessageBuilder;

public class Phase implements Recurring {
    @Nonnull
    private final LinkedList<@Nonnull Recurring> recurring = new LinkedList<>();

    @Nullable
    private MessageBuilder prolog;
    @Nullable
    private MessageBuilder epilog;

    public Phase(@Nullable MessageBuilder prolog, @Nullable MessageBuilder epilog) {
        setProlog(prolog);
        setEpilog(epilog);
    }

    public Phase() {
        this(new MessageBuilder(), new MessageBuilder());
    }

    @Override
    public boolean finished() {
        for (Recurring value : recurring) {
            if (!value.finished())
                return false;
        }
        return true;
    }

    @ForOverride
    @Override
    public void start() {

    }

    @ForOverride
    @Override
    public void end() {

    }

    @Nonnull
    public LinkedList<@Nonnull Recurring> getRecurring() {
        return recurring;
    }

    public boolean hasProlog() {
        final MessageBuilder prolog = getProlog();
        if (prolog == null)
            return false;
        return prolog.isEmpty();
    }

    @Nullable
    public MessageBuilder getProlog() {
        return prolog;
    }

    public void setProlog(@Nullable MessageBuilder prolog) {
        this.prolog = prolog;
    }

    public boolean hasEpilog() {
        final MessageBuilder epilog = getEpilog();
        if (epilog == null)
            return false;
        return epilog.isEmpty();
    }

    @Nullable
    public MessageBuilder getEpilog() {
        return epilog;
    }

    public void setEpilog(@Nullable MessageBuilder epilog) {
        this.epilog = epilog;
    }
}
