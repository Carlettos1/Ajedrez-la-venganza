package com.carlettos.game.board.piece.pattern.starting;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Pattern;

/**
 *
 * @author Carlettos
 */
public interface PatternBallistaAttack extends Pattern {

    @Override
    public default boolean match(AbstractBoard board, Point start, Point end) {
        if(start.x == end.x || start.y == end.y){
            return start.getDistanceTo(end) <= 6;
        }
        return false;
    }
}
