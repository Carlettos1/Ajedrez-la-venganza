package com.carlettos.game.board.piece.pattern.starting;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Pattern;

/**
 *
 * @author Carlettos
 */
public interface PatternMagicianMove extends Pattern {

    @Override
    public default boolean match(AbstractBoard board, Point start, Point end) {
        if (end.equals(start.add(1, 0))) {
            return true;
        }
        if (end.equals(start.add(2, 0))) {
            return true;
        }
        if (end.equals(start.add(0, 1))) {
            return true;
        }
        if (end.equals(start.add(0, 2))) {
            return true;
        }
        if (end.equals(start.add(-1, 0))) {
            return true;
        }
        if (end.equals(start.add(-2, 0))) {
            return true;
        }
        if (end.equals(start.add(0, -1))) {
            return true;
        }
        if (end.equals(start.add(0, -2))) {
            return true;
        }
        return false;
    }
}
