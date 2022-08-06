package com.carlettos.game.util.resource;

import java.util.Objects;
import java.util.regex.Pattern;

import com.carlettos.game.util.helper.ConfigHelper;
import com.carlettos.game.util.helper.FileHelper;
import com.carlettos.game.util.helper.LogHelper;
import com.google.gson.JsonObject;

public record TranslateResource(String translationKey) implements ITranslatable {
    private static final Pattern PATTERN_VALID_CHARACTERS = Pattern.compile("([a-z0-9_.])");
    private static JsonObject ENGLISH;
    private static JsonObject CUSTOM;

    static {
        updateResurces();
    }
    
    public TranslateResource(String type, String name) {
        this(type + '.' + name);
    }
    
    public TranslateResource {
        requireStandard(translationKey);
    }

    public String getTranslationKey() {
        return translationKey;
    }

    @Override
    public String getTranslated() {
        if (CUSTOM.getAsJsonPrimitive(this.getTranslationKey()) != null) {
            return CUSTOM.getAsJsonPrimitive(this.getTranslationKey()).getAsString();
        } else if (ENGLISH.getAsJsonPrimitive(this.getTranslationKey()) != null) {
            return ENGLISH.getAsJsonPrimitive(this.getTranslationKey()).getAsString();
        } else {
            LogHelper.LOG.info(() -> this.getTranslationKey() + " doesn't exists in either CUSTOM nor ENGLISH, using translation key instead");
            return this.getTranslationKey();
        }
    }

    public static void requireStandard(String str) {
        Objects.requireNonNull(str, "Null str");
        if (str.isEmpty()) { throw new IllegalArgumentException("Str cannot be an empty String"); }
        if (str.length() > 40) { throw new IllegalArgumentException("Str cannot be over 40 characters"); }
        if (!str.replaceAll(PATTERN_VALID_CHARACTERS.pattern(), "").isEmpty()) {
            throw new IllegalArgumentException("Non [a-z0-9_.] character in str " + str);
        }
    }

    public static void updateResurces() {
        ENGLISH = FileHelper.getFromFile(FileHelper.LANG_FOLDER + "eng.json");
        CUSTOM = FileHelper.getFromFile(FileHelper.LANG_FOLDER + ConfigHelper.getLanguage() + ".json");
    }
}
