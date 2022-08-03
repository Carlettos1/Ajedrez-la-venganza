package com.carlettos.game.display.info;

import java.util.ArrayList;

import javax.swing.JOptionPane;

import com.carlettos.game.board.Escaque;
import com.carlettos.game.board.MiniBoard;
import com.carlettos.game.board.SquareBoard;
import com.carlettos.game.util.Point;

/**
 *
 * @author Carlettos
 */
public class InfoDisplay extends JOptionPane {
    private static final long serialVersionUID = 2242482205630386186L;

    public static int showOptions(SquareBoard board, Point start, Object... options) {
        return JOptionPane.showOptionDialog(null, new MiniBoardDisplay(MiniBoard.fromBoard(board, start)), "Habilidad", // todo:
                                                                                                                        // use
                                                                                                                        // key-translation
                OK_CANCEL_OPTION, QUESTION_MESSAGE, null, options, null);
    }

    public static int showOptions(SquareBoard board, Escaque escaque) {
        var mini = new MiniBoardDisplay(MiniBoard.fromBoard(board, escaque.getPos()));
        var options = escaque.getPiece().getAbility().getValues(board, escaque.getPos());
        var formatted = new ArrayList<>();
        for (Object option : options) {
            formatted.add(escaque.getPiece().getAbility().formatInfo(option));
        }
        var a = JOptionPane.showInputDialog(null, mini, "Habilidad", QUESTION_MESSAGE, null,
                formatted.toArray(Object[]::new), null);
        return formatted.indexOf(a);
    }
}
