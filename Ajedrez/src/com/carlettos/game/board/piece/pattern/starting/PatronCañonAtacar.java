package com.carlettos.game.board.piece.pattern.starting;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Patron;

/**
 *
 * @author Carlettos
 */
public interface PatronCañonAtacar extends Patron{

    @Override
    public default boolean checkPatron(AbstractBoard tablero, Point inicio, Point final_) {
        if (Math.abs(inicio.x - final_.x) > 3) {
            return false;
        }
        if (Math.abs(inicio.y - final_.y) > 3) {
            return false;
        }

        return true;
    }
}
