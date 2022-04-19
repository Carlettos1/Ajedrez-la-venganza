package com.carlettos.game.visual.info;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.MiniBoard;
import com.carlettos.game.board.manager.Board;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlettos
 */
public class InfoDisplay extends JOptionPane {
    public static int showOptions(Board tablero, Point inicio, Object... options){
        return JOptionPane.showOptionDialog(null, 
                new MiniBoardDisplay(MiniBoard.getMiniFromTablero(tablero, inicio)),
                "Habilidad",
                OK_CANCEL_OPTION, QUESTION_MESSAGE, null, options, null);
    }
}
