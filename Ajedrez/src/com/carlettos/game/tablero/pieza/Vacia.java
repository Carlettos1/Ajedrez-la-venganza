package com.carlettos.game.tablero.pieza;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.habilidad.Habilidad;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.core.Point;

/**
 * Es la pieza que funciona como place-holder, no hace nada y es negra por
 * defecto, la única utilidad que tiene es evitar que un Escaque tenga una
 * referencia null.
 *
 * @author Carlos
 */
public class Vacia extends Pieza {

    public final static Habilidad NO_HABILIDAD = new NoHabilidad();

    public Vacia() {
        super("Vacía", " ", NO_HABILIDAD, Color.GRIS);
    }

    @Override
    public ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_) {
        return ActionResult.FAIL;
    }

    public static class NoHabilidad extends Habilidad {

        public NoHabilidad() {
            super("Habilidad nula", "No hace nada", 0, 0, "Ninguno");
        }

        @Override
        public ActionResult canUsar(Tablero tablero, Pieza pieza, Point inicio, Point final_, String informacionExtra) {
            return ActionResult.FAIL;
        }

        @Override
        public void usar(Tablero tablero, Pieza pieza, Point inicio, Point final_, String informacionExtra) {
        }
    }
}
