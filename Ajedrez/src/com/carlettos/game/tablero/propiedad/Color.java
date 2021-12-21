package com.carlettos.game.tablero.propiedad;

/**
 * Color negro, blanco, etc.
 *
 * @author Carlos
 */
public enum Color {
    BLANCO("#FFFFFF"),
    NEGRO("#000000"),
    GRIS("#888888"),
    CIAN("#00FFFF"),
    MAGENTA("#FF00FF"),
    ROJO("#FF0000"),
    NARANJA("#FFA500");

    private final String color;
    private final java.awt.Color colorAWT;

    /**
     * @param color valor en rgb hexadecimal del color, debe ser un string de la
     * forma {@code "#rrggbb"}.
     */
    private Color(String color) {
        this.color = color;
        this.colorAWT = java.awt.Color.decode(this.color);
    }

    public java.awt.Color getColor() {
        return colorAWT;
    }

    public java.awt.Color getColorNegativo() {
        return new java.awt.Color(255 - this.getColor().getRed(), 255 - this.getColor().getGreen(), 255 - this.getColor().getBlue());
    }
}
