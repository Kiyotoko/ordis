package net.jda.plugin;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.logging.Logger;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

import net.dv8tion.jda.internal.interactions.CommandDataImpl;

public class PlugInLoader {
	private static final Logger logger = Logger.getLogger(PlugInLoader.class.getName());
	public static final Map<String, PlugIn> plugins = new HashMap<>();

	Map<String, File> files = new HashMap<>();

	public PlugInLoader(File parent) {
		assert parent.isDirectory() && parent.exists();
		for (File file : parent.listFiles()) {
			files.put(file.getName(), file);
		}
		plugins.put(parent.getName(), plugin);
	}

	PlugIn plugin = new PlugIn();

	private void script() {
		plugin.interpreter.execfile(files.get("main.py").getAbsolutePath());
	}

	private void data() {
		Gson gson = new Gson();
		try {
			@SuppressWarnings("resource")
			JsonArray array = JsonParser
					.parseString(new String(new FileInputStream(files.get("data.json")).readAllBytes()))
					.getAsJsonArray();
			for (JsonElement element : array) {
				plugin.datas.add(gson.fromJson(element, CommandDataImpl.class));
			}
		} catch (JsonSyntaxException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	private void language() {
		try {
			plugin.bundle = new PropertyResourceBundle(
					new FileInputStream(files.get(Locale.getDefault() + ".properties")));
		} catch (FileNotFoundException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		} catch (IOException e) {
			logger.severe(e.getMessage());
			e.printStackTrace();
		}
	}

	public PlugIn build() {
		language();
		data();
		script();
		return plugin;
	}
}
