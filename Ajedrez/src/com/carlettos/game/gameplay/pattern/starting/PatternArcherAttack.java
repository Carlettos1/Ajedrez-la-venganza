package com.carlettos.game.gameplay.pattern.starting;

import com.carlettos.game.board.IBaseBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;

/**
 *
 * @author Carlettos
 */
public interface PatternArcherAttack extends Pattern {

    @Override
    public default boolean match(IBaseBoard board, Point start, Point end) {
        final int range = 6;
        return start.getDistanceTo(end) <= range;
    }
}
