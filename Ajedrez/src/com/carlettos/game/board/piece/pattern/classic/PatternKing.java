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
    public default boolean match(AbstractBoard board, Point start, Point end) {
        if (Math.abs(start.x - end.x) > 1) {
            return false;
        }
        return Math.abs(start.y - end.y) <= 1;
    }
}
