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
public sealed interface InfoGetter<V> permits InfoGetter.HabilidadInteger, 
        InfoGetter.HabilidadNESW, InfoGetter.HabilidadSinInfo, InfoGetter.HabilidadPieza, 
        InfoGetter.HabilidadPoint, InfoGetter.HabilidadString, Ability {
    
    Info<V> getInfoHabilidad();
    V[] getAllValoresPosibles(AbstractBoard tablero, Point inicio);
    
    public static non-sealed interface HabilidadInteger extends InfoGetter<Integer> {
        @Override
        default InfoInteger getInfoHabilidad(){
            return new InfoInteger(0);
        }
    }
    
    public static non-sealed interface HabilidadNESW extends InfoGetter<Direction> {
        @Override
        default InfoNESW getInfoHabilidad(){
            return new InfoNESW(Direction.N);
        }

        @Override
        public default Direction[] getAllValoresPosibles(AbstractBoard tablero, Point inicio) {
            return Direction.values();
        }
    }
    
    public static non-sealed interface HabilidadSinInfo extends InfoGetter<String> {
        @Override
        default InfoNone getInfoHabilidad(){
            return new InfoNone();
        }

        @Override
        public default String[] getAllValoresPosibles(AbstractBoard tablero, Point inicio) {
            return new String[0];
        }
    }
    
    public static non-sealed interface HabilidadPieza extends InfoGetter<Piece> {
        @Override
        default InfoPiece getInfoHabilidad(){
            return new InfoPiece(new Empty());
        }
    }
    
    public static non-sealed interface HabilidadPoint extends InfoGetter<Point> {
        @Override
        default InfoPoint getInfoHabilidad(){
            return new InfoPoint(new Point());
        }
    }
    
    public static non-sealed interface HabilidadString extends InfoGetter {
        @Override
        default InfoString getInfoHabilidad(){
            return new InfoString("");
        }
    }
}
