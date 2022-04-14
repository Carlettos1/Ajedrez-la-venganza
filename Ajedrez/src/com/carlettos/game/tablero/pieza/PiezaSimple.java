package com.carlettos.game.tablero.pieza;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Tipo;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.AbstractTablero;
import com.carlettos.game.tablero.pieza.patron.Patron;
import com.carlettos.game.tablero.pieza.patron.accion.IComer;
import com.carlettos.game.tablero.pieza.patron.accion.IMover;
import com.carlettos.game.tablero.propiedad.habilidad.Habilidad;

/**
 *
 * @author Carlos
 */
public abstract class PiezaSimple<P extends Patron> extends Pieza implements IComer<P>, IMover<P>{
    protected final P patron;

    public PiezaSimple(String nombre, String abreviacion, Habilidad habilidad, Color color, P patron, Tipo... tipos) {
        super(nombre, abreviacion, habilidad, color, tipos);
        this.patron = patron;
    }

    /**
     * @@inheritDoc
     */
    @Override
    public final ActionResult can(Accion accion, AbstractTablero tablero, Point inicio, Point final_) {
        return switch (accion) {
            case COMER -> this.canComer(tablero, inicio, final_, patron);
            case MOVER -> this.canMover(tablero, inicio, final_, patron);
            default -> ActionResult.FAIL;
        };
    }
}
