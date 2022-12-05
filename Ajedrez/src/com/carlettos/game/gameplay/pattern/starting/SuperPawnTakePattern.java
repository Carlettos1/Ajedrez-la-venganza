package com.carlettos.game.gameplay.pattern.starting;

import com.carlettos.game.board.IBaseBoard;
import com.carlettos.game.gameplay.pattern.PawnPattern;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.helper.PatternHelper;

/**
 *
 * @author Carlettos
 */
public interface SuperPawnTakePattern extends PawnPattern {

    @Override
    public default boolean match(IBaseBoard board, Point start, Point end) {
        int sign = getColor().equals(Color.WHITE) ? 1 : -1;
        return PatternHelper.anyMatch(start, end, new Point(1, sign), new Point(0, sign), new Point(-1, sign));
    }
}
