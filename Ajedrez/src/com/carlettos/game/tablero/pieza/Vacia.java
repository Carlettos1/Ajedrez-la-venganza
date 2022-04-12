package com.carlettos.game.tablero.pieza;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.habilidad.Habilidad;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.TableroAbstract;
import com.carlettos.game.tablero.propiedad.habilidad.InfoNinguna;
import com.carlettos.game.tablero.propiedad.habilidad.InfoGetter.HabilidadSinInfo;

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
        super("Vacía", " ", NO_HABILIDAD, Color.DEFAULT);
    }

    @Override
    public ActionResult can(Accion accion, TableroAbstract tablero, Point inicio, Point final_) {
        return ActionResult.FAIL;
    }

    public static class NoHabilidad extends Habilidad<Pieza, String, InfoNinguna> implements HabilidadSinInfo {

        public NoHabilidad() {
            super("Habilidad nula", "No hace nada", 0, 0, "Ninguno");
        }

        @Override
        public ActionResult canUsar(TableroAbstract tablero, Pieza pieza, Point inicio, InfoNinguna info) {
            return ActionResult.FAIL;
        }

        @Override
        public void usar(TableroAbstract tablero, Pieza pieza, Point inicio, InfoNinguna info) {
        }
    }
}
