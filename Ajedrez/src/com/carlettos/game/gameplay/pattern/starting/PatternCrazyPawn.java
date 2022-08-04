package com.carlettos.game.gameplay.pattern.starting;

import java.util.Random;

import com.carlettos.game.board.IBaseBoard;
import com.carlettos.game.board.IClockUse;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Direction.SubDirection;

/**
 *
 * @author Carlettos
 */
public interface PatternCrazyPawn extends Pattern {

    /**
     * Standard pattern, so that all the crazy pawns are coordinated (with the same
     * rng).
     */
    public static final PatternCrazyPawn STANDARD_PATTERN = new PatternCrazyPawn() {
        private static final Random rng = new Random();
        private int turn = -1;
        private SubDirection randomNumber;

        @Override
        public SubDirection getRandomSubDirection(int turn) {
            if (this.turn != turn) {
                this.turn = turn;
                this.randomNumber = SubDirection.values()[rng.nextInt(8)];
            }
            return randomNumber;
        }
    };

    @Override
    public default boolean match(IBaseBoard board, Point start, Point end) {
        if (!(board instanceof IClockUse)) { // TODO: option to just stablish the direction and bypass this throw
            throw new IllegalArgumentException("Needs to be a clocked board to use this pattern");
        }
        var subDir = this.getRandomSubDirection(((IClockUse) board).getClock().getTurn()).toPoint();
        return end.equals(subDir) || end.equals(subDir.scale(2));
    }

    /**
     * Gives a random number between 0 and 8. In a given turn it must give the same
     * number everytime.
     *
     * @return a number between 0 and 8.
     */
    SubDirection getRandomSubDirection(int turn);
}
