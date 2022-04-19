package com.carlettos.game.core;

import com.carlettos.game.board.property.Color;

/**
 * TODO: que sea configurable
 * @author Carlos
 */
public class Constants {

    /**
     * Ancho y largo preferido de cada escaque.
     */
    public static final int TAMAÑO_CASILLA = 30;

    /**
     * Ancho preferido de cada carta.
     */
    public static final int TAMAÑO_CARTA_X = 100;

    /**
     * Largo preferido de cada carta.
     */
    public static final int TAMAÑO_CARTA_Y = 120;

    /**
     * Cartas máximas admitidas por fila.
     */
    public static final int CARTAS_X = 3;

    /**
     * Cartas máximas admitidas por columna.
     */
    public static final int CARTAS_Y = 5;

    /**
     * Primer color de los escaques.
     */
    public static final Color COLOR1 = Color.BLACK;

    /**
     * Segundo color de los escaques.
     */
    public static final Color COLOR2 = Color.WHITE;

    /**
     * Porcentaje que recubren los círculos de indicación.
     */
    public static final double PORCENTAJE = 0.3;
    
    /**
     * Porcentaje chico que recubren los círculos de indicación.
     */
    public static final double PORCENTAJE_PEQUEÑO = 0.25;
    
    /**
     * Porcentaje chico que recubren los círculos de indicación.
     */
    public static final double PORCENTAJE_PEQUEÑITO = 0.2;
}
