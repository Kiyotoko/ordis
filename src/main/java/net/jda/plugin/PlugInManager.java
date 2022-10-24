package net.jda.plugin;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class PlugInManager {
    Map<String, PlugIn> directs = new HashMap<>();

    Set<PlugIn> plugins = new HashSet<>();

    public PlugInManager(File folder) throws ZipException, IOException {
        assert folder.isDirectory() && folder.exists();
        for (File zip : folder.listFiles((f, n) -> n.endsWith(".zip"))) {
            PlugIn plugin = PlugInBuilder.newBuilder(new ZipFile(zip)).addCommandData().addResourceBundle().addScript()
                    .build();
            for (CommandData data : plugin.getCommandData()) {
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
