package com.carlettos.game.display.info;

import com.carlettos.game.board.Board;
import com.carlettos.game.board.MiniBoard;
import com.carlettos.game.util.Point;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlettos
 */
public class InfoDisplay extends JOptionPane {
    public static int showOptions(Board board, Point start, Object... options){
        return JOptionPane.showOptionDialog(null, 
                new MiniBoardDisplay(MiniBoard.fromBoard(board, start)),
                "Habilidad",
                OK_CANCEL_OPTION, QUESTION_MESSAGE, null, options, null);
    }
}
