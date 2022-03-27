package com.carlettos.game.tablero.pieza.patron.nuevo;

import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.patron.Patron;

/**
 *
 * @author Carlettos
 */
public interface PatronCañonAtacar extends Patron{

    @Override
    public default boolean checkPatron(Tablero tablero, Point inicio, Point final_) {
        if (Math.abs(inicio.x - final_.x) > 3) {
            return false;
        }
        if (Math.abs(inicio.y - final_.y) > 3) {
            return false;
        }

        return true;
    }
}
