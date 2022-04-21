package com.carlettos.game.board.piece.pattern;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;

/**
 * The pattern of a piece is defined as "the way that a piece moves". It's a 
 * way to reduce the (for example) movement of the queen to rook + bishop 
 * without needing to rewrite code, and applicable to other pieces (like most
 * structures moves the same, so they use the same pattern).
 * But, its applicable to every other action, like take and attack. And, can be
 * used to certain abilities.
 *
 * @author Carlettos
 */
public interface Pattern {

    /**
     * Checks if the end point matches the pattern centered on the start point.
     *
     * @param board board to check the pattern.
     * @param start center of the pattern.
     * @param end point to match.
     * @return true if the start-end matches the pattern, false other case.
     */
    public boolean match(AbstractBoard board, Point start, Point end);
}
