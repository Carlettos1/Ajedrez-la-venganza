package com.carlettos.game.visual.info;

import com.carlettos.game.board.manager.MiniBoard;
import com.carlettos.game.visual.EscaqueDisplay;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author Carlettos
 */
public class MiniBoardDisplay extends JPanel{
    private final EscaqueDisplay[][] grid;
    private final MiniBoard tablero;

    public MiniBoardDisplay(MiniBoard tablero) {
        super(new GridLayout(tablero.filas, tablero.columnas));
        this.grid = new EscaqueDisplay[tablero.filas][tablero.columnas];
        this.tablero = tablero;
        for (int y = this.tablero.filas - 1; y >= 0; y--) {
            for (int x = 0; x < this.tablero.columnas; x++) {
                EscaqueDisplay ev = new EscaqueDisplay(this.tablero.getEscaque(x, y));
                grid[y][x] = ev;
                this.add(ev);
            }
        }
    }
}
