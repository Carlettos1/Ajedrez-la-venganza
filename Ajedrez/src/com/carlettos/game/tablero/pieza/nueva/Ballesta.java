package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.Vacia;

import com.carlettos.game.tablero.pieza.patron.accion.IMover;
import com.carlettos.game.tablero.pieza.patron.accion.IAtacar;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronBallestaAtacar;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronEstructuraMover;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Habilidad;
import com.carlettos.game.tablero.propiedad.Tipo;

/**
 *
 * @author Carlettos
 */
public class Ballesta extends Pieza implements IMover<PatronEstructuraMover>, IAtacar<PatronBallestaAtacar> {
    protected final PatronEstructuraMover patronMover;
    protected final PatronBallestaAtacar patronAtacar;

    public Ballesta(Color color) {
        super("Ballesta", "BA", Vacia.NO_HABILIDAD, color, Tipo.ESTRUCTURA);
        patronMover = new PatronEstructuraMover() {};
        patronAtacar = new PatronBallestaAtacar() {};
    }

    @Override
    public ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            case ATACAR -> this.canAtacar(tablero, inicio, final_, patronAtacar);
            default -> ActionResult.FAIL;
        };
    }
}
