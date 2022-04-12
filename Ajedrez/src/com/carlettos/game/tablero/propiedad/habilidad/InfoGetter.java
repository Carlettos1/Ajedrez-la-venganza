package com.carlettos.game.tablero.propiedad.habilidad;

import com.carlettos.game.core.Direction;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.AbstractTablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.Vacia;

/**
 *
 * @author Carlettos
 */
public sealed interface InfoGetter<V> permits InfoGetter.HabilidadInteger, 
        InfoGetter.HabilidadNESW, InfoGetter.HabilidadSinInfo, InfoGetter.HabilidadPieza, 
        InfoGetter.HabilidadPoint, InfoGetter.HabilidadString, Habilidad {
    
    Info<V> getInfoHabilidad();
    V[] getAllValoresPosibles(AbstractTablero tablero, Point inicio);
    
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
        public default Direction[] getAllValoresPosibles(AbstractTablero tablero, Point inicio) {
            return Direction.values();
        }
    }
    
    public static non-sealed interface HabilidadSinInfo extends InfoGetter<String> {
        @Override
        default InfoNinguna getInfoHabilidad(){
            return new InfoNinguna();
        }

        @Override
        public default String[] getAllValoresPosibles(AbstractTablero tablero, Point inicio) {
            return new String[0];
        }
    }
    
    public static non-sealed interface HabilidadPieza extends InfoGetter<Pieza> {
        @Override
        default InfoPieza getInfoHabilidad(){
            return new InfoPieza(new Vacia());
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
