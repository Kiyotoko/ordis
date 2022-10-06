package net.jda;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.jda.application.Application;
import net.jda.plugin.PlugIn;
import net.jda.plugin.PlugInManager;

public class App extends Application {
	private final Collection<CommandData> data = new ArrayList<>();

	private final PlugInManager manager = new PlugInManager(new File("plugins"));

	@Override
	protected void init() throws Exception {
		for (PlugIn plugin : manager.getPlugins()) {
			data.addAll(plugin.getData());
		}
	}

	@Override
	protected void start(JDABuilder builder) throws Exception {
		JDA api = builder.addEventListeners(new ServerListener() {
			@Override
			public void onMessageReceived(MessageReceivedEvent event) {

			}

			@Override
			public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
				manager.redirect(event);
			}

			@Override
			public void onMessageReactionAdd(MessageReactionAddEvent event) {

			}

			@Override
			public void onDisconnect(DisconnectEvent event) {

			}
		}).setActivity(Activity.competing("PlugIns")).build();
		api.updateCommands().addCommands(data).queue();
		api.awaitReady();

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			api.cancelRequests();
			System.err.println("runtime shutdown, cancel api requests");
		}));
	}
}
