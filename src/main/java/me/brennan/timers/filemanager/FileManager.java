package me.brennan.timers.filemanager;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import me.brennan.timers.Timers;

import java.io.File;
import java.io.FileReader;
import java.io.PrintWriter;

/**
 * made for LOLTimers
 *
 * @author Brennan
 * @since 5/10/2020
 **/
public class FileManager {
    private final Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    private final File baseDir;

    public FileManager(File baseDir) {
        this.baseDir = baseDir;

        if(!baseDir.exists()) {
            baseDir.mkdirs();
        }
    }

    public void load() throws Exception {
        final File settingsFile = new File(baseDir, "settings.json");
        if(!settingsFile.exists()) {
            settingsFile.createNewFile();
            save();
            return;
        }
        final JsonObject jsonObject = JsonParser.parseReader(new FileReader(settingsFile)).getAsJsonObject();
        if(jsonObject.has("api_key")) {
            Timers.INSTANCE.setApiKey(jsonObject.get("api_key").getAsString());
        }

        if(jsonObject.has("username")) {
            Timers.INSTANCE.setUsername(jsonObject.get("username").getAsString());
        }
    }

    public void save() throws Exception {
        final File settingsFile = new File(baseDir, "settings.json");
        if (!settingsFile.exists()) settingsFile.createNewFile();

        final PrintWriter printWriter = new PrintWriter(settingsFile);

        final JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("api_key", Timers.INSTANCE.getApiKey());
        jsonObject.addProperty("username", Timers.INSTANCE.getUsername());

        printWriter.write(GSON.toJson(jsonObject));
        printWriter.close();
    }
}
