package com.carlettos.game.board.property.ability;

import com.carlettos.game.core.Direction;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.Empty;
import com.carlettos.game.core.Tuple;

/**
 *
 * @author Carlettos
 */
public sealed interface InfoGetter<V> permits InfoGetter.AbilityInteger, 
        InfoGetter.AbilityDirection, InfoGetter.AbilityNone, InfoGetter.AbilityPiece, 
        InfoGetter.AbilityPoint, InfoGetter.AbilityString, 
        InfoGetter.AbilityTuple, Ability {
    
    V[] getPossibleValues(AbstractBoard board, Point start);
    
    public static non-sealed interface AbilityDirection extends InfoGetter<Direction> {
        @Override
        public default Direction[] getPossibleValues(AbstractBoard tablero, Point inicio) {
            return Direction.values();
        }
    }
    
    public static non-sealed interface AbilityNone extends InfoGetter<String> {
        @Override
        public default String[] getPossibleValues(AbstractBoard tablero, Point inicio) {
            return new String[]{"Usar"};
        }
    }
    
    public static non-sealed interface AbilityInteger extends InfoGetter<Integer> {
    }
    
    public static non-sealed interface AbilityPiece extends InfoGetter<Piece> {
    }
    
    public static non-sealed interface AbilityPoint extends InfoGetter<Point> {
    }
    
    public static non-sealed interface AbilityString extends InfoGetter<String> {
    }
    
    public static non-sealed interface AbilityTuple<A, B> extends InfoGetter<Tuple<A, B>> {
    }
}
