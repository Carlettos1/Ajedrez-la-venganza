package ajedrez.carlettos.src.pieza.base;

import ajedrez.carlettos.src.estructura.base.Inexistente;
import ajedrez.carlettos.src.pieza.propiedad.Habilidad;
import ajedrez.carlettos.src.tablero.TableroManager;
import ajedrez.carlettos.src.tablero.escaque.Escaque;
import ajedrez.carlettos.src.tablero.jugador.Color;
import ajedrez.carlettos.src.util.Accion;
import ajedrez.carlettos.src.util.Par;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;

/**
 * Es la pieza que funciona como place-holder, no hace nada y es negra por defecto,
 * la única utilidad que tiene es evitar que un Escaque tenga una referencia null.
 * 
 * @author Carlos
 * 
 * @see Escaque
 * @see Inexistente
 */
public class Vacia extends Pieza{

    public Vacia() {
        super("Vacía", " ", Habilidad.NO_HABILIDAD, Color.NINGUNO);
    }

    @Override
    public boolean canMover(TableroManager tablero, Point inicio, Point final_) {
        return false;
    }

    @Override
    public boolean canComer(TableroManager tablero, Point inicio, Point final_) {
        return false;
    }

    @Override
    public List<Par<Point, Accion>> allAcciones(TableroManager tablero, Point seleccionado) {
        return new ArrayList<>();
    }

    @Override
    public void habilidad(TableroManager tablero, Point inicio, Point final_, String informacionExtra) {
        //nada
    }

    @Override
    public Par<Boolean, String> canUsarHabilidad(TableroManager tablero, Point inicio, Point final_, String informacionExtra) {
        return new Par<>(false, "Es vacía");
    }
}
