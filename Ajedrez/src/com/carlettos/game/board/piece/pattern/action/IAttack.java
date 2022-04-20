package com.carlettos.game.board.piece.pattern.action;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Pattern;
import com.carlettos.game.board.property.ability.Info;

/**
 *
 * @author Carlettos
 */
public interface IAttack<P extends Pattern> {

    /**
     * 
     * @param tablero tablero en el que se quiere atacar.
     * @param inicio donde está la pieza.
     * @param final_ donde quiere atacar.
     * @return {@code ActionResult.PASS} o {@code ActionResult.FAIL}, 
     * dependiendo del caso.
     */
    public default ActionResult canAtacar(AbstractBoard tablero, Point inicio, Info info, P patron){
        if(info.getValue() instanceof Point p) {
            if (!this.checkAtacarCondition(tablero, inicio, p)) {
                return ActionResult.FAIL;
            }
            return ActionResult.fromBoolean(patron.match(tablero, inicio, p));
        } else {
            throw new IllegalArgumentException("Info is not Info<Point>");
        }
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
    public default boolean checkAtacarCondition(AbstractBoard tablero, Point inicio, Point final_) {
        if (!tablero.getEscaque(final_).hasPiece()) {
            return false;
        }
        if (tablero.getEscaque(final_).getPiece().getColor().equals(tablero.getEscaque(inicio).getPiece().getColor())) {
            return false;
        }
        return !tablero.getEscaque(inicio).getPiece().isMoved();
    }
}
