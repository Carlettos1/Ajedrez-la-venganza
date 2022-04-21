package com.carlettos.game.visual.info;

import com.carlettos.game.board.manager.MiniBoard;
import com.carlettos.game.visual.EscaqueDisplay;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author Carlettos
 */
public class MiniBoardDisplay extends JPanel {
    private final EscaqueDisplay[][] grid;
    private final MiniBoard board;

    public MiniBoardDisplay(MiniBoard tablero) {
        super(new GridLayout(tablero.rows, tablero.columns));
        this.grid = new EscaqueDisplay[tablero.rows][tablero.columns];
        this.board = tablero;
        for (int y = this.board.rows - 1; y >= 0; y--) {
            for (int x = 0; x < this.board.columns; x++) {
                EscaqueDisplay ev = new EscaqueDisplay(this.board.getEscaque(x, y));
                grid[y][x] = ev; //TODO: not display -1 -1 Escaques.
                this.add(ev);
            }
        }
    }
}
