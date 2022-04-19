package com.carlettos.game.input;

import com.carlettos.game.core.Tuple;
import com.carlettos.game.board.manager.clock.Clock;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.visual.CardDisplay;
import com.carlettos.game.visual.EscaqueDisplay;
import com.carlettos.game.visual.BoardDisplay;
import com.carlettos.game.core.Point;
import java.awt.Component;
import java.awt.Container;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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
        CardDisplay card = (CardDisplay) (e.getSource());
        BoardDisplay board = DisplayHelper.getBoardDisplay(card);
        Component escaqueVisual = board.getComponentAt(e.getXOnScreen() - board.getX(), e.getYOnScreen() - board.getY());
        
        while (escaqueVisual instanceof Container) {
            Point componentLocation = DisplayHelper.getAbsoluteLocation(escaqueVisual);
            escaqueVisual = DisplayHelper.getComponentAt(escaqueVisual, e.getXOnScreen() - componentLocation.x,
                    e.getYOnScreen() - componentLocation.y);
        }
        
        if (escaqueVisual instanceof EscaqueDisplay escaque) {
            Clock clock = board.getClockDisplay().getClock();
            ActionResult can = card.getCard().canUse(escaque.getEscaque().getPos(),
                    board.getBoard(),
                    clock.getPlayerOfColor(card.getColor()));
            if (can.isPositive()) {
                card.getCard().use(escaque.getEscaque().getPos(),
                        board.getBoard(),
                        clock.getPlayerOfColor(card.getColor()));
                board.getManoVisual().rehacer();
                board.getClockDisplay().repaint();
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
