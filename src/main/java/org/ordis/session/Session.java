package org.ordis.session;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.InteractionHook;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import org.ordis.game.Game;

import java.util.*;

public class Session {
    private final Set<User> users = new HashSet<>();
    private final User host;
    private final Game game = new Game();

    private InteractionHook hook;

    public Session(Registry registry, SlashCommandInteractionEvent event) {
        this.host = event.getUser();
        users.add(event.getUser());

        int hash = event.hashCode();
        event.replyEmbeds(new EmbedBuilder().setTitle("Session hosted!").setAuthor(host.getName(), null, host.getAvatarUrl()).setDescription("You can now join and leave the session. When the host presses start, the game will start and this field will be closed.").build())
        .setActionRow(
            Button.primary("join_" + hash, "Join"),
            Button.primary("leave_" + hash, "Leave"),
            Button.primary("start_" + hash, "Start")
        ).queue(success -> hook = success);

        registry.getButtonInteractions().putAll(Map.of("join_" + hash, e -> {
            boolean joined = getUsers().add(e.getUser());
            if (joined) e.reply(e.getUser().getName() + " joined the game!").queue();
            else e.reply(e.getUser().getName() + " can not join, as this user has already joined.").queue();
        }, "leave_" + hash, e -> {
            boolean left = getUsers().remove(e.getUser());
            if (left) {
                if (e.getUser().equals(host)) {
                    e.reply("Session will close as the host has left.").queue();
                    hook.editOriginalEmbeds(new EmbedBuilder().setTitle("Session destroyed!").setAuthor(host.getName(), null, host.getAvatarUrl()).build()).queue();
                    registry.getButtonInteractions().remove("join_" + hash);
                    registry.getButtonInteractions().remove("leave_" + hash);
                    registry.getButtonInteractions().remove("start_" + hash);
                } else
                    e.reply(e.getUser().getName() + " left the game!").queue();
            }
            else e.reply(e.getUser().getName() + " can not leave, as this user has already left.").queue();
        }, "start_" + hash, e -> {
            e.reply("The game starts now!").queue();
            hook.editOriginalEmbeds(new EmbedBuilder().setTitle("Game started!").setAuthor(host.getName(), null, host.getAvatarUrl()).build()).queue();
            registry.getButtonInteractions().remove("join_" + hash);
            registry.getButtonInteractions().remove("leave_" + hash);
            registry.getButtonInteractions().remove("start_" + hash);
        }));
    }

    public Set<User> getUsers() {
        return users;
    }
}
