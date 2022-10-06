package net.jda.plugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Logger;

import org.python.core.PyObject;
import org.python.util.PythonInterpreter;

import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;

public class PlugIn {
	public static final Logger logger = Logger.getLogger(PlugIn.class.getName());
	public static final Map<String, User> users = new HashMap<>();

	public void onPrivateMessage(String user, String response) {
		users.get(user).openPrivateChannel().queue(c -> c.sendMessage(response).queue());
	}

	public String getOfLang(String key) {
		return bundle.getString(key);
	}

	Collection<CommandData> datas = new ArrayList<>();

	PythonInterpreter interpreter = new PythonInterpreter();

	ResourceBundle bundle;

	final void call(SlashCommandInteractionEvent event) {
		String tag = event.getUser().getAsTag();
		User user = event.getUser();
		if (!users.containsKey(tag)) {
			users.put(tag, user);
		}

		StringBuilder args = new StringBuilder();
		for (OptionMapping option : event.getOptions()) {
			args.append("(\"" + option.getName() + "\",\"" + option.getAsString() + "\"),");
		}
		if (!args.isEmpty())
			args.deleteCharAt(args.length() - 1);

		String key = event.getCommandPath().replace('/', '_');
		PyObject response = interpreter.eval(key + "(\"" + event.getUser().getAsTag() + "\",[" + args + "])");
		event.reply(response.asString()).queue();
	}

	public Collection<CommandData> getData() {
		return datas;
	}
}
