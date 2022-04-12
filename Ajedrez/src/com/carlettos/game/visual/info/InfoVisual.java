package com.carlettos.game.visual.info;

import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.MiniTablero;
import com.carlettos.game.tablero.manager.Tablero;
import javax.swing.JOptionPane;

/**
 *
 * @author Carlettos
 */
public class InfoVisual extends JOptionPane {
    public static int showOptions(Tablero tablero, Point inicio, Object... options){
        return JOptionPane.showOptionDialog(null, 
                new MiniTableroVisual(MiniTablero.getMiniFromTablero(tablero, inicio)),
                "Habilidad",
                OK_CANCEL_OPTION, QUESTION_MESSAGE, null, options, null);
    }
}
