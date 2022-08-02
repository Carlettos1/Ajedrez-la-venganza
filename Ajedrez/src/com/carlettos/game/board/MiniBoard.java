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
public class MiniBoard extends AbstractSquareBoard {
    public AbstractClock clock;

    public MiniBoard(int columns, int rows, AbstractClock clock) {
        super(columns, rows);
        this.clock = clock;
    }

    public MiniBoard(AbstractClock clock) {
        this(5, 5, clock);
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
        for (int y = 0; y < mini.shape.y; y++) {
            for (int x = 0; x < mini.shape.x; x++) {
                int x0 = -radio + x + center.x;
                int y0 = -radio + y + center.y;
                Escaque esc;
                if (x0 < 0 || x0 >= board.shape.x) {
                    esc = new Escaque(new Point(-1, -1));
                } else if (y0 < 0 || y0 >= board.shape.y) {
                    esc = new Escaque(new Point(-1, -1));
                } else {
                    esc = board.getEscaque(new Point(x0, y0));
                }
                mini.getEscaque(new Point(x, y)).setPiece(esc.getPiece());
                mini.getEscaque(new Point(x, y)).setIsBuildable(esc.isBuildable());
                mini.getEscaque(new Point(x, y)).setIsMagic(esc.isMagic());
            }
        }
        return mini;
    }

    @Override
    public AbstractClock getClock() {
        return clock;
    }

    @Override
    public void tick() {}
}
