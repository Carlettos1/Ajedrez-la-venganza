package com.carlettos.game.core.helper;

import com.carlettos.game.core.config.DefaultConfig;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 *
 * @author Carlettos
 */
public final class ConfigHelper {
    private static ConfigHelper INSTANCE;
    private Gson gson;
    private DefaultConfig defaultConfigs;

    private ConfigHelper() {
        this.gson = new Gson();
        this.updateConfigs();
    }
    
    public void updateConfigs() {
        try {
            var file = new File("./src/com/carlettos/resources/config.json");
            var reader = new BufferedReader(new FileReader(file));
            /*this.defaultConfigs = gson.fromJson(reader, DefaultConfig.class);
            JsonObject jo = new JsonObject();*/
            JsonReader jr = new JsonReader(reader);
            var a = gson.getAdapter(JsonObject.class).read(jr);
            System.out.println(a);
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Config file not found");
        } catch (IOException e) {
            System.out.println("Config file IOException");
        }
    }

    public DefaultConfig getDefaultConfigs() {
        return defaultConfigs;
    }

    public static final ConfigHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConfigHelper();
        }
        return INSTANCE;
    }
}
