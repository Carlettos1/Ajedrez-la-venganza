package com.carlettos.game.board.manager;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.Escaque;

/**
 * It's a MiniBoard. A tiny board to show just a portion of the real Board. It
 * doesn't have any functionallity added.
 *
 * @see Board
 * @author Carlettos
 */
public class MiniBoard extends AbstractBoard {
    public MiniBoard(int columns, int rows) {
        super(columns, rows);
    }

    public MiniBoard() {
        this(5, 5);
    }
    
    /**
     * Creates a 5x5 MiniBoard centered on the given point of an original 
     * Board.
     *
     * @param board the board to copy.
     * @param center center of the 5x5 grid.
     * @return a MiniBoard with all the escaques being identical to the original
     * board.
     */
    public static MiniBoard fromBoard(Board board, Point center){
        MiniBoard mini = new MiniBoard();
        int radio = 2;
        for (int y = 0; y < mini.rows; y++) {
            for (int x = 0; x < mini.columns; x++) {
                int x0 = -radio + x + center.x;
                int y0 = -radio + y + center.y;
                Escaque esc;
                if(x0 < 0 || x0 >= board.columns){
                    esc = new Escaque(new Point(-1, -1));
                } else if(y0 < 0 || y0 >= board.rows){
                    esc = new Escaque(new Point(-1, -1));
                } else {
                    esc = board.getEscaque(x0, y0);
                }
                mini.getEscaque(x, y).setPiece(esc.getPiece());
                mini.getEscaque(x, y).setIsBuildable(esc.isBuildable());
                mini.getEscaque(x, y).setIsMagic(esc.isMagic());
            }
        }
        return mini;
    }
}
