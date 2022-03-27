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
import com.carlettos.game.tablero.pieza.patron.Patron;
import com.carlettos.game.tablero.pieza.patron.accion.IComer;
import com.carlettos.game.tablero.pieza.patron.accion.IMover;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronPeonComer;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronPeonMover;
import java.util.List;

/**
 * Pieza fundamental del ajedrez, solo que levemente transformada. Se mueve 2
 * escaques hacia al frente y come, en su escaque más próximo, en sus dos
 * diagonales frontales. Su habilidad es la de coronar, no come al paso.
 *
 * @author Carlos
 */
public class Peon extends PiezaClasica implements IComer, IMover{

    /**
     * la habilidad default del peón, de utilidad por si necesita usarse en
     * otras piezas.
     */
    public static final Habilidad<Peon> HABILIDAD_PEON = new HabilidadPeon<>();
    public final Patron COMER;
    public final Patron MOVER;

    public Peon(Color color) {
        super("Peón", "P", HABILIDAD_PEON, color, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
        COMER = (PatronPeonComer) () -> color;
        MOVER = (PatronPeonMover) () -> color;
    }

    @Override
    public ActionResult canMover(Tablero tablero, Point inicio, Point final_) {
        if (!this.checkMoverCondition(tablero, inicio, final_)) {
            return ActionResult.FAIL;
        }
        return ActionResult.fromBoolean(MOVER.checkPatron(tablero, inicio, final_));
    }

    @Override
    public ActionResult canComer(Tablero tablero, Point inicio, Point final_) {
        if (!this.checkComerCondition(tablero, inicio, final_)) {
            return ActionResult.FAIL;
        }
        return ActionResult.fromBoolean(COMER.checkPatron(tablero, inicio, final_));
    }

    @Override
    public List<Par<Point, Accion>> allAcciones(Tablero tablero, Point seleccionado) {
        //todo: all acciones llama al super...
        List<Par<Point, Accion>> myc = super.allAcciones(tablero, seleccionado);
        if (this.habilidad.canUsar(tablero, this, seleccionado, seleccionado, "owo").isPositive()) {
            myc.add(new Par<>(seleccionado, Accion.HABILIDAD));
        }
        return myc;
    }

    @Override
    @Deprecated
    public boolean checkPatron(Tablero tablero, Point inicio, Point final_) {
        return true; // UNUSED
    }

    public static class HabilidadPeon<P extends Pieza> extends Habilidad<P> {

        public HabilidadPeon() {
            super("Coronar",
                    "Al estar en la última fila, puede transformarse en cualquier pieza de las que se permita.",
                    0,
                    0,
                    "El nombre de la pieza a transformar. Ejemplo: Dama.");
        }

        @Override
        public ActionResult canUsar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            if (pieza.getColor().equals(Color.BLANCO)) {
                if (inicio.y + 1 == tablero.filas) {
                    return ActionResult.PASS;
                } else {
                    return ActionResult.FAIL;
                }
            } else if (pieza.getColor().equals(Color.NEGRO)) {
                if (inicio.y == 0) {
                    return ActionResult.PASS;
                } else {
                    return ActionResult.FAIL;
                }
            }
            //todo: verificar la información
            //todo: poder coronar con cualquier color
            System.err.println("INTENTANDO CORONAR CON OTRO COLOR");
            return ActionResult.FAIL;
        }

        @Override
        public void usar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra) {
            //TODO: hacer que sirva el habilidad();
            System.out.println("Felicidades, has coronado, toma una flor.");
        }
    }
}
