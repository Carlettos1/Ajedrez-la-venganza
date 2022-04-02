package com.carlettos.game.tablero.pieza.clasica;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Par;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronAlfil;
import java.util.List;

/**
 *
 * @author Carlos
 */
public class Alfil extends PiezaSimple<PatronAlfil>{

    public static final Habilidad<Alfil> HABILIDAD_ALFIL = new HabilidadAlfil<>();

    public Alfil(Color color) {
        super("Alfil", "A", HABILIDAD_ALFIL, color, new PatronAlfil(){}, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
    }

    @Override
    public List<Par<Point, Accion>> allAcciones(Tablero tablero, Point seleccionado) {
        //myc viene de mover y comer
        List<Par<Point, Accion>> myc = super.allAcciones(tablero, seleccionado);
        if (this.getHabilidad().canUsar(tablero, this, seleccionado, seleccionado, "N").isPositive()) {
            myc.add(new Par<>(new Point(seleccionado.x, seleccionado.y + 1), Accion.HABILIDAD));
        }
        if (this.getHabilidad().canUsar(tablero, this, seleccionado, seleccionado, "E").isPositive()) {
            myc.add(new Par<>(new Point(seleccionado.x + 1, seleccionado.y), Accion.HABILIDAD));
        }
        if (this.getHabilidad().canUsar(tablero, this, seleccionado, seleccionado, "S").isPositive()) {
            myc.add(new Par<>(new Point(seleccionado.x, seleccionado.y - 1), Accion.HABILIDAD));
        }
        if (this.getHabilidad().canUsar(tablero, this, seleccionado, seleccionado, "W").isPositive()) {
            myc.add(new Par<>(new Point(seleccionado.x - 1, seleccionado.y), Accion.HABILIDAD));
        }
        return myc;
    }

    public static class HabilidadAlfil<P extends Pieza> extends Habilidad<P> {
        public HabilidadAlfil() {
            super("Cambio de Color",
                    "El alfil cambia de color, pudiendo moverse una casilla en cualquiera de las 4 direcciones cardinales.",
                    2,
                    0,
                    "La dirección cardinal hacia dónde se mueve el alfil (NESW).");
        }

        @Override
        public ActionResult canUsar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            if (pieza.getCdActual() > 0) {
                return ActionResult.FAIL;
            }

            if (pieza.seHaMovidoEsteTurno()) {
                return ActionResult.FAIL;
            }

            if (informacionExtra == null) {
                return ActionResult.FAIL;
            }

            boolean verificacion;

            switch (informacionExtra) {
                case "N" -> verificacion = !tablero.getEscaque(inicio.x, inicio.y + 1).hasPieza();
                case "E" -> verificacion = !tablero.getEscaque(inicio.x + 1, inicio.y).hasPieza();
                case "S" -> verificacion = !tablero.getEscaque(inicio.x, inicio.y - 1).hasPieza();
                case "W" -> verificacion = !tablero.getEscaque(inicio.x - 1, inicio.y).hasPieza();
                default -> {
                        return ActionResult.FAIL;
                }
            }
            return verificacion ? ActionResult.PASS : ActionResult.FAIL;
        }

        @Override
        public void usar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            switch (informacionExtra) {
                case "N" -> {
                    tablero.getEscaque(inicio.x, inicio.y + 1).setPieza(pieza);
                    tablero.getEscaque(inicio).quitarPieza();
                }
                case "E" -> {
                    tablero.getEscaque(inicio.x + 1, inicio.y).setPieza(pieza);
                    tablero.getEscaque(inicio).quitarPieza();
                }
                case "S" -> {
                    tablero.getEscaque(inicio.x, inicio.y - 1).setPieza(pieza);
                    tablero.getEscaque(inicio).quitarPieza();
                }
                case "W" -> {
                    tablero.getEscaque(inicio.x - 1, inicio.y).setPieza(pieza);
                    tablero.getEscaque(inicio).quitarPieza();
                }
            }
            //TODO: cambiar cd
        }
    }
}
