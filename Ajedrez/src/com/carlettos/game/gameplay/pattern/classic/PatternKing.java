package com.carlettos.game.gameplay.pattern.classic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;

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
