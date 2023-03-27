package com.ordis.base;

import java.util.LinkedList;

import javax.annotation.Nonnull;

public class Cycle {
    @Nonnull
    private final LinkedList<Recurring> recurring = new LinkedList<>();

    private final Property<Boolean> ended = new Property<>(Boolean.class, false);

    public void rotate() {
        if (hasRecurring()) {
            getRecurring().getLast().end();
            getRecurring().addLast(getRecurring().remove());
            getRecurring().getFirst().start();
        }
    }

    public boolean hasRecurring() {
        return !getRecurring().isEmpty();
    }

    @Nonnull
    public LinkedList<Recurring> getRecurring() {
        return recurring;
    }

    public Property<Boolean> getEnded() {
        return ended;
    }
}
