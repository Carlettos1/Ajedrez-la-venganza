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
public interface IMove<P extends Pattern> {

    /**
     *
     * @param tablero tablero en el que se quiere mover.
     * @param inicio donde está la pieza.
     * @param final_ donde quiere mover.
     * @return {@code ActionResult.PASS} o {@code ActionResult.FAIL},
     * dependiendo del caso.
     */
    public default ActionResult canMover(AbstractBoard tablero, Point inicio, Info info, P patron) {
        if(info.getValue() instanceof Point p) {
            if (!this.checkMoverCondition(tablero, inicio, p)) {
                return ActionResult.FAIL;
            }
            return ActionResult.fromBoolean(patron.match(tablero, inicio, p));
        } else {
            throw new IllegalArgumentException("Info is not Info<Point>");
        }
    }

    /**
     * Comprueba que la pieza no se haya movido en este turno y que a donde
     * quiera moverse está vacío.
     *
     * @param tablero tablero en el que se quiere mover.
     * @param inicio donde está la pieza.
     * @param final_ donde quiere mover.
     * @return true si no se ha movido y está desocupado el escaque final, false
     * sino.
     */
    public default boolean checkMoverCondition(AbstractBoard tablero, Point inicio, Point final_) {
        return !(tablero.getEscaque(final_).hasPiece() || tablero.getEscaque(inicio).getPiece().isMoved());
    }
}
