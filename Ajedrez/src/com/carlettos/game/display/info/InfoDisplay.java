package com.carlettos.game.display.info;

import com.carlettos.game.board.Board;
import com.carlettos.game.board.Escaque;
import com.carlettos.game.board.MiniBoard;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.helper.LogHelper;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlettos
 */
public class InfoDisplay extends JOptionPane {
    public static int showOptions(Board board, Point start, Object... options){
        return JOptionPane.showOptionDialog(null, 
                new MiniBoardDisplay(MiniBoard.fromBoard(board, start)),
                "Habilidad", //todo: use key-translation
                OK_CANCEL_OPTION, QUESTION_MESSAGE, null, options, null);
    }
    
    public static int showOptions(Board board, Escaque escaque) {
        var mini = new MiniBoardDisplay(MiniBoard.fromBoard(board, escaque.getPos()));
        var options = escaque.getPiece().getAbility().getPossibleValues(board, escaque.getPos());
        var formatted = new ArrayList<Object>();
        for (int i = 0; i < options.length; i++) {
            formatted.add(escaque.getPiece().getAbility().formatInfo(options[i]));
        }
        var a = JOptionPane.showInputDialog(null, mini,
                "Habilidad",
                QUESTION_MESSAGE, null, formatted.toArray(Object[]::new), null);
        return formatted.indexOf(a);
    }
}
