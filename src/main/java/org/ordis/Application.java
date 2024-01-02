package org.ordis;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.SubcommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.ordis.game.Game;
import org.ordis.game.Player;

import javax.annotation.Nonnull;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Application {

    private final @Nonnull OrdisRegistry registry = new OrdisRegistry();

    private final @Nonnull Map<String, Game> games = new HashMap<>();

    private final @Nonnull Map<String, Player> players = new HashMap<>();

    public void setup() {
        registry.getCommandData().addAll(List.of(new CommandDataImpl("choose", "Choose a player."),
                new CommandDataImpl("use", "Use an option."), new CommandDataImpl("help", "Get help."),
                new CommandDataImpl("session", "Manage your session.").addSubcommands(
                        new SubcommandData("join", "Join a session."),
                        new SubcommandData("leave", "Leave your session."),
                        new SubcommandData("create", "Create a new session."))));
        registry.getInteractions().putAll(Map.of("/help", event -> event.replyEmbeds(new EmbedBuilder().setDescription("Check out the wiki for help!").setUrl("https://github.com/Kiyotoko/ordis").setColor(
                Color.GREEN).build()).queue(), "/session join", event -> event.replyEmbeds(new EmbedBuilder().setDescription("ERROR: Unimplemented").setColor(Color.RED).build()).queue()));
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
            var handler = registry.getInteractions().get(event.getCommandString());
            System.out.println(event.getCommandString());
            if (handler != null) {
                handler.accept(event);
            } else {
                event.replyEmbeds(new EmbedBuilder().setDescription("ERROR: cmd not found").setColor(Color.RED).build()).queue();
            }
        }
    }
}
