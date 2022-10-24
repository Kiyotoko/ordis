package net.jda.application;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public abstract class Application {
    public static void launch(Class<? extends Application> clazz, String token) {
        try {
            Constructor<? extends Application> constractor = clazz.getConstructor();
            Application application = constractor.newInstance();

            try {
                application.init();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                application.start(JDABuilder.createDefault(token));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (SecurityException e) {
            e.printStackTrace();
        }
    }

    protected Application() {

    }

    protected void init() throws Exception {

    }

    protected abstract void start(JDABuilder builder) throws Exception;

    protected abstract class ServerListener extends ListenerAdapter {
        @Override
        public abstract void onSlashCommandInteraction(SlashCommandInteractionEvent event);
    }
}
