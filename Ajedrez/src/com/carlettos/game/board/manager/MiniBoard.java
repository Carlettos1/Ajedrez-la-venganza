package com.carlettos.game.board.manager;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.Escaque;

/**
 *
 * @author Carlettos
 */
public class MiniBoard extends AbstractBoard{
    public MiniBoard(int columnas, int filas) {
        super(columnas, filas);
    }

    public MiniBoard() {
        this(5, 5);
    }
    
    public static MiniBoard getMiniFromTablero(Board tablero, Point centro){
        MiniBoard mini = new MiniBoard();
        int radio = 2;
        for (int y = 0; y < mini.filas; y++) {
            for (int x = 0; x < mini.columnas; x++) {
                int x0 = -radio + x + centro.x;
                int y0 = -radio + y + centro.y;
                Escaque esc;
                if(x0 < 0 || x0 >= tablero.columnas){
                    esc = new Escaque(new Point());
                } else if(y0 < 0 || y0 >= tablero.filas){
                    esc = new Escaque(new Point());
                } else {
                    esc = tablero.getEscaque(x0, y0);
                }
                mini.getEscaque(x, y).setPiece(esc.getPiece());
                if (esc.isConstruible()) {
                    mini.getEscaque(x, y).setIsConstruible(true);
                }
                if(esc.isFuenteDeMagia()){
                    mini.getEscaque(x, y).setIsFuenteDeMagia(true);
                }
            }
        }
        return mini;
    }
}
