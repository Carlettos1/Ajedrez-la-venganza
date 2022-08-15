package com.carlettos.game.gameplay.pattern;

import java.util.function.Predicate;

import com.carlettos.game.board.Escaque;
import com.carlettos.game.board.IBaseBoard;
import com.carlettos.game.util.Point;

/**
 * The pattern of a piece is defined as "the way that a piece moves". It's a way
 * to reduce the (for example) movement of the queen to rook + bishop without
 * needing to rewrite code, and applicable to other pieces (like most structures
 * moves the same, so they use the same pattern). But, its applicable to every
 * other action, like take and attack. And, can be used to certain abilities.
 *
 * @author Carlettos
 */
@FunctionalInterface
public interface Pattern {

    /**
     * Checks if the end point matches the pattern centered on the start point.
     *
     * @param board board to check the pattern.
     * @param start center of the pattern.
     * @param end   point to match.
     *
     * @return true if the start-end matches the pattern, false other case.
     */
    public boolean match(IBaseBoard board, Point start, Point end);

    /**
     * Turns this pattern into a Predicate. The end Point of the match will be the
     * point escaque.getPos() provided by the predicate.test(escaque).
     *
     * @param board to match this pattern.
     * @param start start point of the match.
     *
     * @return a predicate with the match method of this Pattern.
     *
     * @author Carlettos
     */
    default Predicate<Escaque> toPredicate(IBaseBoard board, Point start) {
        return escaque -> this.match(board, start, escaque.getPos());
    }
}
