package com.carlettos.game.ia;

import com.carlettos.game.core.Accion;
import static com.carlettos.game.core.Accion.ATACAR;
import static com.carlettos.game.core.Accion.COMER;
import com.carlettos.game.core.Direction;
import com.carlettos.game.core.Either;
import com.carlettos.game.core.Par;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.Escaque;
import com.carlettos.game.tablero.jugador.Jugador;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.habilidad.InfoCompuesta;
import com.carlettos.game.tablero.propiedad.habilidad.InfoInteger;
import com.carlettos.game.tablero.propiedad.habilidad.InfoNESW;
import com.carlettos.game.tablero.propiedad.habilidad.InfoNinguna;
import com.carlettos.game.tablero.propiedad.habilidad.InfoPieza;
import com.carlettos.game.tablero.propiedad.habilidad.InfoPoint;
import com.carlettos.game.tablero.propiedad.habilidad.InfoString;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlettos
 */
public interface AI {
    default List<Par<Escaque, List<Either<Object, Par<Point, Accion>>>>> getAllAcciones(){
        List<Par<Escaque, List<Either<Object, Par<Point, Accion>>>>> lista = new ArrayList<>();
        for (int x = 0; x < getTablero().columnas; x++) {
            for (int y = 0; y < getTablero().filas; y++) {
                Escaque escaque = getTablero().getEscaque(x, y);
                if(escaque.getPieza().seHaMovidoEsteTurno()){
                    continue;
                }
                if(getTablero().canMoverPieza(escaque.getPieza())){
                    lista.add(Par.of(escaque, getAllAccionesEscaque(escaque)));
                }
            }
        }
        return lista;
    }
    
    default void interpretarEither(Escaque escaque, Either<Object, Par<Point, Accion>> either, Tablero t){
        if(either.isLeft()){
            Object valor = either.left;
            switch (valor) {
                case Integer i -> t.intentarHabilidadPieza(escaque.getPos(), new InfoInteger(i));
                case Direction dir -> t.intentarHabilidadPieza(escaque.getPos(), new InfoNESW(dir));
                case Pieza pieza -> t.intentarHabilidadPieza(escaque.getPos(), new InfoPieza(pieza));
                case Point point -> t.intentarHabilidadPieza(escaque.getPos(), new InfoPoint(point));
                case String str -> t.intentarHabilidadPieza(escaque.getPos(),
                        str.equals("Ninguna") ? new InfoNinguna() : new InfoString(str));
                case Par<?, ?> par -> t.intentarHabilidadPieza(escaque.getPos(), new InfoCompuesta(par));
                case default -> System.out.println("valor no es caso esperado");
            }
        } else {
            Par<Point, Accion> parAccion = either.right;
            switch (parAccion.y){
                case MOVER -> t.intentarMoverPieza(escaque.getPos(), parAccion.x);
                case COMER -> t.intentarComerPieza(escaque.getPos(), parAccion.x);
                case ATACAR -> t.intentarAtacarPieza(escaque.getPos(), parAccion.x);
                case default -> System.out.println("accion inesperada");
            }
        }
    }
    
    default <V> List<Either<V, Par<Point, Accion>>> getAllAccionesEscaque(Escaque escaque){
        List<Either<V, Par<Point, Accion>>> lista = new ArrayList<>();
        Pieza pieza = escaque.getPieza();
        Point pos = escaque.getPos();
        for (Par<Point, Accion> accion : pieza.allAcciones(getTablero(), pos)) {
            lista.add(Either.makeRight(accion));
        }
        for (Object valores : pieza.getHabilidad().getOpciones(getTablero(), pos)) {
            lista.add(Either.makeLeft((V)valores));
        }
        return lista;
    }

    void jugar();
    Tablero getTablero();
    Jugador getJugador();
}
