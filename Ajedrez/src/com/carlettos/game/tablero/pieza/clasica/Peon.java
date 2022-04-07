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
public class Peon extends Pieza implements IComer<PatronPeonComer>, IMover<PatronPeonMover> {

    /**
     * la habilidad default del peón, de utilidad por si necesita usarse en
     * otras piezas.
     */
    public static final Habilidad<Peon> HABILIDAD_PEON = new HabilidadPeon<>();
    protected final PatronPeonComer patronComer;
    protected final PatronPeonMover patronMover;

    public Peon(Color color) {
        super("Peón", "P", HABILIDAD_PEON, color, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
        patronComer = (PatronPeonComer) () -> color;
        patronMover = (PatronPeonMover) () -> color;
    }

    @Override
    public ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_) {
        return switch (accion) {
            case MOVER ->
                this.canMover(tablero, inicio, final_, patronMover);
            case COMER ->
                this.canComer(tablero, inicio, final_, patronComer);
            default ->
                ActionResult.FAIL;
        };
    }

    @Override
    public List<Par<Point, Accion>> allAcciones(Tablero tablero, Point seleccionado) {
        //todo: all acciones llama al super...
        List<Par<Point, Accion>> myc = super.allAcciones(tablero, seleccionado);
        if (this.getHabilidad().canUsar(tablero, this, seleccionado, seleccionado, "owo").isPositive()) {
            myc.add(new Par<>(seleccionado, Accion.HABILIDAD));
        }
        return myc;
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
