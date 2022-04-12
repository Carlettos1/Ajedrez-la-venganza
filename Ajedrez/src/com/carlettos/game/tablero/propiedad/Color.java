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

    /**
     * Color por defecto, utilizado para piezas sin color, como piezas neutrales 
     * o piezas que aún no se han puesto al tablero.
     */
    DEFAULT(GRIS),
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
    Color(String color) {
        this.color = color;
        this.colorAWT = java.awt.Color.decode(this.color);
    }

    Color(Color color) {
        this(color.color);
    }

    public java.awt.Color getAWT() {
        return colorAWT;
    }

    public java.awt.Color getColorNegativo() {
        return new java.awt.Color(255 - this.getAWT().getRed(), 255 - this.getAWT().getGreen(), 255 - this.getAWT().getBlue());
    }
}
