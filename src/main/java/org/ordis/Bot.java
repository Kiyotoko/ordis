package org.ordis;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;


public class Bot {

	public void start(String token) throws InterruptedException {
		JDA jda = JDABuilder.createDefault(token).addEventListeners(new OrdisListenerAdapter()).build();
		jda.awaitReady();

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			jda.cancelRequests();
			jda.shutdown();
		}));
	}

	protected static class OrdisListenerAdapter extends ListenerAdapter {
		@Override
		public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
			event.reply("You have used a slash command!").queue();
		}
	}
}
