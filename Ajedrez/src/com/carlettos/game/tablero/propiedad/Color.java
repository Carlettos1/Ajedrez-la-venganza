package com.carlettos.game.tablero.propiedad;

import java.util.Objects;

/**
 * Color negro, blanco, etc. Un equipo puede contener a varios jugadores de
 * distintos colores, mientras que un color es simplemente el color de las
 * piezas.
 *
 * @author Carlos
 *
 * @see Equipo
 */
public class Color {

    public static final Color BLANCAS = new Color("Blanco", "#ffffff");
    public static final Color NEGRAS = new Color("Negro", "#000000");
    public static final Color NINGUNO = new Color("Ninguno", "#888888");

    private final String nombre;
    private final String color;
    private final java.awt.Color colorAWT;

    /**
     * Crea un color, la idea es que se use el mismo objeto para todas las
     * piezas, jugadores, estructuras, y cualquier otra cosa que tenga un color.
     * Los colores blanco y negro están como constantes en esta misma clase.
     *
     * @param nombre nombre del color, también puede ser cualquier otra palabra,
     * ya que solo sirve como una id del color.
     * @param color valor en rgb hexadecimal del color, debe ser un string de la
     * forma {@code "#rrggbb"}.
     */
    public Color(String nombre, String color) {
        this.nombre = nombre;
        this.color = color;
        this.colorAWT = java.awt.Color.decode(this.color);
    }

    public java.awt.Color getAwtColor() {
        return colorAWT;
    }

    public String getNombre() {
        return this.equals(NINGUNO) ? "" : nombre;
    }

    public String getColor() {
        return color;
    }

    @Override
    public String toString() {
        return getNombre();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.nombre);
        hash = 59 * hash + Objects.hashCode(this.color);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Color other = (Color) obj;
        if (!Objects.equals(this.nombre, other.nombre)) {
            return false;
        }
        return Objects.equals(this.color, other.color);
    }
}
