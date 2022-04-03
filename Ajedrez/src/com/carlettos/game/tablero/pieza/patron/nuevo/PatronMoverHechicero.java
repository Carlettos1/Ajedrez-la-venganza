package com.carlettos.game.tablero.pieza.patron.nuevo;

import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.patron.Patron;

/**
 *
 * @author Carlettos
 */
public interface PatronMoverHechicero extends Patron {

    @Override
    public default boolean checkPatron(Tablero tablero, Point inicio, Point final_) {
        if (final_.equals(inicio.add(1, 0))) {
            return true;
        }
        if (final_.equals(inicio.add(2, 0))) {
            return true;
        }
        if (final_.equals(inicio.add(0, 1))) {
            return true;
        }
        if (final_.equals(inicio.add(0, 2))) {
            return true;
        }
        if (final_.equals(inicio.add(-1, 0))) {
            return true;
        }
        if (final_.equals(inicio.add(-2, 0))) {
            return true;
        }
        if (final_.equals(inicio.add(0, -1))) {
            return true;
        }
        if (final_.equals(inicio.add(0, -2))) {
            return true;
        }
        return false;
    }
}
