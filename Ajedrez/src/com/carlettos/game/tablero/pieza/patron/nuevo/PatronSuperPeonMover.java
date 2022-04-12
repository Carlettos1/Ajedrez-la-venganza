package com.carlettos.game.tablero.pieza.patron.nuevo;

import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.AbstractTablero;
import com.carlettos.game.tablero.pieza.patron.PatronPeon;
import com.carlettos.game.tablero.propiedad.Color;

/**
 * 
 * @author Carlettos
 */
public interface PatronSuperPeonMover extends PatronPeon {

    @Override
    public default boolean checkPatron(AbstractTablero tablero, Point inicio, Point final_){
        int sign = getColor().equals(Color.BLANCO) ? 1 : -1;
        if(inicio.add(1, sign * 1).equals(final_)){
            return true;
        }
        if(inicio.add(0, sign * 1).equals(final_)){
            return true;
        }
        if(inicio.add(-1, sign * 1).equals(final_)){
            return true;
        }
        if(inicio.add(1, sign * 2).equals(final_)){
            return !tablero.getEscaque(inicio.add(1, sign * 1)).hasPieza();
        }
        if(inicio.add(0, sign * 2).equals(final_)){
            return !tablero.getEscaque(inicio.add(0, sign * 1)).hasPieza();
        }
        if(inicio.add(-1, sign * 2).equals(final_)){
            return !tablero.getEscaque(inicio.add(-1, sign * 1)).hasPieza();
        }
        return false;
    }
}
