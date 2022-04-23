package com.carlettos.game.core.helper;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Carlettos
 */
public final class ConfigHelper {
    private static ConfigHelper INSTANCE;
    private Gson gson;
    private JsonObject configs;

    private ConfigHelper() {
        this.gson = new Gson();
        this.readConfigs();
    }
    
    public void readConfigs() {
        try {
            var file = new File("./src/com/carlettos/resources/config.json");
            var reader = new BufferedReader(new FileReader(file));
            var jr = new JsonReader(reader);
            configs = gson.getAdapter(JsonObject.class).read(jr);
            reader.close();
        } catch (FileNotFoundException e) {
            System.out.println("Read config file not found");
        } catch (IOException e) {
            System.out.println("Read config file IOException");
        }
    }
    
    public void writeConfigs() {
        try {
            var file = new File("./src/com/carlettos/resources/config.json");
            var writer = new BufferedWriter(new FileWriter(file));
            var jw = new JsonWriter(writer);
            gson.getAdapter(JsonObject.class).write(jw, configs);
            writer.close();
        } catch (FileNotFoundException e) {
            System.out.println("Writing config file not found");
        } catch (IOException e) {
            System.out.println("Writing config file IOException");
        }
    }

    public double getDoubleConfig(String config) {
        return configs.get(config).getAsDouble();
    }

    public int getIntConfig(String config) {
        return configs.get(config).getAsInt();
    }
    
    public void setNumberConfig(String config, double number){
        this.configs.addProperty(config, number);
    }
    
    public Set<Map.Entry<String, JsonElement>> getConfigEntries(){
        return this.configs.entrySet();
    }

    public static final ConfigHelper getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ConfigHelper();
        }
        return INSTANCE;
    }
}
