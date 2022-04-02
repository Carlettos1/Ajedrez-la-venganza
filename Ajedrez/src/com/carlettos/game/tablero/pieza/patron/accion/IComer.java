package com.carlettos.game.tablero.pieza.patron.accion;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.patron.Patron;

/**
 *
 * @author Carlettos
 */
public interface IComer<P extends Patron> {

    /**
     *
     * @param tablero tablero en el que se quiere comer.
     * @param inicio donde está la pieza.
     * @param final_ donde quiere comer.
     * @return {@code ActionResult.PASS} o {@code ActionResult.FAIL},
     * dependiendo del caso.
     */
    public default ActionResult canComer(Tablero tablero, Point inicio, Point final_, P patron) {
        if (!this.checkComerCondition(tablero, inicio, final_)) {
            return ActionResult.FAIL;
        }
        return ActionResult.fromBoolean(patron.checkPatron(tablero, inicio, final_));
    }

    /**
     * Comprueba que pueda comer, sin fijarse en un patron.
     *
     * @param tablero tablero en el que se quiere comer.
     * @param inicio donde está la pieza.
     * @param final_ donde quiere comer.
     * @return true si no se ha movido y la pieza que se quiere comer no es de
     * su mismo color. False si no.
     */
    public default boolean checkComerCondition(Tablero tablero, Point inicio, Point final_) {
        if (!tablero.getEscaque(final_).hasPieza()) {
            return false;
        }
        if (tablero.getEscaque(final_).getPieza().getColor().equals(tablero.getEscaque(inicio).getPieza().getColor())) {
            return false;
        }
        return !tablero.getEscaque(inicio).getPieza().seHaMovidoEsteTurno();
    }
}
