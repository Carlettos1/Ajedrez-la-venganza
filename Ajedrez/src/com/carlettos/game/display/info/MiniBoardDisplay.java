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
        super(new GridLayout(tablero.getShape().getBoundingRectangle().y, tablero.getShape().getBoundingRectangle().x));
        this.grid = new EscaqueDisplay[tablero.getShape().getBoundingRectangle().y][tablero.getShape()
                .getBoundingRectangle().x];
        this.board = tablero;
        for (int y = this.board.getShape().getBoundingRectangle().y - 1; y >= 0; y--) {
            for (int x = 0; x < this.board.getShape().getBoundingRectangle().x; x++) {
                EscaqueDisplay ev = new EscaqueDisplay(this.board.get(new Point(x, y)));
                grid[y][x] = ev; // TODO: not display -1 -1 Escaques.
                this.add(ev);
            }
        }
    }
}
