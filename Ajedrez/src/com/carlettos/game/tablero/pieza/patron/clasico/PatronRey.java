package com.carlettos.game.tablero.pieza.patron.clasico;

import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.AbstractTablero;
import com.carlettos.game.tablero.pieza.patron.Patron;

/**
 *
 * @author Carlettos
 */
public interface PatronRey extends Patron{

    @Override
    public default boolean checkPatron(AbstractTablero tablero, Point inicio, Point final_) {
        if (Math.abs(inicio.x - final_.x) > 1) {
            return false;
        }
        return Math.abs(inicio.y - final_.y) <= 1;
    }
}
