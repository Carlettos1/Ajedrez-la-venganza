package com.carlettos.game.board.piece.pattern.starting;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Patron;

/**
 *
 * @author Carlettos
 */
public interface PatronSanguijuelaComer extends Patron {

    @Override
    public default boolean checkPatron(AbstractBoard tablero, Point inicio, Point final_) {
        if (final_.equals(inicio.add(1, 1))) {
            return true;
        }
        if (final_.equals(inicio.add(1, -1))) {
            return true;
        }
        if (final_.equals(inicio.add(-1, 1))) {
            return true;
        }
        if (final_.equals(inicio.add(-1, -1))) {
            return true;
        }
        return false;
    }
}
