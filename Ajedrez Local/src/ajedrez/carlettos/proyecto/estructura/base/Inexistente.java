package ajedrez.carlettos.proyecto.estructura.base;

import ajedrez.carlettos.proyecto.pieza.propiedad.Habilidad;
import ajedrez.carlettos.proyecto.tablero.TableroManager;
import ajedrez.carlettos.proyecto.tablero.jugador.Color;
import ajedrez.carlettos.proyecto.util.Par;
import java.awt.Point;


/**
 * Es la estructura que funciona como place-holder, no hace nada y es negra por defecto,
 * la única utilidad que tiene es evitar que un Escaque tenga una referencia null.
 * 
 * @author Carlos
 * 
 * @see Escaque
 * @see Inexistente
 */
public class Inexistente extends Estructura {

    public Inexistente() {
        super(false, 1, 1, "Inexistente", " ", Habilidad.NO_HABILIDAD, Color.NINGUNO);
    }

    @Override
    public void habilidad(TableroManager tablero, Point inicio, Point final_, String informacionExtra) {
        //nada
    }

    @Override
    public Par<Boolean, String> canUsarHabilidad(TableroManager tablero, Point inicio, Point final_, String informacionExtra) {
        return new Par<>(false, "No existe");
    }
}