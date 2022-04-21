package com.carlettos.game.board.piece.pattern.starting;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.PatternPawn;
import com.carlettos.game.board.property.Color;

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
