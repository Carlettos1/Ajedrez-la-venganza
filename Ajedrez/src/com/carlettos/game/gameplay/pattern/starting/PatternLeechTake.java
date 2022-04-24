package com.carlettos.game.gameplay.pattern.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;

/**
 *
 * @author Carlettos
 */
public interface PatternLeechTake extends Pattern {

    @Override
    public default boolean match(AbstractBoard board, Point start, Point end) {
        if (end.equals(start.add(1, 1))) {
            return true;
        }
        if (end.equals(start.add(1, -1))) {
            return true;
        }
        if (end.equals(start.add(-1, 1))) {
            return true;
        }
        if (end.equals(start.add(-1, -1))) {
            return true;
        }
        return false;
    }
}
