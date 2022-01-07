package com.carlettos.game.tablero.pieza.clasica;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.core.Point;

/**
 *
 * @author Carlos
 */
public abstract class PiezaClasica extends Pieza {

    public PiezaClasica(String nombre, String abreviacion, Habilidad habilidad, Color color, Tipo... tipos) {
        super(nombre, abreviacion, habilidad, color, tipos);
    }

    @Override
    public final ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_) {
        switch (accion) {
            case COMER:
                return canComer(tablero, inicio, final_);
            case MOVER:
                return canMover(tablero, inicio, final_);
            default:
                return ActionResult.FAIL;
        }
    }

    protected abstract ActionResult canComer(Tablero tablero, Point inicio, Point final_);

    protected abstract ActionResult canMover(Tablero tablero, Point inicio, Point final_);
}
