package com.carlettos.game.gameplay.pattern.starting;

import com.carlettos.game.board.IBaseBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;

/**
 *
 * @author Carlettos
 */
public interface CannonAttackPattern extends Pattern {

    @Override
    public default boolean match(IBaseBoard board, Point start, Point end) {
        return Math.abs(start.x - end.x) <= 3 && Math.abs(start.y - end.y) <= 3;
    }
}
