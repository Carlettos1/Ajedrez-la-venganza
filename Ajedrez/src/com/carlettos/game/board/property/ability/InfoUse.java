package com.carlettos.game.board.property.ability;

import com.carlettos.game.core.Direction;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.core.Tuple;

/**
 *
 * @author Carlettos
 */
public sealed interface InfoUse<V> permits InfoUse.AbilityInteger, 
        InfoUse.AbilityDirection, InfoUse.AbilityNone, InfoUse.AbilityPiece, 
        InfoUse.AbilityPoint, InfoUse.AbilityString, 
        InfoUse.AbilityTuple, Ability {
    
    V[] getPossibleValues(AbstractBoard board, Point start);
    
    public static non-sealed interface AbilityDirection extends InfoUse<Direction> {
        @Override
        public default Direction[] getPossibleValues(AbstractBoard tablero, Point inicio) {
            return Direction.values();
        }
    }
    
    public static non-sealed interface AbilityNone extends InfoUse<String> {
        @Override
        public default String[] getPossibleValues(AbstractBoard tablero, Point inicio) {
            return new String[]{"Usar"};
        }
    }
    
    public static non-sealed interface AbilityInteger extends InfoUse<Integer> {
    }
    
    public static non-sealed interface AbilityPiece extends InfoUse<Piece> {
    }
    
    public static non-sealed interface AbilityPoint extends InfoUse<Point> {
    }
    
    public static non-sealed interface AbilityString extends InfoUse<String> {
    }
    
    public static non-sealed interface AbilityTuple<A, B> extends InfoUse<Tuple<A, B>> {
    }
}
