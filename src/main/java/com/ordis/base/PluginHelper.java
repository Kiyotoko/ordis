package com.ordis.base;

import java.util.function.Consumer;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class PluginHelper {
	private final CommandData commandData;
	private final Consumer<SlashCommandInteractionEvent> consumer;

	public PluginHelper(CommandData commandData, Consumer<SlashCommandInteractionEvent> consumer) {
		this.commandData = commandData;
		this.consumer = consumer;
	}

	public CommandData getCommandData() {
		return commandData;
	}

	public Consumer<SlashCommandInteractionEvent> getConsumer() {
		return consumer;
	}
}
