package com.carlettos.game.tablero.pieza;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.patron.PatronPeon;
import com.carlettos.game.tablero.pieza.patron.accion.IMover;
import com.carlettos.game.tablero.pieza.patron.accion.IComer;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;

/**
 *
 * @author Carlettos <M extends PatronPeon, C extends PatronPeon> 
 */
public abstract class AbstractPeon<M extends PatronPeon, C extends PatronPeon> extends Pieza implements IMover<M>, IComer<C> {
    protected final C patronComer;
    protected final M patronMover;

    public AbstractPeon(C patronComer, M patronMover, String nombre, String abreviacion, Habilidad habilidad, Color color) {
        super(nombre, abreviacion, habilidad, color, Tipo.BIOLOGICA, Tipo.TRANSPORTABLE);
        this.patronComer = patronComer;
        this.patronMover = patronMover;
    }

    @Override
    public ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            case COMER -> this.canComer(tablero, inicio, final_, patronComer);
            default -> ActionResult.FAIL;
        };
    }
}
