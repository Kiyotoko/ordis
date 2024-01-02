package org.ordis;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import javax.annotation.Nonnull;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import java.util.Map;
import java.util.function.Consumer;

public class OrdisRegistry {
    private final @Nonnull Set<SlashCommandData> commandData = new HashSet<>();
    private final @Nonnull Map<String, Consumer<SlashCommandInteractionEvent>> interactions = new HashMap<>();

    @Nonnull
    public Set<SlashCommandData> getCommandData() {
        return commandData;
    }

    @Nonnull
    public Map<String, Consumer<SlashCommandInteractionEvent>> getInteractions() {
        return interactions;
    }
}
