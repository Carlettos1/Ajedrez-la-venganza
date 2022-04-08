package com.carlettos.game.tablero.pieza.patron;

import com.carlettos.game.tablero.propiedad.Color;

/**
 * Interfaz para piezas tipo peón; que necesiten ir en cierta dirección del
 * tablero.
 * @author Carlettos
 */
public interface PatronPeon extends Patron {

    /**
     * Devuelve el color del peon. //TODO: cambiar por dirección de tablero
     * @return Color del peon.
     */
    public Color getColor();
}
