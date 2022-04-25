package com.carlettos.game.gameplay.ability;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.Direction;

/**
 *
 * @author Carlettos
 */
public sealed interface InfoUse<V> permits InfoUse.AbilityInteger, 
        InfoUse.AbilityDirection, InfoUse.AbilityNone, InfoUse.AbilityPiece, 
        InfoUse.AbilityPoint, InfoUse.AbilityString, 
        InfoUse.AbilityTuple, Ability {
    
    V[] getPossibleValues(AbstractBoard board, Point start);
    
    default String formatInfo(V info) {
        return info.toString();
    }
    
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
        @Override
        public default String formatInfo(Point info) {
            return "%s, %s".formatted(info.x, info.y);
        }
    }
    
    public static non-sealed interface AbilityString extends InfoUse<String> {
    }
    
    public static non-sealed interface AbilityTuple<A, B> extends InfoUse<Tuple<A, B>> {
        @Override
        public default String formatInfo(Tuple<A, B> info) {
            return "%s, %s".formatted(info.x, info.y);
        }
    }
}
