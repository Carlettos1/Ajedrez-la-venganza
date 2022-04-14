package com.carlettos.game.ia;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.Direction;
import com.carlettos.game.core.Either;
import com.carlettos.game.core.Par;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.Escaque;
import com.carlettos.game.tablero.jugador.Jugador;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.habilidad.InfoCompuesta;
import com.carlettos.game.tablero.propiedad.habilidad.InfoInteger;
import com.carlettos.game.tablero.propiedad.habilidad.InfoNESW;
import com.carlettos.game.tablero.propiedad.habilidad.InfoNinguna;
import com.carlettos.game.tablero.propiedad.habilidad.InfoPieza;
import com.carlettos.game.tablero.propiedad.habilidad.InfoPoint;
import com.carlettos.game.tablero.propiedad.habilidad.InfoString;
import com.carlettos.game.visual.TableroVisual;
import java.util.List;
import java.util.Random;

/**
 *
 * @author Carlettos
 */
public class RandomAI extends AbstractAI{

    public RandomAI(TableroVisual visual, Jugador jugador) {
        super(visual, jugador);
    }
    
    @Override
    public void jugar(){
        List<Par<Escaque, List<Either<Object, Par<Point, Accion>>>>> lista = getAllAcciones();
        lista.removeIf(par -> par.y.size() <= 0);
        Random rng = new Random();
        if(lista.isEmpty()){
            List<Pieza> tmp = getTablero().getAllPiezasOfColor(getJugador().getColor());
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
        interpretarEither(escaque, either, getTablero());
        getVisual().repaint();
    }
}
