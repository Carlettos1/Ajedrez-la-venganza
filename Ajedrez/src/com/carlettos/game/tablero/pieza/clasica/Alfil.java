package com.carlettos.game.tablero.pieza.clasica;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Direction;
import com.carlettos.game.core.Par;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.habilidad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.TableroAbstract;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronAlfil;
import com.carlettos.game.tablero.propiedad.habilidad.InfoNESW;
import com.carlettos.game.tablero.propiedad.habilidad.InfoGetter.HabilidadNESW;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class Alfil extends PiezaSimple<PatronAlfil> {

    public static final Habilidad<Alfil, Direction, InfoNESW> HABILIDAD_ALFIL = new HabilidadAlfil<>();

    public Alfil(Color color) {
        super("Alfil", "A", HABILIDAD_ALFIL, color, new PatronAlfil(){}, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
    }

    @Override
    public List<Par<Point, Accion>> allAcciones(TableroAbstract tablero, Point seleccionado) {
        //myc viene de mover y comer
        List<Par<Point, Accion>> myc = super.allAcciones(tablero, seleccionado);
        if (this.getHabilidad().canUsar(tablero, this, seleccionado, new InfoNESW(Direction.N)).isPositive()) {
            myc.add(new Par<>(new Point(seleccionado.x, seleccionado.y + 1), Accion.HABILIDAD));
        }
        if (this.getHabilidad().canUsar(tablero, this, seleccionado, new InfoNESW(Direction.E)).isPositive()) {
            myc.add(new Par<>(new Point(seleccionado.x + 1, seleccionado.y), Accion.HABILIDAD));
        }
        if (this.getHabilidad().canUsar(tablero, this, seleccionado, new InfoNESW(Direction.S)).isPositive()) {
            myc.add(new Par<>(new Point(seleccionado.x, seleccionado.y - 1), Accion.HABILIDAD));
        }
        if (this.getHabilidad().canUsar(tablero, this, seleccionado, new InfoNESW(Direction.W)).isPositive()) {
            myc.add(new Par<>(new Point(seleccionado.x - 1, seleccionado.y), Accion.HABILIDAD));
        }
        return myc;
    }

    public static class HabilidadAlfil<P extends Pieza> extends Habilidad<P, Direction, InfoNESW> implements HabilidadNESW {
        public HabilidadAlfil() {
            super("Cambio de Color",
                    "El alfil cambia de color, pudiendo moverse una casilla en cualquiera de las 4 direcciones cardinales.",
                    2,
                    0,
                    "La dirección cardinal hacia dónde se mueve el alfil (NESW).");
        }

        @Override
        public ActionResult canUsar(TableroAbstract tablero, P pieza, Point inicio, InfoNESW info) {
            if (pieza.getCdActual() > 0) {
                return ActionResult.FAIL;
            }

            if (pieza.seHaMovidoEsteTurno()) {
                return ActionResult.FAIL;
            }
            
            boolean verificacion;
            
            if(info.isAxis(Direction.Axis.NS)){
                verificacion = !tablero.getEscaque(inicio.add(0, info.getSign())).hasPieza();
            } else {
                verificacion = !tablero.getEscaque(inicio.add(info.getSign(), 0)).hasPieza();
            }
            return ActionResult.fromBoolean(verificacion);
        }

        @Override
        public void usar(TableroAbstract tablero, P pieza, Point inicio, InfoNESW info) {
            if(info.isAxis(Direction.Axis.NS)){
                tablero.getEscaque(inicio.add(0, info.getSign())).setPieza(pieza);
            } else {
                tablero.getEscaque(inicio.add(info.getSign(), 0)).setPieza(pieza);
            }
            tablero.getEscaque(inicio).quitarPieza();
            //TODO: cambiar cd
        }

        @Override
        public Direction[] getAllValoresPosibles(TableroAbstract tablero, Point inicio) {
            List<Direction> valores = new ArrayList<>(4);
            for (Direction direction : Direction.values()) {
                boolean verificacion;
                if(direction.isAxis(Direction.Axis.NS)){
                    verificacion = !tablero.getEscaque(inicio.add(0, direction.getSign())).hasPieza();
                } else {
                    verificacion = !tablero.getEscaque(inicio.add(direction.getSign(), 0)).hasPieza();
                }
                if(verificacion){
                    valores.add(direction);
                }
            }
            return valores.toArray(Direction[]::new);
        }
    }
}
