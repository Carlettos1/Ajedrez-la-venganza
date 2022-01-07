package com.carlettos.game.tablero.pieza.nuevas;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.pieza.Vacia;
import com.carlettos.game.tablero.propiedad.Color;
import com.carlettos.game.tablero.propiedad.Tipo;

/**
 *
 * @author Carlos
 */
public class Cañon extends Pieza {

    public Cañon(Color color) {
        super("Cañón", "CAÑ", Vacia.NO_HABILIDAD, color, Tipo.ESTRUCTURA);
    }

    @Override
    public ActionResult can(Accion accion, Tablero tablero, Point inicio, Point final_) {
        switch (accion) {
            case ATACAR:
                return this.canAtacar(tablero, inicio, final_);
            case MOVER:
                return this.canMover(tablero, inicio, final_);
            default:
                return ActionResult.FAIL;
        }
    }

    protected ActionResult canMover(Tablero tablero, Point inicio, Point final_) {
        if (tablero.getEscaque(final_).hasPieza()) {
            return ActionResult.FAIL;
        }

        if (seHaMovidoEsteTurno()) {
            return ActionResult.FAIL;
        }

        if (final_.equals(inicio.add(1, 0))) {
            return ActionResult.PASS;
        }
        if (final_.equals(inicio.add(-1, 0))) {
            return ActionResult.PASS;
        }
        if (final_.equals(inicio.add(0, 1))) {
            return ActionResult.PASS;
        }
        if (final_.equals(inicio.add(0, -1))) {
            return ActionResult.PASS;
        }
        return ActionResult.FAIL;
    }

    protected ActionResult canAtacar(Tablero tablero, Point inicio, Point final_) {
        if (!tablero.getEscaque(final_).hasPieza()) {
            return ActionResult.FAIL;
        }

        if (tablero.getEscaque(final_).getPieza().getColor().equals(getColor())) {
            return ActionResult.FAIL;
        }

        if (seHaMovidoEsteTurno()) {
            return ActionResult.FAIL;
        }

        if (Math.abs(inicio.x - final_.x) > 3) {
            return ActionResult.FAIL;
        }
        if (Math.abs(inicio.y - final_.y) > 3) {
            return ActionResult.FAIL;
        }

        return ActionResult.PASS;
    }
}
