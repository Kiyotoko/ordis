package net.jda.plugin;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Locale;
import java.util.PropertyResourceBundle;
import java.util.ResourceBundle;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;

import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.internal.interactions.CommandDataImpl;

public class PlugInBuilder {
    static PlugInBuilder newBuilder(ZipFile source) {
        return new PlugInBuilder(source);
    }

    private ZipFile source;
    private PlugIn output;

    private PlugInBuilder(ZipFile source) {
        this.source = source;
        String name = source.getName();
        this.output = new PlugIn(name.substring(name.indexOf("\\") + 1, name.lastIndexOf(".")));
    }

    public PlugInBuilder addScript(InputStream file) {
        assert output.getResourceBundle() != null;
        output.getInterpreter().execfile(file);
        return this;
    }

    public PlugInBuilder addScript() throws IOException {
        assert source != null;
        try (InputStream stream = source.getInputStream(getFileEntry("script.py"))) {
            addScript(stream);
        }
        return this;
    }

    public PlugInBuilder addCommandData(Collection<CommandData> datas) {
        output.getCommandData().addAll(datas);
        return this;
    }

    public PlugInBuilder addCommandData() throws IOException {
        assert source != null;
        try (InputStream stream = source
                .getInputStream(getFileEntry("index.json"))) {
            ArrayList<CommandData> list = new ArrayList<>();
            for (JsonElement element : JsonParser
                    .parseString(new String(stream.readAllBytes()))
                    .getAsJsonArray()) {
                list.add(new Gson().fromJson(element, CommandDataImpl.class));
            }
            addCommandData(list);
        }
        return this;
    }

    public PlugInBuilder addResourceBundle(ResourceBundle bundle) {
        output.setResourceBundle(bundle);
        return this;
    }

    public PlugInBuilder addResourceBundle() throws IOException {
        assert source != null;
        try (InputStream stream = source
                .getInputStream(getFileEntry(Locale.getDefault() + ".properties"))) {
            addResourceBundle(new PropertyResourceBundle(stream));
        }
        return this;
    }

    private ZipEntry getFileEntry(String filename) {
        return source.getEntry(output.getName() + "/" + filename);
    }

    public PlugIn build() throws IOException {
        source.close();
        return output;
    }
}
