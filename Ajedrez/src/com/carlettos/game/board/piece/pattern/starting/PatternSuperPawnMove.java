package com.carlettos.game.board.piece.pattern.starting;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.PatternPawn;
import com.carlettos.game.board.property.Color;

/**
 * 
 * @author Carlettos
 */
public interface PatternSuperPawnMove extends PatternPawn {

    @Override
    public default boolean checkPatron(AbstractBoard tablero, Point inicio, Point final_){
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
