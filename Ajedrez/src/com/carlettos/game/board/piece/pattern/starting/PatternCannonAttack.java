package com.carlettos.game.board.piece.pattern.starting;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Pattern;

/**
 *
 * @author Carlettos
 */
public interface PatternCannonAttack extends Pattern {

    @Override
    public default boolean match(AbstractBoard board, Point start, Point end) {
        if (Math.abs(start.x - end.x) > 3) {
            return false;
        }
        if (Math.abs(start.y - end.y) > 3) {
            return false;
        }

        return true;
    }
}
