package com.carlettos.game.core;

import com.carlettos.game.tablero.propiedad.Color;

/**
 * TODO: que sea configurable
 * @author Carlos
 */
public class Constantes {

    /**
     * Ancho y largo preferido de cada escaque.
     */
    public static final int TAMAÑO_CASILLA = 45;

    /**
     * Ancho preferido de cada carta.
     */
    public static final int TAMAÑO_CARTA_X = 150;

    /**
     * Largo preferido de cada carta.
     */
    public static final int TAMAÑO_CARTA_Y = 200;

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
    public static final Color COLOR1 = Color.NEGRO;

    /**
     * Segundo color de los escaques.
     */
    public static final Color COLOR2 = Color.BLANCO;

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
