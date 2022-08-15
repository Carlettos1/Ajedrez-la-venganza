package com.carlettos.game.board;

import com.carlettos.game.board.clock.AbstractClock;
import com.carlettos.game.board.clock.FalseClock;
import com.carlettos.game.util.Point;

/**
 * It's a MiniBoard. A tiny board to show just a portion of the real Board. It
 * doesn't have any functionallity added.
 *
 * @see SquareBoard
 * @author Carlettos
 */
public class MiniBoard extends SquareBoard {
    public MiniBoard(AbstractClock clock) {
        super(5, 5, clock);
    }

    /**
     * Creates a 5x5 MiniBoard centered on the given point of an original Board.
     *
     * @param board  the board to copy.
     * @param center center of the 5x5 grid.
     * @return a MiniBoard with all the escaques being identical to the original
     *         board.
     */
    public static MiniBoard fromBoard(SquareBoard board, Point center) {
        MiniBoard mini = new MiniBoard(new FalseClock(board.getClock()));
        int radio = 2;
        mini.forEach(e -> {
            Point pos = e.getPos().add(-radio, -radio).add(center);
            if (board.contains(pos)) {
                mini.set(e.getPos(), board.get(pos));
            }
        });
        return mini;
    }

    @Override
    public AbstractClock getClock() {
        return clock;
    }

    @Override
    public void tick() {}
}
