package ajedrez.carlettos.src.pieza.piezas.clasica;

import ajedrez.carlettos.src.pieza.base.Pieza;
import ajedrez.carlettos.src.pieza.propiedad.EnumTipoPieza;
import ajedrez.carlettos.src.pieza.propiedad.Habilidad;
import ajedrez.carlettos.src.tablero.TableroManager;
import ajedrez.carlettos.src.tablero.jugador.Color;
import ajedrez.carlettos.src.util.Accion;
import ajedrez.carlettos.src.util.Par;
import java.awt.Point;
import java.util.List;

/**
 * Pieza fundamental del ajedrez, solo que levemente transformada. Se mueve 2
 * escaques hacia al frente y come, en su escaque más próximo, en sus dos
 * diagonales frontales. Su habilidad es la de coronar, no come al paso.
 *
 * @author Carlos
 */
public class Peon extends Pieza {

    /**
     * la habilidad default del peón, de utilidad por si necesita usarse en
     * otras piezas.
     */
    public static final Habilidad HABILIDAD_PEON = new Habilidad("Coronar",
            "Al estar en la última fila, puede transformarse en cualquier pieza de las que se permita.",
            0,
            0,
            "El nombre de la pieza a transformar. Ejemplo: Dama.");

    public Peon(Color color) {
        super("Peón", "P", HABILIDAD_PEON, color, EnumTipoPieza.BIOLOGICA, EnumTipoPieza.TRANSPORTABLE);
    }

    @Override
    public boolean canMover(TableroManager tablero, Point inicio, Point final_) {
        if (getColor().equals(Color.BLANCAS)) {
            Point puntoSiguiente = new Point(inicio.x, inicio.y + 1);
            Point puntoSubSiguiente = new Point(inicio.x, inicio.y + 2);
            if (final_.equals(puntoSiguiente)) {
                return !tablero.getEscaque(puntoSiguiente).hasPieza();
            } else if (final_.equals(puntoSubSiguiente)) {
                return !tablero.getEscaque(puntoSiguiente).hasPieza()
                        && !tablero.getEscaque(puntoSubSiguiente).hasPieza();
            }
        } else if (getColor().equals(Color.NEGRAS)) {
            Point puntoAnterior = new Point(inicio.x, inicio.y - 1);
            Point puntoAnteAnterior = new Point(inicio.x, inicio.y - 2);
            if (final_.equals(puntoAnterior)) {
                return !tablero.getEscaque(puntoAnterior).hasPieza();
            } else if (final_.equals(puntoAnteAnterior)) {
                return !tablero.getEscaque(puntoAnterior).hasPieza()
                        && !tablero.getEscaque(puntoAnteAnterior).hasPieza();
            }
        }
        return false;
    }

    @Override
    public boolean canComer(TableroManager tablero, Point inicio, Point final_) {
        if (getColor().equals(Color.BLANCAS)) {
            Point punto1 = new Point(inicio.x + 1, inicio.y + 1);
            Point punto2 = new Point(inicio.x - 1, inicio.y + 1);
            if (final_.equals(punto1)) {
                return tablero.getEscaque(punto1).hasPieza()
                        && !tablero.getEscaque(punto1).getPieza().getColor().equals(getColor());
            } else if (final_.equals(punto2)) {
                return tablero.getEscaque(punto2).hasPieza()
                        && !tablero.getEscaque(punto2).getPieza().getColor().equals(getColor());
            }
        } else if (getColor().equals(Color.NEGRAS)) {
            Point punto1 = new Point(inicio.x + 1, inicio.y - 1);
            Point punto2 = new Point(inicio.x - 1, inicio.y - 1);
            if (final_.equals(punto1)) {
                return tablero.getEscaque(punto1).hasPieza()
                        && !tablero.getEscaque(punto1).getPieza().getColor().equals(getColor());
            } else if (final_.equals(punto2)) {
                return tablero.getEscaque(punto2).hasPieza()
                        && !tablero.getEscaque(punto2).getPieza().getColor().equals(getColor());
            }
        }
        return false;
    }

    @Override
    public Par<Boolean, String> canUsarHabilidad(TableroManager tablero, Point inicio, Point final_, String informacionExtra) {
        if (getColor().equals(Color.BLANCAS)) {
            if (inicio.y + 1 == tablero.filas) {
                return new Par(true, "Está en el final");
            } else {
                return new Par(false, "No está en el final");
            }
        } else if (getColor().equals(Color.NEGRAS)) {
            if (inicio.y == 0) {
                return new Par(true, "Está en el final");
            } else {
                return new Par(false, "No está en el final");
            }
        }
        return new Par(false, "Descarte general");
    }

    @Override
    public void habilidad(TableroManager tablero, Point inicio, Point final_, String informacionExtra) {
        //TODO: hacer que sirva el habilidad();
        System.out.println("Felicidades, has coronado, toma una flor.");
    }

    @Override
    public List<Par<Point, Accion>> allAcciones(TableroManager tablero, Point seleccionado) {
        List<Par<Point, Accion>> myc = super.allAcciones(tablero, seleccionado);
        if (canUsarHabilidad(tablero, seleccionado, seleccionado, "owo").x) {
            myc.add(new Par<>(seleccionado, Accion.HABILIDAD));
        }
        return myc;
    }
}
