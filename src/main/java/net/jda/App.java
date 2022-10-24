package net.jda;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.Properties;

import com.google.gson.Gson;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Activity.ActivityType;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.jda.application.Application;
import net.jda.plugin.PlugIn;
import net.jda.plugin.PlugInManager;

public class App extends Application {
    private Collection<CommandData> data = new ArrayList<>();
    private PlugInManager manager;
    private Properties properties;

    @Override
    protected void init() throws Exception {
        File config = new File("server.properties");
        if (!config.exists()) {
            config.createNewFile();
            try (FileWriter writer = new FileWriter(config)) {
                writer.append("activity.type=WATCHING\n").append("activity.name=Plug In Manager\n")
                        .append("locale.tag=en-GB\n").flush();
            }
        }
        properties = new Properties();
        properties.load(new FileInputStream(config.getName()));
        Locale.setDefault(Locale.forLanguageTag(properties.getProperty("locale.tag")));

        manager = new PlugInManager(new File("plugins"));
        for (PlugIn plugin : manager.getPlugins()) {
            data.addAll(plugin.getCommandData());
        }
    }

    @Override
    protected void start(JDABuilder builder) throws Exception {
        JDA api = builder.addEventListeners(new ServerListener() {
            @Override
            public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
                manager.redirect(event);
            }
        }).setActivity(Activity.of(new Gson().fromJson(properties.getProperty("activity.type"), ActivityType.class),
                properties.getProperty("activity.name"))).build();
        api.updateCommands().addCommands(data).queue();
        api.awaitReady();

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            api.cancelRequests();
            System.err.println("runtime shutdown, cancel api requests");
        }));
    }
}
