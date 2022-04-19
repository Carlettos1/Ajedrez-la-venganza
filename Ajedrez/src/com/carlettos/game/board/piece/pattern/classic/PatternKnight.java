package com.carlettos.game.board.piece.pattern.classic;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Pattern;

/**
 *
 * @author Carlettos
 */
public interface PatternKnight extends Pattern{

    @Override
    public default boolean match(AbstractBoard tablero, Point inicio, Point final_) {
        int deltaX = final_.x - inicio.x;
        int deltaY = final_.y - inicio.y;
        deltaX = Math.abs(deltaX);
        deltaY = Math.abs(deltaY);

        boolean can = (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);
        return can;
    }
}
