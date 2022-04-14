package com.carlettos.game.ia;

import com.carlettos.game.core.Accion;
import static com.carlettos.game.core.Accion.ATACAR;
import static com.carlettos.game.core.Accion.COMER;
import com.carlettos.game.core.Direction;
import com.carlettos.game.core.Either;
import com.carlettos.game.core.Par;
import com.carlettos.game.core.Point;
import com.carlettos.game.core.Trio;
import com.carlettos.game.tablero.Escaque;
import com.carlettos.game.tablero.jugador.Jugador;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.clasica.Alfil;
import com.carlettos.game.tablero.pieza.clasica.Caballo;
import com.carlettos.game.tablero.pieza.clasica.Peon;
import com.carlettos.game.tablero.pieza.clasica.Reina;
import com.carlettos.game.tablero.pieza.clasica.Rey;
import com.carlettos.game.tablero.pieza.clasica.Torre;
import com.carlettos.game.tablero.pieza.nueva.Ariete;
import com.carlettos.game.tablero.pieza.nueva.Arquero;
import com.carlettos.game.tablero.pieza.nueva.Ballesta;
import com.carlettos.game.tablero.pieza.nueva.Brujo;
import com.carlettos.game.tablero.pieza.nueva.Catapulta;
import com.carlettos.game.tablero.pieza.nueva.Cañon;
import com.carlettos.game.tablero.pieza.nueva.Constructor;
import com.carlettos.game.tablero.pieza.nueva.Defensor;
import com.carlettos.game.tablero.pieza.nueva.Hechicero;
import com.carlettos.game.tablero.pieza.nueva.Muro;
import com.carlettos.game.tablero.pieza.nueva.Nave;
import com.carlettos.game.tablero.pieza.nueva.Paladin;
import com.carlettos.game.tablero.pieza.nueva.PeonLoco;
import com.carlettos.game.tablero.pieza.nueva.SuperPeon;
import com.carlettos.game.tablero.pieza.nueva.TorreTesla;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.habilidad.InfoCompuesta;
import com.carlettos.game.tablero.propiedad.habilidad.InfoInteger;
import com.carlettos.game.tablero.propiedad.habilidad.InfoNESW;
import com.carlettos.game.tablero.propiedad.habilidad.InfoNinguna;
import com.carlettos.game.tablero.propiedad.habilidad.InfoPieza;
import com.carlettos.game.tablero.propiedad.habilidad.InfoPoint;
import com.carlettos.game.tablero.propiedad.habilidad.InfoString;
import com.carlettos.game.visual.TableroVisual;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 *
 * @author Carlettos
 */
public class SimpleDepthAI extends AbstractAI  {
    private static Map<Class<? extends Pieza>, Integer> valores;
    protected final int depth;
    protected final Jugador rival;
    protected int cantidad = 10;
    
    static {
        getValoresPiezas();
    }

    public SimpleDepthAI(TableroVisual visual, Jugador jugador, int depth) {
        super(visual, jugador);
        this.depth = depth;
        if(getTablero().getReloj().getJugadores().get(0) == jugador){
            rival = getTablero().getReloj().getJugadores().get(1);
        } else {
            rival = getTablero().getReloj().getJugadores().get(0);
        }
    }
    
    private Color getOtroColor(Color color){
        return color.equals(getJugador().getColor()) ? rival.getColor() : getJugador().getColor();
    }

    @Override
    public void jugar() {
        TableroVirtual t = TableroVirtual.fromTablero(getTablero());
        List<Par<Escaque, List<Either<Object, Par<Point, Accion>>>>> acciones = getAllAcciones(t, getJugador());
        List<Jugada> jugadas = getMejoresJugadas(acciones, t, getJugador().getColor(), cantidad);
        if(jugadas == null){
            System.out.println("No hay jugadas para: " + getJugador());
            return;
        }
        Jugada jugada = getMejorJugadaInDepth(jugadas, t);
        interpretarEither(jugada.x, jugada.y, getTablero());
        getVisual().repaint();
    }
    
    public Jugada getMejorJugadaInDepth(List<Jugada> acciones, TableroVirtual tablero){
        if(acciones.size() == 1){
            return acciones.get(0);
        }
        List<Par<Trio<Jugada, Jugada, Jugada>, Integer>> todas = new ArrayList<>();
        TableroVirtual t0 = TableroVirtual.fromTablero(tablero);
        int i = 0;
        for (int d = 0; d < depth; d++) {
            TableroVirtual t1 = TableroVirtual.fromTablero(t0);
            for (Jugada j2 : acciones) {
                i++;
                TableroVirtual t2 = TableroVirtual.fromTablero(t1);
                interpretarJugada(j2, t2);
                List<Jugada> lj2 = getMejoresJugadas(getAllAcciones(t2, rival), t2, rival.getColor(), cantidad);
                if(lj2 == null){
                    continue;
                }
                for (Jugada j3 : lj2) {
                    TableroVirtual t3 = TableroVirtual.fromTablero(t2);
                    interpretarJugada(j3, t3);
                    List<Jugada> lj3 = getMejoresJugadas(getAllAcciones(t3, getJugador()), t3, getJugador().getColor(), cantidad);
                    if(lj3 == null){
                        continue;
                    }
                    for (Jugada j4 : lj3) {
                        TableroVirtual t4 = TableroVirtual.fromTablero(t3);
                        interpretarJugada(j4, t4);
                        
                        todas.add(Par.of(Trio.of(j2, j3, j4), t4.getValoracionTotal(getJugador().getColor(), rival.getColor(), valores)));
                    }
                }
                if (todas.size() > 1000) {
                    System.out.print("Sorting");
                    todas.sort((p1, p2) -> Integer.compare(p1.y, p2.y));
                    todas = todas.subList(0, 1000);
                    System.out.println("-ed ");
                    System.out.println(i + "/" + acciones.size());
                }
            }
            break;
        }
        todas.sort((p1, p2) -> Integer.compare(p1.y, p2.y));
        return todas.get(0).x.x;
    }
    
    public List<Jugada> getMejoresJugadas(List<Par<Escaque, List<Either<Object, Par<Point, Accion>>>>> acciones, TableroVirtual tablero, Color color, int cantidad){
        List<Par<Jugada, Integer>> movidas = new ArrayList<>();
        
        for (Par<Escaque, List<Either<Object, Par<Point, Accion>>>> escaqueEither : acciones) {
            Escaque escaque = escaqueEither.x;
            List<Either<Object, Par<Point, Accion>>> eithers = escaqueEither.y;
            for (Either<Object, Par<Point, Accion>> either : eithers) {
                TableroVirtual sim = TableroVirtual.fromTablero(tablero);
                interpretarEither(escaque, either, sim);
                int valoracion = sim.getValoracionTotal(color, getOtroColor(color), valores);
                movidas.add(Par.of(Jugada.of(Par.of(escaque, either)), valoracion));
            }
        }
        if(movidas.isEmpty()){
            return null;
        }
        movidas.sort((par1, par2) -> Integer.compare(par1.y, par2.y));
        if(movidas.size() > cantidad){
            return movidas.subList(0, cantidad).stream().map(par -> par.x).toList();
        } else {
            return movidas.stream().map(par -> par.x).toList();
        }
    }
    
    public void interpretarJugada(Jugada jugada, TableroVirtual tablero){
        interpretarEither(jugada.x, jugada.y, tablero);
    }
    
    public void interpretarEither(Escaque escaque, Either<Object, Par<Point, Accion>> either, TableroVirtual t){
        if(either.isLeft()){
            Object valor = either.left;
            switch (valor) {
                case Integer i -> t.intentar(Accion.HABILIDAD, escaque.getPos(), escaque.getPos(), new InfoInteger(i));
                case Direction dir -> t.intentar(Accion.HABILIDAD, escaque.getPos(), escaque.getPos(), new InfoNESW(dir));
                case Pieza pieza -> t.intentar(Accion.HABILIDAD, escaque.getPos(), escaque.getPos(), new InfoPieza(pieza));
                case Point point -> t.intentar(Accion.HABILIDAD, escaque.getPos(), escaque.getPos(), new InfoPoint(point));
                case String str -> t.intentar(Accion.HABILIDAD, escaque.getPos(), escaque.getPos(),
                        str.equals("Ninguna") ? new InfoNinguna() : new InfoString(str));
                case Par<?, ?> par -> t.intentar(Accion.HABILIDAD, escaque.getPos(), escaque.getPos(), new InfoCompuesta(par));
                case default -> System.out.println("valor no es caso esperado");
            }
        } else {
            Par<Point, Accion> parAccion = either.right;
            switch (parAccion.y){
                case MOVER, COMER, ATACAR -> t.intentar(parAccion.y, escaque.getPos(), parAccion.x, null);
                case default -> System.out.println("accion inesperada");
            }
        }
    }
    
    protected List<Par<Escaque, List<Either<Object, Par<Point, Accion>>>>> getAllAcciones(TableroVirtual tablero, Jugador jugador){
        List<Par<Escaque, List<Either<Object, Par<Point, Accion>>>>> lista = new ArrayList<>();
        for (int x = 0; x < tablero.columnas; x++) {
            for (int y = 0; y < tablero.filas; y++) {
                Escaque escaque = tablero.getEscaque(x, y);
                if(escaque.getPieza().seHaMovidoEsteTurno()){
                    continue;
                }
                if(jugador.getColor().equals(escaque.getColorControlador())){
                    lista.add(Par.of(escaque, getAllAccionesEscaque(tablero, escaque)));
                }
            }
        }
        lista.removeIf(par -> par.y.size() <= 0);
        return lista;
    }
    
    protected <V> List<Either<V, Par<Point, Accion>>> getAllAccionesEscaque(TableroVirtual tablero, Escaque escaque){
        List<Either<V, Par<Point, Accion>>> lista = new ArrayList<>();
        Pieza pieza = escaque.getPieza();
        Point pos = escaque.getPos();
        for (Par<Point, Accion> accion : pieza.allAcciones(tablero, pos)) {
            lista.add(Either.makeRight(accion));
        }
        for (Object vs : pieza.getHabilidad().getOpciones(tablero, pos)) {
            lista.add(Either.makeLeft((V)vs));
        }
        return lista;
    }
    
    public static Map<Class<? extends Pieza>, Integer> getValoresPiezas(){
        if(valores == null){
            valores = new HashMap<>();
            valores.put(Alfil.class, 4);
            valores.put(Caballo.class, 4);
            valores.put(Peon.class, 1);
            valores.put(Reina.class, 10);
            valores.put(Rey.class, 2);
            valores.put(Torre.class, 10);
            valores.put(Ariete.class, 5);
            valores.put(Arquero.class, 8);
            valores.put(Ballesta.class, 5);
            valores.put(Brujo.class, 0);
            valores.put(Catapulta.class, 2);
            valores.put(Cañon.class, 7);
            valores.put(Constructor.class, 2);
            valores.put(Defensor.class, 1);
            valores.put(Hechicero.class, 0);
            valores.put(Muro.class, 0);
            valores.put(Nave.class, 7);
            valores.put(Paladin.class, 10);
            valores.put(PeonLoco.class, 1);
            valores.put(SuperPeon.class, 3);
            valores.put(TorreTesla.class, 4);
        }
        return valores;
    }
    
    public static class Jugada extends Par<Escaque, Either<Object, Par<Point, Accion>>> {
        public Jugada(Escaque x, Either<Object, Par<Point, Accion>> y) {
            super(x, y);
        }
        
        public static Jugada of(Par<Escaque, Either<Object, Par<Point, Accion>>> par){
            return new Jugada(par.x, par.y);
        }
    }
}
