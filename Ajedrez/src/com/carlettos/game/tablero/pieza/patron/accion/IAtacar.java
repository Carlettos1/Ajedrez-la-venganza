package com.carlettos.game.tablero.pieza.patron.accion;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.TableroAbstract;
import com.carlettos.game.tablero.pieza.patron.Patron;

/**
 *
 * @author Carlettos
 */
public interface IAtacar<P extends Patron> {

    /**
     * 
     * @param tablero tablero en el que se quiere atacar.
     * @param inicio donde está la pieza.
     * @param final_ donde quiere atacar.
     * @return {@code ActionResult.PASS} o {@code ActionResult.FAIL}, 
     * dependiendo del caso.
     */
    public default ActionResult canAtacar(TableroAbstract tablero, Point inicio, Point final_, P patron){
        if (!this.checkAtacarCondition(tablero, inicio, final_)) {
            return ActionResult.FAIL;
        }
        return ActionResult.fromBoolean(patron.checkPatron(tablero, inicio, final_));
    }
    /**
     * Comprueba que pueda atacar, sin fijarse en un patron.
     *
     * @param tablero tablero en el que se quiere comer.
     * @param inicio donde está la pieza.
     * @param final_ donde quiere comer.
     * @return true si no se ha movido y la pieza que se quiere comer no es de
     * su mismo color. False si no.
     */
    public default boolean checkAtacarCondition(TableroAbstract tablero, Point inicio, Point final_) {
        if (!tablero.getEscaque(final_).hasPieza()) {
            return false;
        }
        if (tablero.getEscaque(final_).getPieza().getColor().equals(tablero.getEscaque(inicio).getPieza().getColor())) {
            return false;
        }
        return !tablero.getEscaque(inicio).getPieza().seHaMovidoEsteTurno();
    }
}
