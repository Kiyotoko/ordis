package org.ordis.base;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public abstract class CommandAdapter {

    private static final Logger logger = LoggerFactory.getLogger(CommandAdapter.class);

    private final @Nonnull Map<String, Method> actions = new HashMap<>();

    private final @Nonnull Set<CommandData> data = new HashSet<>();

    protected CommandAdapter() {
        for (Method method : getClass().getMethods()) {
            if (method.isAnnotationPresent(Command.class) ) {
                Command command = method.getAnnotation(Command.class);
                actions.put(command.name(), method);
                data.add(new CommandDataImpl(command.name(), command.description()));
            }
        }
        logger.info("Loaded {} commands.", actions.size());
    }

    public void call(@Nonnull SlashCommandInteractionEvent event) {
        Method method = getActions().get(event.getName());
        if (method != null) {
            try {
                method.invoke(this, event);
            } catch (IllegalAccessException | InvocationTargetException ex) {
                logger.error("Can not invoke command", ex);
            }
        }
    }

    @SuppressWarnings("unused")
    public abstract void help(SlashCommandInteractionEvent event);

    @Nonnull
    public Map<String, Method> getActions() {
        return actions;
    }

    @Nonnull
    public Set<CommandData> getData() {
        return data;
    }
}
