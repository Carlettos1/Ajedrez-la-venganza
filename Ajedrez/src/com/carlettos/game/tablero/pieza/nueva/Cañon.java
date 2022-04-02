package com.carlettos.game.tablero.pieza.nueva;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.Vacia;
import com.carlettos.game.tablero.pieza.patron.Patron;
import com.carlettos.game.tablero.pieza.patron.accion.IAtacar;
import com.carlettos.game.tablero.pieza.patron.accion.IMover;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronCañonAtacar;
import com.carlettos.game.tablero.pieza.patron.nuevo.PatronEstructuraMover;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Tipo;

/**
 *
 * @author Carlos
 */
public class Cañon extends Pieza implements IAtacar<PatronCañonAtacar>, IMover<PatronEstructuraMover> {

    protected final PatronCañonAtacar patronAtacar;
    protected final PatronEstructuraMover patronMover;

    public Cañon(Color color) {
        super("Cañón", "CAÑ", Vacia.NO_HABILIDAD, color, Tipo.ESTRUCTURA);
        patronAtacar = new PatronCañonAtacar() {};
        patronMover = new PatronEstructuraMover() {};
    }

    @Override
    public ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_) {
        return switch (accion) {
            case ATACAR -> this.canAtacar(tablero, inicio, final_, patronAtacar);
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            default -> ActionResult.FAIL;
        };
    }
}
