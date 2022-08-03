package com.carlettos.game.gameplay.pattern.action;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.ActionResult;

/**
 *
 * @author Carlettos
 */
public interface IMove<P extends Pattern> {

    /**
     * Checks the pattern and the standard condition.
     *
     * @param board   board in which the move is happening.
     * @param start   position of the piece.
     * @param info    info containing a Point to where the move is being excecuted.
     * @param pattern pattern to check.
     * @return PASS if the conditions are both true. FAIL otherwise.
     * @throws IllegalArgumentException if the info is not an implementation of
     *                                  {@literal Info<Point>}
     */
    public default ActionResult canMove(AbstractSquareBoard board, Point start, Info info, P pattern) {
        if (info.getValue() instanceof Point p) {
            if (!this.checkMoveCondition(board, start, p)) { return ActionResult.FAIL; }
            return ActionResult.fromBoolean(pattern.match(board, start, p));
        } else {
            throw new IllegalArgumentException("Info is not Info<Point>");
        }
    }

    /**
     * Checks the standard conditions.
     *
     * @param board board in which the move is happening.
     * @param start position of the piece.
     * @param end   position to move.
     * @return true if the end pos doesn't have a piece, and if the start piece
     *         hasn't moved.
     */
    public default boolean checkMoveCondition(AbstractSquareBoard board, Point start, Point end) {
        return !(board.getEscaque(end).hasPiece() || board.getEscaque(start).getPiece().isMoved());
    }
}
