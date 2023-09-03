package org.ordis;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.ordis.base.*;

public class Bot {

	public void start(String token) throws InterruptedException {
		JDA jda = JDABuilder.createDefault(token).addEventListeners(new OrdisListenerAdapter()).build();
		jda.updateCommands().addCommands(adapter.getData()).queue();
		jda.awaitReady();

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			jda.cancelRequests();
			jda.shutdown();
		}));
	}

	private final @Nonnull OrdisCommandAdapter adapter = new OrdisCommandAdapter();

	protected class OrdisListenerAdapter extends ListenerAdapter {
		@Override
		public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
			OrdisDB.register(event.getUser());
			adapter.call(event);
			OrdisDB.select();
		}
	}

	protected static class OrdisCommandAdapter extends CommandAdapter {

		@Command(name = "help", description = "Gives help.")
		@Override
		public void help(SlashCommandInteractionEvent event) {
			event.reply("No help available.").queue();
		}
	}
}
