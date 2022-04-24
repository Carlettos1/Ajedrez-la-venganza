package com.carlettos.game.gameplay.pattern.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.pattern.PatternPawn;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public interface PatternSuperPawnTake extends PatternPawn {

    @Override
    public default boolean match(AbstractBoard board, Point start, Point end) {
        int sign = getColor().equals(Color.WHITE) ? 1 : -1;
        if(start.add(1, sign * 1).equals(end)){
            return true;
        }
        if(start.add(0, sign * 1).equals(end)){
            return true;
        }
        if(start.add(-1, sign * 1).equals(end)){
            return true;
        }
        return false;
    }
}
