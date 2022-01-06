package com.carlettos.game.tablero.pieza.clasica;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Par;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import java.awt.Point;
import java.util.List;

/**
 * Pieza fundamental del ajedrez, solo que levemente transformada. Se mueve 2
 * escaques hacia al frente y come, en su escaque más próximo, en sus dos
 * diagonales frontales. Su habilidad es la de coronar, no come al paso.
 *
 * @author Carlos
 */
public class Peon extends PiezaClasica {

    /**
     * la habilidad default del peón, de utilidad por si necesita usarse en
     * otras piezas.
     */
    public static final Habilidad<Peon> HABILIDAD_PEON = new HabilidadPeon<>();

    public Peon(Color color) {
        super("Peón", "P", HABILIDAD_PEON, color, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
    }

    @Override
    public ActionResult canMover(Tablero tablero, Point inicio, Point final_) {
        if (getColor().equals(Color.BLANCO)) {
            Point puntoSiguiente = new Point(inicio.x, inicio.y + 1);
            Point puntoSubSiguiente = new Point(inicio.x, inicio.y + 2);
            
            if (final_.equals(puntoSiguiente)) {
                boolean can = !tablero.getEscaque(puntoSiguiente).hasPieza();
                return can ? ActionResult.PASS : ActionResult.FAIL;
                
            } else if (final_.equals(puntoSubSiguiente)) {
                boolean can = !tablero.getEscaque(puntoSiguiente).hasPieza()
                        && !tablero.getEscaque(puntoSubSiguiente).hasPieza();
                return can ? ActionResult.PASS : ActionResult.FAIL;
            }
        } else if (getColor().equals(Color.NEGRO)) {
            Point puntoAnterior = new Point(inicio.x, inicio.y - 1);
            Point puntoAnteAnterior = new Point(inicio.x, inicio.y - 2);
            
            if (final_.equals(puntoAnterior)) {
                boolean can = !tablero.getEscaque(puntoAnterior).hasPieza();
                return can ? ActionResult.PASS : ActionResult.FAIL;
                
            } else if (final_.equals(puntoAnteAnterior)) {
                boolean can = !tablero.getEscaque(puntoAnterior).hasPieza()
                        && !tablero.getEscaque(puntoAnteAnterior).hasPieza();
                return can ? ActionResult.PASS : ActionResult.FAIL;
            }
        }
        //TODO: mover de otros colores
        return ActionResult.FAIL;
    }

    @Override
    public ActionResult canComer(Tablero tablero, Point inicio, Point final_) {
        if (getColor().equals(Color.BLANCO)) {
            Point punto1 = new Point(inicio.x + 1, inicio.y + 1);
            Point punto2 = new Point(inicio.x - 1, inicio.y + 1);
            
            if (final_.equals(punto1)) {
                boolean can = tablero.getEscaque(punto1).hasPieza()
                        && !tablero.getEscaque(punto1).getPieza().getColor().equals(getColor());
                return can ? ActionResult.PASS : ActionResult.FAIL;
                
            } else if (final_.equals(punto2)) {
                boolean can = tablero.getEscaque(punto2).hasPieza()
                        && !tablero.getEscaque(punto2).getPieza().getColor().equals(getColor());
                return can ? ActionResult.PASS : ActionResult.FAIL;
            }
        } else if (getColor().equals(Color.NEGRO)) {
            Point punto1 = new Point(inicio.x + 1, inicio.y - 1);
            Point punto2 = new Point(inicio.x - 1, inicio.y - 1);
            
            if (final_.equals(punto1)) {
                boolean can = tablero.getEscaque(punto1).hasPieza()
                        && !tablero.getEscaque(punto1).getPieza().getColor().equals(getColor());
                return can ? ActionResult.PASS : ActionResult.FAIL;
                
            } else if (final_.equals(punto2)) {
                boolean can = tablero.getEscaque(punto2).hasPieza()
                        && !tablero.getEscaque(punto2).getPieza().getColor().equals(getColor());
                return can ? ActionResult.PASS : ActionResult.FAIL;
            }
        }
        //TODO: comer de otros colores
        return ActionResult.FAIL;
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
