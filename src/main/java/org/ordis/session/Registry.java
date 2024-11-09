package org.ordis.session;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.SlashCommandData;

import javax.annotation.Nonnull;
import java.util.*;
import java.util.function.Consumer;

public class Registry {
    private final @Nonnull Set<SlashCommandData> commandData = new HashSet<>();

    private final @Nonnull Map<String, Consumer<SlashCommandInteractionEvent>> slashInteractions = new HashMap<>();

    private final @Nonnull Map<String, Consumer<ButtonInteractionEvent>> buttonInteractions = new HashMap<>();

    @Nonnull
    public Set<SlashCommandData> getCommandData() {
        return commandData;
    }

    @Nonnull
    public Map<String, Consumer<SlashCommandInteractionEvent>> getSlashInteractions() {
        return slashInteractions;
    }

    @Nonnull
    public Map<String, Consumer<ButtonInteractionEvent>> getButtonInteractions() {
        return buttonInteractions;
    }
}
