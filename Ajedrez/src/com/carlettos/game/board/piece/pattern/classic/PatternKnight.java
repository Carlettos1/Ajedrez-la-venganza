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
    public default boolean match(AbstractBoard board, Point start, Point end) {
        int deltaX = end.x - start.x;
        int deltaY = end.y - start.y;
        deltaX = Math.abs(deltaX);
        deltaY = Math.abs(deltaY);

        boolean can = (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);
        return can;
    }
}
