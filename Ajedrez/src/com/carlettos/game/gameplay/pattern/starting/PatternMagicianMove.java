package com.carlettos.game.gameplay.pattern.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.helper.PatternHelper;

/**
 *
 * @author Carlettos
 */
public interface PatternMagicianMove extends Pattern {
    @Override
    public default boolean match(AbstractBoard board, Point start, Point end) {
        return PatternHelper.anyMatch(start, end, 
                new Point(1, 0), new Point(0, 1), new Point(-1, 0), new Point(0, -1),
                new Point(2, 0), new Point(0, 2), new Point(-2, 0), new Point(0, -2));
    }
}
