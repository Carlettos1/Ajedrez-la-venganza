package com.carlettos.game.gameplay.pattern.starting;

import java.util.Random;

import com.carlettos.game.board.IBaseBoard;
import com.carlettos.game.board.SquareBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;

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
        private int randomNumber;

        @Override
        public int getRandomNumber(int turn) {
            if (this.turn != turn) {
                this.turn = turn;
                this.randomNumber = rng.nextInt(8);
            }
            return randomNumber;
        }
    };

    @Override
    public default boolean match(IBaseBoard board, Point start, Point end) {
        int turn;
        if (board instanceof SquareBoard t) {
            turn = t.getClock().getTurn();
        } else {
            throw new IllegalArgumentException("Tablero no es instanceof Tablero");
        }

        return switch (this.getRandomNumber(turn)) {
            case 0 -> end.equals(start.add(0, 1)) || end.equals(start.add(0, 2));
            case 1 -> end.equals(start.add(1, 1)) || end.equals(start.add(2, 2));
            case 2 -> end.equals(start.add(1, 0)) || end.equals(start.add(2, 0));
            case 3 -> end.equals(start.add(1, -1)) || end.equals(start.add(2, -2));
            case 4 -> end.equals(start.add(0, -1)) || end.equals(start.add(0, -2));
            case 5 -> end.equals(start.add(-1, -1)) || end.equals(start.add(-2, -2));
            case 6 -> end.equals(start.add(-1, 0)) || end.equals(start.add(-2, 0));
            case 7 -> end.equals(start.add(-1, 1)) || end.equals(start.add(-2, 2));
            default -> throw new IllegalArgumentException("Numero random no esperado");
        };
    }

    /**
     * Gives a random number between 0 and 8. In a given turn it must give the same
     * number everytime.
     * 
     * @return a number between 0 and 8.
     */
    int getRandomNumber(int turn);
}
