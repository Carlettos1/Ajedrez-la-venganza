package com.carlettos.game.util.helper;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.imageio.ImageIO;

import com.carlettos.game.display.main.ConfigEntryPanel;
import com.carlettos.game.util.ResourceLocation;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

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
    public static final String UNDEFINED_TEXTURE = TEXTURES_FOLDER + "undefined.png";

    public static final JsonObject getFromFile(String path) {
        LogHelper.LOG.info(() -> "Reading file: %s".formatted(path));
        try (var reader = new JsonReader(new FileReader(path))) {
            return GSON.getAdapter(JsonObject.class).read(reader);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Path %s doesn't exists or worse".formatted(path));
        }
    }
    
    public static final void setToFile(String path, JsonObject jsonObject) {
        LogHelper.LOG.info(() -> "Saving file: %s".formatted(path));
        try (var writer = new JsonWriter(new FileWriter(path))) {
            GSON.getAdapter(JsonObject.class).write(writer, jsonObject);
        } catch (IOException ex) {
            throw new IllegalArgumentException("Path %s doesn't exists or worse".formatted(path));
        }
    }
    
    public static final BufferedImage getImageFromFile(String path) {
        var file = new File(path);
        if (!file.exists()) {
            file = new File(UNDEFINED_TEXTURE);
            LogHelper.LOG.warning(() -> "Texture at %s doesn't exists, using undefined one".formatted(path));
        }
        try {
            return ImageIO.read(file);
        } catch (IOException ex) {
            LogHelper.LOG.severe("Undefined texture doesn't exists");
            throw new IllegalArgumentException("path doesn't exists");
        }
    }
    
    public static final void updateHelpers() {
        ConfigEntryPanel.setChanged(false);
        LogHelper.LOG.info("Updating helpers");
        ConfigHelper.saveConfigs();
        
        ImageHelper.clearTextures();
        
        ResourceLocation.updateResurces();
        ConfigHelper.updateConfigs();
    }
}
