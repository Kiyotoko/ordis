package net.jda.application;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Locale;
import java.util.logging.Level;
import java.util.logging.Logger;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.DisconnectEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class Application {
	private static final Logger logger = Logger.getLogger(Application.class.getName());

	static {
		logger.setLevel(Level.ALL);
		Locale.setDefault(new Locale.Builder().setLanguage("de").setRegion("DE").build());
	}

	public static void launch(Class<? extends Application> clazz, String token) {
		try {
			Constructor<? extends Application> constractor = clazz.getConstructor();
			Application application = constractor.newInstance();

			try {
				application.init();
			} catch (Exception e) {
				logger.throwing(Application.class.getName(), "init", e);
			}
			try {
				application.start(JDABuilder.createDefault(token));
			} catch (Exception e) {
				logger.throwing(Application.class.getName(), "start", e);
			}
		} catch (InstantiationException e) {
			logger.throwing(Application.class.getName(), "launch", e);
		} catch (IllegalAccessException e) {
			logger.throwing(Application.class.getName(), "launch", e);
		} catch (IllegalArgumentException e) {
			logger.throwing(Application.class.getName(), "launch", e);
		} catch (InvocationTargetException e) {
			logger.throwing(Application.class.getName(), "launch", e);
		} catch (NoSuchMethodException e) {
			logger.throwing(Application.class.getName(), "launch", e);
		} catch (SecurityException e) {
			logger.throwing(Application.class.getName(), "launch", e);
		}
	}

	protected Application() {

	}

	protected void init() throws Exception {

	}

	protected abstract void start(JDABuilder builder) throws Exception;

	protected abstract class ServerListener extends ListenerAdapter {
		@Override
		public abstract void onMessageReceived(MessageReceivedEvent event);

		@Override
		public abstract void onSlashCommandInteraction(SlashCommandInteractionEvent event);

		@Override
		public abstract void onMessageReactionAdd(MessageReactionAddEvent event);

		@Override
		public abstract void onDisconnect(DisconnectEvent event);
	}
}
