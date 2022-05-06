package com.carlettos.game.gameplay.pattern.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.pattern.PatternPawn;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.helper.PatternHelper;

/**
 *
 * @author Carlettos
 */
public interface PatternSuperPawnTake extends PatternPawn {

    @Override
    public default boolean match(AbstractBoard board, Point start, Point end) {
        int sign = getColor().equals(Color.WHITE) ? 1 : -1;
        return PatternHelper.anyMatch(start, end, 
                new Point(1, sign), new Point(0, sign), new Point(-1, sign));
    }
}
