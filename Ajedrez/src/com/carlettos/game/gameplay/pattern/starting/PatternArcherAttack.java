package com.carlettos.game.gameplay.pattern.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;

/**
 *
 * @author Carlettos
 */
public interface PatternArcherAttack extends Pattern {

    @Override
    public default boolean match(AbstractBoard board, Point start, Point end) {
        final int range = 6;
        return start.getDistanceTo(end) <= range;
    }
}
