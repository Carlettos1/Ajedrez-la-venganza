package com.carlettos.game.board.piece.pattern.starting;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Patron;

/**
 *
 * @author Carlettos
 */
public interface PatronBallestaAtacar extends Patron{

    @Override
    public default boolean checkPatron(AbstractBoard tablero, Point inicio, Point final_) {
        if(inicio.x == final_.x || inicio.y == final_.y){
            return inicio.getDistanceTo(final_) <= 6;
        }
        return false;
    }
}
