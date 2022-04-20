package com.carlettos.game.board.property.ability;

import com.carlettos.game.core.Direction;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.Empty;

/**
 *
 * @author Carlettos
 */
public sealed interface InfoGetter<V> permits InfoGetter.AbilityInteger, 
        InfoGetter.AbilityDirection, InfoGetter.AbilityNone, InfoGetter.AbilityPiece, 
        InfoGetter.AbilityPoint, InfoGetter.AbilityString, Ability {
    
    Info<V> getInfo();
    V[] getPossibleValues(AbstractBoard board, Point start);
    
    public static non-sealed interface AbilityInteger extends InfoGetter<Integer> {
        @Override
        default InfoInteger getInfo(){
            return new InfoInteger(0);
        }
    }
    
    public static non-sealed interface AbilityDirection extends InfoGetter<Direction> {
        @Override
        default InfoDirection getInfo(){
            return new InfoDirection(Direction.N);
        }

        @Override
        public default Direction[] getPossibleValues(AbstractBoard tablero, Point inicio) {
            return Direction.values();
        }
    }
    
    public static non-sealed interface AbilityNone extends InfoGetter<String> {
        @Override
        default InfoNone getInfo(){
            return new InfoNone();
        }

        @Override
        public default String[] getPossibleValues(AbstractBoard tablero, Point inicio) {
            return new String[0];
        }
    }
    
    public static non-sealed interface AbilityPiece extends InfoGetter<Piece> {
        @Override
        default InfoPiece getInfo(){
            return new InfoPiece(new Empty());
        }
    }
    
    public static non-sealed interface AbilityPoint extends InfoGetter<Point> {
        @Override
        default InfoPoint getInfo(){
            return new InfoPoint(new Point());
        }
    }
    
    public static non-sealed interface AbilityString extends InfoGetter {
        @Override
        default InfoString getInfo(){
            return new InfoString("");
        }
    }
}
