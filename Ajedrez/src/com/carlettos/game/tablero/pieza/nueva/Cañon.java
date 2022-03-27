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
public class Cañon extends Pieza implements IAtacar, IMover {

    public final Patron ATACAR;
    public final Patron MOVER;

    public Cañon(Color color) {
        super("Cañón", "CAÑ", Vacia.NO_HABILIDAD, color, Tipo.ESTRUCTURA);
        ATACAR = new PatronCañonAtacar() {};
        MOVER = new PatronEstructuraMover() {};
    }

    @Override
    public ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_) {
        return switch (accion) {
            case ATACAR -> this.canAtacar(tablero, inicio, final_);
            case MOVER -> this.canMover(tablero, inicio, final_);
            default -> ActionResult.FAIL;
        };
    }

    @Override
    public ActionResult canMover(Tablero tablero, Point inicio, Point final_) {
        return IMover.super.canMover(tablero, inicio, final_).isPositive()
                ? ActionResult.fromBoolean(MOVER.checkPatron(tablero, inicio, final_)) : ActionResult.FAIL;
    }

    @Override
    public ActionResult canAtacar(Tablero tablero, Point inicio, Point final_) {
        return IAtacar.super.canAtacar(tablero, inicio, final_).isPositive()
                ? ActionResult.fromBoolean(ATACAR.checkPatron(tablero, inicio, final_)) : ActionResult.FAIL;
    }

    @Override
    public boolean checkPatron(Tablero tablero, Point inicio, Point final_) {
        return true;
    }
}
