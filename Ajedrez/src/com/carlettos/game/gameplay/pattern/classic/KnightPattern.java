package com.carlettos.game.gameplay.pattern.classic;

import com.carlettos.game.board.IBaseBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;

/**
 *
 * @author Carlettos
 */
public interface KnightPattern extends Pattern {

    @Override
    public default boolean match(IBaseBoard board, Point start, Point end) {
        int deltaX = Math.abs(end.x - start.x);
        int deltaY = Math.abs(end.y - start.y);
        return (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);
    }
}
