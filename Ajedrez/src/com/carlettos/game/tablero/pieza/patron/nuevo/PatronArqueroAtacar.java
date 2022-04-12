package com.carlettos.game.tablero.pieza.patron.nuevo;

import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.AbstractTablero;
import com.carlettos.game.tablero.pieza.patron.Patron;

/**
 *
 * @author Carlettos
 */
public interface PatronArqueroAtacar extends Patron{

    @Override
    public default boolean checkPatron(AbstractTablero tablero, Point inicio, Point final_) {
        final int rango = 6;
        return inicio.getDistanceTo(final_) <= rango;
    }
}
