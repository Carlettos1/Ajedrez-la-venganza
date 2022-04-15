package com.carlettos.game.visual.info;

import com.carlettos.game.board.manager.MiniChessBoard;
import com.carlettos.game.visual.EscaqueVisual;
import java.awt.GridLayout;
import javax.swing.JPanel;

/**
 *
 * @author Carlettos
 */
public class MiniTableroVisual extends JPanel{
    private final EscaqueVisual[][] grid;
    private final MiniChessBoard tablero;

    public MiniTableroVisual(MiniChessBoard tablero) {
        super(new GridLayout(tablero.filas, tablero.columnas));
        this.grid = new EscaqueVisual[tablero.filas][tablero.columnas];
        this.tablero = tablero;
        for (int y = this.tablero.filas - 1; y >= 0; y--) {
            for (int x = 0; x < this.tablero.columnas; x++) {
                EscaqueVisual ev = new EscaqueVisual(this.tablero.getEscaque(x, y));
                grid[y][x] = ev;
                this.add(ev);
            }
        }
    }
}
