package com.carlettos.game.gameplay.pattern.action;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;

/**
 *
 * @author Carlettos
 */
public interface ITake {

    /**
     * Checks the pattern and the standard condition.
     *
     * @param board   board in which the take is happening.
     * @param start   position of the piece.
     * @param info    info containing a Point to where the take is being excecuted.
     * @param pattern pattern to check.
     * @return PASS if the conditions are both true. FAIL otherwise.
     * @throws IllegalArgumentException if the info is not an implementation of
     *                                  {@literal Info<Point>}
     */
    public default boolean canTake(AbstractBoard board, Point start, Info info, Pattern pattern) {
        if (info.getValue() instanceof Point p) {
            if (!this.checkTakeCondition(board, start, p)) { return false; }
            return (pattern.match(board, start, p));
        } else {
            throw new IllegalArgumentException("Info is not Info<Point>");
        }
    }

    /**
     * Checks the standard conditions.
     *
     * @param board board in which the take is happening.
     * @param start position of the piece.
     * @param end   position to take.
     * @return true if the end pos has a piece, if is a different color of the piece
     *         at the start pos, and if the start piece hasn't moved.
     */
    public default boolean checkTakeCondition(AbstractBoard board, Point start, Point end) {
        if (!board.get(end).hasPiece() || board.getPiece(end).getColor().equals(board.getPiece(start).getColor())) {
            return false;
        }
        return !board.getPiece(start).isMoved();
    }

    Pattern getTakePattern(AbstractBoard board, Point start);
}
