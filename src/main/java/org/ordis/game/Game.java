package org.ordis.game;

import javax.annotation.Nonnull;
import java.util.*;

public class Game {
    private final @Nonnull Map<String, Player> players = new HashMap<>();

    private final @Nonnull List<Player> targets = new ArrayList<>();

    @Nonnull
    public Map<String, Player> getPlayers() {
        return players;
    }
}
