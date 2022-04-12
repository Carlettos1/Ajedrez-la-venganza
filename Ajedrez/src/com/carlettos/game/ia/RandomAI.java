package com.carlettos.game.ia;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.Direction;
import com.carlettos.game.core.Either;
import com.carlettos.game.core.Par;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.Escaque;
import com.carlettos.game.tablero.jugador.Jugador;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.habilidad.Info;
import com.carlettos.game.tablero.propiedad.habilidad.InfoCompuesta;
import com.carlettos.game.tablero.propiedad.habilidad.InfoInteger;
import com.carlettos.game.tablero.propiedad.habilidad.InfoNESW;
import com.carlettos.game.tablero.propiedad.habilidad.InfoNinguna;
import com.carlettos.game.tablero.propiedad.habilidad.InfoPieza;
import com.carlettos.game.tablero.propiedad.habilidad.InfoPoint;
import com.carlettos.game.tablero.propiedad.habilidad.InfoString;
import com.carlettos.game.visual.TableroVisual;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Carlettos
 */
public class RandomAI {
    private final Tablero tablero;
    private final TableroVisual visual;
    private final Jugador jugador;

    public RandomAI(TableroVisual visual, Jugador jugador) {
        this.tablero = visual.getTablero();
        this.visual = visual;
        this.jugador = jugador;
    }
    
    public void jugar(){
        List<Par<Escaque, List<Either<Object, Par<Point, Accion>>>>> lista = getAllAcciones();
        lista.removeIf(par -> par.y.size() <= 0);
        Random rng = new Random();
        if(lista.isEmpty()){
            List<Pieza> tmp = tablero.getAllPiezasOfColor(getJugador().getColor());
            System.out.println(tmp);
            tmp.removeIf(p -> p.seHaMovidoEsteTurno());
            System.out.println(tmp);
            System.out.println(getAllAcciones());
            return;
        }
        Par<Escaque, List<Either<Object, Par<Point, Accion>>>> escaqueEither = lista.get(rng.nextInt(lista.size()));
        Escaque escaque = escaqueEither.x;
        List<Either<Object, Par<Point, Accion>>> eithers = escaqueEither.y;
        Either<Object, Par<Point, Accion>> either = eithers.get(rng.nextInt(eithers.size()));
        if(either.isLeft()){
            Object valor = either.left;
            System.out.println(escaque.getPieza().getNombre() + "(" + escaque.getPos() + ")" 
                    + " va a hacer habilidad " 
                    + valor.getClass().getSimpleName() + ": " + valor);
            switch (valor) {
                case Integer i -> tablero.intentarHabilidadPieza(escaque.getPos(), new InfoInteger(i));
                case Direction dir -> tablero.intentarHabilidadPieza(escaque.getPos(), new InfoNESW(dir));
                case Pieza pieza -> tablero.intentarHabilidadPieza(escaque.getPos(), new InfoPieza(pieza));
                case Point point -> tablero.intentarHabilidadPieza(escaque.getPos(), new InfoPoint(point));
                case String str -> tablero.intentarHabilidadPieza(escaque.getPos(),
                        str.equals("Ninguna") ? new InfoNinguna() : new InfoString(str));
                case Par<?, ?> par -> tablero.intentarHabilidadPieza(escaque.getPos(), new InfoCompuesta(par));
                case default -> {System.out.println("valor no es caso esperado");}
            }
        } else {
            Par<Point, Accion> parAccion = either.right;
            System.out.println(escaque.getPieza().getNombre() + " va a " + parAccion.y + " en " + parAccion.x);
            switch (parAccion.y){
                case MOVER -> tablero.intentarMoverPieza(escaque.getPos(), parAccion.x);
                case COMER -> tablero.intentarComerPieza(escaque.getPos(), parAccion.x);
                case ATACAR -> tablero.intentarAtacarPieza(escaque.getPos(), parAccion.x);
                case default -> System.out.println("accion inesperada");
            }
        }
        visual.repaint();
    }
    
    public List<Par<Escaque, List<Either<Object, Par<Point, Accion>>>>> getAllAcciones(){
        List<Par<Escaque, List<Either<Object, Par<Point, Accion>>>>> lista = new ArrayList<>();
        for (int x = 0; x < tablero.columnas; x++) {
            for (int y = 0; y < tablero.filas; y++) {
                Escaque escaque = tablero.getEscaque(x, y);
                if(escaque.getPieza().seHaMovidoEsteTurno()){
                    continue;
                }
                if(tablero.canMoverPieza(escaque.getPieza())){
                    lista.add(Par.of(escaque, getAllAccionesEscaque(escaque)));
                }
            }
        }
        return lista;
    }
    
    public <V> List<Either<V, Par<Point, Accion>>> getAllAccionesEscaque(Escaque escaque){
        List<Either<V, Par<Point, Accion>>> lista = new ArrayList<>();
        Pieza pieza = escaque.getPieza();
        Point pos = escaque.getPos();
        for (Par<Point, Accion> accion : pieza.allAcciones(tablero, pos)) {
            lista.add(Either.makeRight(accion));
        }
        for (Object valores : pieza.getHabilidad().getOpciones(tablero, pos)) {
            lista.add(Either.makeLeft((V)valores));
        }
        return lista;
    }

    public Jugador getJugador() {
        return jugador;
    }
}
