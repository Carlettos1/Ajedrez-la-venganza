package com.carlettos.game.gameplay.pattern.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;

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
