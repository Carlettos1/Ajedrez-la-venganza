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
public interface ITake<P extends Pattern> {

    /**
     * Checks the pattern and the standard condition.
     * 
     * @param board board in which the take is happening.
     * @param start position of the piece.
     * @param info info containing a Point to where the take is being 
     * excecuted.
     * @param pattern pattern to check.
     * @return PASS if the conditions are both true. FAIL otherwise.
     * @throws IllegalArgumentException if the info is not an implementation of
     * {@literal Info<Point>}
     */
    public default ActionResult canTake(AbstractBoard board, Point start, Info info, P pattern) {
        if(info.getValue() instanceof Point p) {
            if (!this.checkComerCondition(board, start, p)) {
                return ActionResult.FAIL;
            }
            return ActionResult.fromBoolean(pattern.match(board, start, p));
        } else {
            throw new IllegalArgumentException("Info is not Info<Point>");
        }
    }

    /**
     * Checks the standard conditions.
     *
     * @param board board in which the take is happening.
     * @param start position of the piece.
     * @param end position to take.
     * @return true if the end pos has a piece, if is a different color of the 
     * piece at the start pos, and if the start piece hasn't moved.
     */
    public default boolean checkComerCondition(AbstractBoard board, Point start, Point end) {
        if (!board.getEscaque(end).hasPiece()) {
            return false;
        }
        if (board.getEscaque(end).getPiece().getColor().equals(board.getEscaque(start).getPiece().getColor())) {
            return false;
        }
        return !board.getEscaque(start).getPiece().isMoved();
    }
}
