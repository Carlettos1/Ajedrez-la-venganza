package com.carlettos.game.display.info;

import java.awt.GridLayout;

import javax.swing.JPanel;

import com.carlettos.game.board.MiniBoard;
import com.carlettos.game.display.board.EscaqueDisplay;
import com.carlettos.game.util.Point;

/**
 *
 * @author Carlettos
 */
public class MiniBoardDisplay extends JPanel {
    private static final long serialVersionUID = -6114779705263991014L;
    private final EscaqueDisplay[][] grid;
    // todo: transient?
    private final transient MiniBoard board;

    public MiniBoardDisplay(MiniBoard tablero) {
        super(new GridLayout(tablero.shape.y, tablero.shape.x));
        this.grid = new EscaqueDisplay[tablero.shape.y][tablero.shape.x];
        this.board = tablero;
        for (int y = this.board.shape.y - 1; y >= 0; y--) {
            for (int x = 0; x < this.board.shape.x; x++) {
                EscaqueDisplay ev = new EscaqueDisplay(this.board.getEscaque(new Point(x, y)));
                grid[y][x] = ev; // TODO: not display -1 -1 Escaques.
                this.add(ev);
            }
        }
    }
}
