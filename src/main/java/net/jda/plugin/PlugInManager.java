package net.jda.plugin;

import java.io.File;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class PlugInManager {
	Map<String, PlugIn> directs = new HashMap<>();

	Set<PlugIn> plugins = new HashSet<>();

	public PlugInManager(File parent) {
		assert parent.isDirectory() && parent.exists();
		for (File folder : parent.listFiles((f, n) -> f.isDirectory())) {
			PlugIn plugin = new PlugInLoader(folder).build();
			for (CommandData data : plugin.datas) {
				directs.put(data.getName(), plugin);
			}
			plugins.add(plugin);
		}
	}

	public final void redirect(SlashCommandInteractionEvent event) {
		String key = event.getName();

		PlugIn plugin = directs.get(key);
		if (plugin == null) {
			event.reply("Command not found!").queue();
		} else {
			plugin.call(event);
		}
	}

	public Set<PlugIn> getPlugins() {
		return plugins;
	}
}
