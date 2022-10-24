package net.jda;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import net.dv8tion.jda.api.entities.User;
import net.jda.plugin.PlugIn;

public final class Util {
    private static final Map<String, PlugIn> PLUGINS = new HashMap<>();

    public void setPlugIn(PlugIn plugin) {
        PLUGINS.put(plugin.getName(), plugin);
    }

    public static PlugIn getPlugIn(String key) {
        return PLUGINS.get(key);
    }

    public static Set<String> getPlugInNames() {
        return PLUGINS.keySet();
    }

    private static final Map<String, User> USERS = new HashMap<>();

    public static User getUser(String key) {
        return USERS.get(key);
    }

    public String setUser(User user) {
        String tag = user.getAsTag();
        if (!USERS.containsKey(tag)) {
            USERS.put(tag, user);
        }
        return tag;
    }

    public static void sendPrivateMessage(String user, String response) {
        USERS.get(user).openPrivateChannel().queue(c -> c.sendMessage("" + response).queue());
    }

    public static Set<String> getUserTags() {
        return USERS.keySet();
    }
}
