package com.carlettos.game.tablero.pieza.patron.nuevo;

import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.AbstractTablero;
import com.carlettos.game.tablero.pieza.patron.Patron;

/**
 *
 * @author Carlettos
 */
public interface PatronBallestaAtacar extends Patron{

    @Override
    public default boolean checkPatron(AbstractTablero tablero, Point inicio, Point final_) {
        if(inicio.x == final_.x || inicio.y == final_.y){
            return inicio.getDistanceTo(final_) <= 6;
        }
        return false;
    }
}
