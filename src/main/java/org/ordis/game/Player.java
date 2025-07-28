package org.ordis.game;

import net.dv8tion.jda.api.entities.User;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.OverridingMethodsMustInvokeSuper;
import java.util.ArrayList;
import java.util.List;

public class Player {

    private final @Nonnull User user;
    private @Nullable Role role;
    private @Nullable Game game;

    private final @Nonnull List<String> items = new ArrayList<>();

    public Player(@Nonnull User user) {
        this.user = user;
    }

    @Nonnull
    public User getUser() {
        return user;
    }

    @Nullable
    public Game getGame() {
        return game;
    }

    public void setGame(@Nullable Game game) {
        this.game = game;
    }

    @Nullable
    public Role getRole() {
        return role;
    }

    public void setRole(@Nonnull Role role) {
        this.role = role;
    }

    @Nonnull
    public List<String> getItems() {
        return items;
    }

    private boolean voting;

    public boolean isVoting() {
        return voting;
    }

    public void setVoting(boolean voting) {
        this.voting = voting;
    }

    private @Nullable Player target;

    @OverridingMethodsMustInvokeSuper
    public void setTarget(@Nonnull Player target) {
        this.target = target;
    }

    @Nullable
    public Player getTarget() {
        return target;
    }

    private int action;

    @OverridingMethodsMustInvokeSuper
    public void setAction(int action) {
        this.action = action;
    }

    public int getAction() {
        return action;
    }
}
