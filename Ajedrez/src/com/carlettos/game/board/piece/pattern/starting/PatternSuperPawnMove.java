package com.carlettos.game.board.piece.pattern.starting;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.PatternPawn;
import com.carlettos.game.board.property.Color;

/**
 * 
 * @author Carlettos
 */
public interface PatternSuperPawnMove extends PatternPawn {

    @Override
    public default boolean match(AbstractBoard board, Point start, Point end){
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
        if(start.add(1, sign * 2).equals(end)){
            return !board.getEscaque(start.add(1, sign * 1)).hasPiece();
        }
        if(start.add(0, sign * 2).equals(end)){
            return !board.getEscaque(start.add(0, sign * 1)).hasPiece();
        }
        if(start.add(-1, sign * 2).equals(end)){
            return !board.getEscaque(start.add(-1, sign * 1)).hasPiece();
        }
        return false;
    }
}
