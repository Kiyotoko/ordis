package com.ordis;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import javax.annotation.Nonnull;

import com.ordis.base.Issue;
import com.ordis.base.IssueState;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.MessageBuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

public class Bot {
	private final Map<CommandData, Consumer<SlashCommandInteractionEvent>> data = Map.of(
			// help
			new CommandDataImpl("help", "gives help"), (SlashCommandInteractionEvent event) -> {
				event.reply("Why should I help you? Don't see a reason.").queue();
			},

			// privacy
			new CommandDataImpl("privacy", "returns importent privacy information"),
			(SlashCommandInteractionEvent event) -> {
				event.reply("Privacy? Sounds like an interesting concept for me.").queue();
			}//
	);

	private final Map<String, Issue> issues = new HashMap<>();

	protected void start(String token) throws Exception {
		JDA jda = JDABuilder.createDefault(token).addEventListeners(new OrdisListenerAdapter())
				.setActivity(Activity.watching("2001")).build();
		jda.updateCommands().addCommands(data.keySet()).queue();
		jda.awaitReady();

		Runtime.getRuntime().addShutdownHook(new Thread(() -> {
			jda.cancelRequests();
			jda.shutdown();
		}));
	}

	protected class OrdisListenerAdapter extends ListenerAdapter {
		@Override
		public void onSlashCommandInteraction(@Nonnull SlashCommandInteractionEvent event) {
			for (Map.Entry<CommandData, Consumer<SlashCommandInteractionEvent>> entry : data.entrySet()) {
				if (entry.getKey().getName().contains(event.getName())) {
					entry.getValue().accept(event);
					return;
				}
			}
		}

		@Override
		public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
			if (event.getAuthor().isBot())
				return;
			Issue issue = new Issue(Bot.this, event.getTextChannel(), new MessageBuilder(
					"That could mean everything! React now to say: yeah Ordis, you are the best human exploiter!"),
					IssueState.OPEN) {
				public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
					System.out.println(event.getReaction());
					if (isValid()) {
						getBuilder().setContent("At least *SOMEONE* is proud of me!");
						setState(IssueState.CLOSED);
						edit(getBuilder().build());
						end();
					}
				};
			};
			issue.start();
		}

		@Override
		public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {
			Issue issue = issues.get(event.getMessageId());
			if (issue != null) {
				issue.onMessageReactionAdd(event);
			}
		}
	}

	public Map<String, Issue> getIssues() {
		return issues;
	}
}
