package com.carlettos.game.gameplay.pattern.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;

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
