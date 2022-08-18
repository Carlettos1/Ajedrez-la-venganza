package com.carlettos.game.gameplay.pattern.action;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;

/**
 *
 * @author Carlettos
 */
public interface IAttack {

    /**
     * Checks the pattern and the standard condition.
     *
     * @param board   board in which the attack is happening.
     * @param start   position of the piece.
     * @param info    info containing a Point to where the attack is being
     *                excecuted.
     * @param pattern pattern to check.
     * @return PASS if the conditions are both true. FAIL otherwise.
     * @throws IllegalArgumentException if the info is not an implementation of
     *                                  {@literal Info<Point>}
     */
    public default boolean canAttack(AbstractBoard board, Point start, Info info, Pattern pattern) {
        if (info.getValue() instanceof Point p) {
            if (!this.checkAttackConditions(board, start, p)) { return false; }
            return (pattern.match(board, start, p));
        } else {
            throw new IllegalArgumentException("Info is not Info<Point>");
        }
    }

    /**
     * Checks the standard conditions.
     *
     * @param board board in which the attack is happening.
     * @param start position of the piece.
     * @param end   position to attack.
     * @return true if the end pos has a piece, if is a different color of the piece
     *         at the start pos, and if the start piece hasn't moved.
     */
    public default boolean checkAttackConditions(AbstractBoard board, Point start, Point end) {
        if (!board.get(end).hasPiece() || board.getPiece(end).getColor().equals(board.getPiece(start).getColor())) {
            return false;
        }
        return !board.getPiece(start).isMoved();
    }

    Pattern getAttackPattern(AbstractBoard board, Point start);
}
