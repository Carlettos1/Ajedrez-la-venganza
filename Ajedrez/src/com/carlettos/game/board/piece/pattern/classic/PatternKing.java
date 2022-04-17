package com.carlettos.game.board.piece.pattern.classic;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Pattern;

/**
 *
 * @author Carlettos
 */
public interface PatternKing extends Pattern{

    @Override
    public default boolean checkPatron(AbstractBoard tablero, Point inicio, Point final_) {
        if (Math.abs(inicio.x - final_.x) > 1) {
            return false;
        }
        return Math.abs(inicio.y - final_.y) <= 1;
    }
}
