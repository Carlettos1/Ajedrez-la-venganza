package com.carlettos.game.input;

import com.carlettos.game.core.helper.DisplayHelper;
import com.carlettos.game.core.Tuple;
import com.carlettos.game.board.manager.clock.Clock;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.display.board.CardDisplay;
import com.carlettos.game.display.board.EscaqueDisplay;
import com.carlettos.game.display.board.BoardDisplay;
import com.carlettos.game.core.Point;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import javax.swing.SwingUtilities;

/**
 * Listener implementation class.
 * 
 * @author Carlos
 */
public class MouseCard implements MouseListener {
    private static final MouseCard LISTENER = new MouseCard();
    
    private MouseCard(){
    }
    
    public static MouseCard get(){
        return LISTENER;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        var card = (CardDisplay) (e.getSource());
        var board = BoardDisplay.getInstance();
        var frame = board.getRootPane().getParent();
        var ev = DisplayHelper.getLastComponentAt(frame, e.getXOnScreen(), e.getYOnScreen());
        
        if (ev instanceof EscaqueDisplay escaque) {
            var clock = board.getClockDisplay().getClock();
            var can = card.getCard().canUse(escaque.getEscaque().getPos(),
                    board.getBoard(),
                    clock.getPlayerOfColor(card.getColor()));
            if (can.isPositive()) {
                card.getCard().use(escaque.getEscaque().getPos(),
                        board.getBoard(),
                        clock.getPlayerOfColor(card.getColor()));
                board.getManoVisual().rehacer();
                board.repaint();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
