package org.ordis;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.jetbrains.annotations.NotNull;
import org.ordis.session.Registry;
import org.ordis.session.Session;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.List;
import java.util.Map;

public class Application {

    private final @Nonnull Registry registry = new Registry();

    public void setup() {
        registry.getCommandData().addAll(List.of(new CommandDataImpl("help", "Get help."),
                new CommandDataImpl("host", "Host a game.")));
        registry.getSlashInteractions().putAll(Map.of(
                "/help", event -> event.replyEmbeds(new EmbedBuilder().setDescription("Check out the wiki for help!").setUrl("https://github.com/Kiyotoko/ordis").setColor(
                Color.GREEN).build()).queue(),
                "/host", event1 -> new Session(registry, event1)));
    }

    public void start(String token) throws InterruptedException {
        setup();
        JDA jda = JDABuilder.createDefault(token).addEventListeners(new OrdisListenerAdapter()).build();
        jda.updateCommands().addCommands(registry.getCommandData()).queue();
        jda.awaitReady();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            jda.cancelRequests();
            jda.shutdown();
        }));
    }

    protected class OrdisListenerAdapter extends ListenerAdapter {

        @Override
        public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
            var handler = registry.getSlashInteractions().get(event.getCommandString());
            if (handler != null) {
                handler.accept(event);
            } else {
                event.replyEmbeds(new EmbedBuilder().setDescription("Command not found.").setColor(Color.RED).build()).queue();
            }
        }

        @Override
        public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
            var handler = registry.getButtonInteractions().get(event.getButton().getId());
            if (handler != null) {
                handler.accept(event);
            } else {
                event.editButton(event.getButton().asDisabled()).queue();
            }
        }
    }
}
