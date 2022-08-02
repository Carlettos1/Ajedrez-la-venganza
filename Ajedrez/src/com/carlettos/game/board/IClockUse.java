package com.carlettos.game.board;

import com.carlettos.game.board.clock.AbstractClock;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.enums.Color;

public interface IClockUse {
    AbstractClock getClock();

    /**
     * It has to tick the clock and the pieces.
     */
    void tick();

    /**
     * It notifies the clock that a movement had happen.
     */
    default void movement() {
        this.getClock().movement();
        if (getClock().getMovements() >= getClock().turnOf().getMaxMovements()) {
            this.tick();
        }
    }

    /**
     * Checks if the given piece can play, doesn't check if the piece has moved.
     *
     * @param piece piece to check.
     * @return true if the color of the piece can play, false other case.
     */
    default boolean canPlay(Piece piece) {
        return canPlay(piece.getColor());
    }

    /**
     * Checks if the given color can play.
     *
     * @param color color to check.
     * @return true if can play, false other case.
     */
    default boolean canPlay(Color color) {
        return getClock().turnOf().getColor().equals(color) && getClock().canPlay(getClock().turnOf());
    }
}
