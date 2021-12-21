package com.carlettos.game.tablero.pieza;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.Par;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.manager.Tablero;
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
        super("Vacía", " ", Habilidad.NO_HABILIDAD, Color.GRIS);
    }

    @Override
    public boolean canMover(Tablero tablero, Point inicio, Point final_) {
        return false;
    }

    @Override
    public boolean canComer(Tablero tablero, Point inicio, Point final_) {
        return false;
    }

    @Override
    public List<Par<Point, Accion>> allAcciones(Tablero tablero, Point seleccionado) {
        return new ArrayList<>();
    }

    @Override
    public void habilidad(Tablero tablero, Point inicio, Point final_, String informacionExtra) {
        //nada
    }

    @Override
    public Par<Boolean, String> canUsarHabilidad(Tablero tablero, Point inicio, Point final_, String informacionExtra) {
        return new Par<>(false, "Es vacía");
    }
}
