package net.jda.plugin;

import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;

import org.python.util.PythonInterpreter;

import net.dv8tion.jda.api.entities.Channel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.jda.Util;

public class PlugIn {
    private PythonInterpreter interpreter = new PythonInterpreter();
    private Set<CommandData> datas = new HashSet<>();
    private ResourceBundle bundle;
    private String name;

    private final Util util = new Util();

    public PlugIn(String name) {
        this.name = name;
        util.setPlugIn(this);
    }

    final void call(SlashCommandInteractionEvent event) {
        User user = event.getUser();
        util.setUser(user);

        Channel channel = event.getChannel();
        util.setChannel(channel);

        StringBuilder args = new StringBuilder();
        for (OptionMapping option : event.getOptions()) {
            args.append("(\"" + option.getName() + "\",\"" + option.getAsString() + "\"),");
        }
        if (!args.isEmpty())
            args.deleteCharAt(args.length() - 1);

        String key = event.getCommandPath().replace('/', '_');
        String response = getInterpreter()
                .eval(key + "(\"" + channel.getId() + "\",\"" + user.getId() + "\",[" + args + "])")
                .asString();
        event.reply("" + response).queue();
    }

    public PythonInterpreter getInterpreter() {
        return interpreter;
    }

    public Set<CommandData> getCommandData() {
        return datas;
    }

    public void setResourceBundle(ResourceBundle bundle) {
        this.bundle = bundle;
    }

    public ResourceBundle getResourceBundle() {
        return bundle;
    }

    public String getName() {
        return name;
    }
}
