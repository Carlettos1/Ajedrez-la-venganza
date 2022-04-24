package com.carlettos.game.util.helper;

import com.carlettos.game.util.ResourceLocation;
import com.carlettos.game.util.function.Action;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 *
 * @author Carlettos
 */
public class FileHelper {
    private FileHelper() {
    }
    
    public static final Gson GSON = new Gson();
    public static final String BASE_FOLDER = "./src/com/carlettos/resources/";
    public static final String LANG_FOLDER = BASE_FOLDER + "lang/";
    public static final String TEXTURES_FOLDER = BASE_FOLDER + "textures/";
    public static final String CONFIG_FILE = BASE_FOLDER + "config.json";

    public static final JsonObject getFromFile(String path) {
        try (var reader = new JsonReader(new FileReader(path))) {
            return GSON.getAdapter(JsonObject.class).read(reader);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Path %s doesn't exists or worse".formatted(path));
        }
    }
    
    public static final void setToFile(String path, JsonObject jsonObject) {
        try (var writer = new JsonWriter(new FileWriter(path))) {
            GSON.getAdapter(JsonObject.class).write(writer, jsonObject);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Path %s doesn't exists or worse".formatted(path));
        }
    }
    
    public static final void updateHelpers() {
        ConfigHelper.saveConfigs();
        ResourceLocation.updateResurces();
        ConfigHelper.updateConfigs();
    }
}
