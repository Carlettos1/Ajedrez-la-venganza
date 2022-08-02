package com.carlettos.game.util.enums;

import com.carlettos.game.util.helper.MathHelper;

/**
 * Black color, white color, etc. An interface with java.awt.Color.
 *
 * @author Carlos
 */
public enum Color {
    WHITE("#FFFFFF"), BLACK("#000000"), GRAY("#888888"), CYAN("#00FFFF"), MAGENTA("#FF00FF"), RED("#FF0000"),
    ORANGE("#FFA500"), AQUA("#03FDFC");

    private final String hex;
    private final java.awt.Color colorAWT;

    /**
     * General constructor.
     * 
     * @param hex rgb code in the form {@code #rrggbb}.
     * @see java.awt.Color#decode.
     */
    Color(String hex) {
        this.hex = hex;
        this.colorAWT = java.awt.Color.decode(this.hex);
    }

    /**
     * Gets the awt representation of this color.
     *
     * @return the java.awt.Color of this color.
     */
    public java.awt.Color getAWT() {
        return colorAWT;
    }

    /**
     * Creates the negative awt color of this color.
     *
     * @return the negative of this color.
     */
    public java.awt.Color getNegativeColor() {
        return new java.awt.Color(255 - this.getAWT().getRed(), 255 - this.getAWT().getGreen(),
                255 - this.getAWT().getBlue());
    }

    public static Color fromIndex(int idx) {
        MathHelper.requireInBounds(idx, 0, Color.values().length);
        return Color.values()[idx];
    }
}
