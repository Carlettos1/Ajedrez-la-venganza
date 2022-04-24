package com.carlettos.game.util.helper;

import com.carlettos.game.util.enums.Color;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import java.util.Map;
import java.util.Set;

/**
 *
 * @author Carlettos
 */
public final class ConfigHelper {
    
    static {
        updateConfigs();
    }
    
    private static JsonObject CONFIGS;
    
    public static void updateConfigs() {
        CONFIGS = FileHelper.getFromFile(FileHelper.CONFIG_FILE);
    }
    
    public static void saveConfigs() {
        FileHelper.setToFile(FileHelper.CONFIG_FILE, CONFIGS);
    }

    public static double getDouble(String config) {
        return CONFIGS.get(config).getAsDouble();
    }

    public static int getInt(String config) {
        return CONFIGS.get(config).getAsInt();
    }

    public static String getString(String config) {
        return CONFIGS.get(config).getAsString();
    }
    
    public static void setDouble(String config, double number){
        CONFIGS.addProperty(config, number);
    }
    
    public static void setInt(String config, int number){
        CONFIGS.addProperty(config, number);
    }
    
    public static void setString(String config, String str){
        CONFIGS.addProperty(config, str);
    }
    
    public static Set<Map.Entry<String, JsonElement>> getConfigEntries(){
        return CONFIGS.entrySet();
    }
    
    public static int getEscaqueLength() {
        return getInt("escaque_lenght");
    }
    
    public static int getCardWidth() {
        return getInt("card_width");
    }
    
    public static int getCardHeight() {
        return getInt("card_height");
    }
    
    public static int getCardsPerRow() {
        return getInt("cards_per_row");
    }
    
    public static int getCardsPerColumn() {
        return getInt("cards_per_column");
    }
    
    public static int getClockHeight() {
        return getInt("clock_height");
    }
    
    public static Color getColor1() {
        return Color.fromIndex(getInt("color_1_id"));
    }
    
    public static Color getColor2() {
        return Color.fromIndex(getInt("color_2_id"));
    }
    
    public static Color getColorMana() {
        return Color.fromIndex(getInt("color_mana_id"));
    }
    
    public static double getPercentage1() {
        return getDouble("percentage_1");
    }
    
    public static double getPercentage2() {
        return getDouble("percentage_2");
    }
    
    public static double getPercentage3() {
        return getDouble("percentage_3");
    }
    
    public static double getPercentage4() {
        return getDouble("percentage_4");
    }
    
    public static double getPercentage(int number) {
        return switch (number) {
            case 1 -> getPercentage1();
            case 2 -> getPercentage2();
            case 3 -> getPercentage3();
            case 4 -> getPercentage4();
            default -> getPercentage4();
        };
    }
    
    public static String getManaSymbol() {
        return getString("mana_symbol");
    }
    
    public static String getLanguage() {
        return getString("language");
    }
}
