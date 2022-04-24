package com.carlettos.game.util;

import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.helper.ConfigHelper;
import com.carlettos.game.util.helper.FileHelper;
import com.google.gson.JsonObject;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 *
 * @author Carlettos
 */
public record ResourceLocation(String key) {
    
    static {
        updateResurces();
    }

    private static final Pattern PATTERN_VALID_CHARACTERS = Pattern.compile("([a-z0-9_.])");
    private static JsonObject ENGLISH;
    private static JsonObject CUSTOM;

    public ResourceLocation {
        requireStandard(key);
    }
    
    public String getTexture(Color color) { //TODO: texturas no dependientes del color
        return key.substring(key.lastIndexOf(".") + 1) + "_" + color.name().toLowerCase();
    }
    
    public String getName() {
        if (CUSTOM.getAsJsonPrimitive(this.key()) != null) {
            return CUSTOM.getAsJsonPrimitive(this.key()).getAsString();
        } else if (ENGLISH.getAsJsonPrimitive(this.key()) != null) {
            return ENGLISH.getAsJsonPrimitive(this.key()).getAsString();
        } else {
            return this.key();
        }
    }
    
    public static void requireStandard(String key) {
        Objects.requireNonNull(key, "Null key");
        if (key.isEmpty()) {
            throw new IllegalArgumentException("Key cannot be an empty String");
        }
        if (key.length() > 40) {
            throw new IllegalArgumentException("Key cannot be over 40 characters");
        }
        if (!key.replaceAll(PATTERN_VALID_CHARACTERS.pattern(), "").isEmpty()) {
            throw new IllegalArgumentException("Non [a-z0-9_.] character in key");
        }
    }
    
    public static void updateResurces() {
        ENGLISH = FileHelper.getFromFile(FileHelper.LANG_FOLDER + "eng.json");
        CUSTOM = FileHelper.getFromFile(FileHelper.LANG_FOLDER + ConfigHelper.getLanguage() + ".json");
    }
}
