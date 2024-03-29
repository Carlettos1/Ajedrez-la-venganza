package com.carlettos.game.gameplay.pattern.action;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;

/**
 *
 * @author Carlettos
 */
public interface IMove {

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
    default boolean canMove(AbstractBoard board, Point start, Info info, Pattern pattern) {
        if (info.getValue() instanceof Point p) {
            if (!this.checkMoveCondition(board, start, p)) { return false; }
            return (pattern.match(board, start, p));
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
    default boolean checkMoveCondition(AbstractBoard board, Point start, Point end) {
        return !(board.get(end).hasPiece() || board.getPiece(start).isMoved());
    }

    Pattern getMovePattern(AbstractBoard board, Point start);
}
