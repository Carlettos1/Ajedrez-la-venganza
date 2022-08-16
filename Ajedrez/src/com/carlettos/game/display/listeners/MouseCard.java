package com.carlettos.game.display.listeners;

import java.awt.Component;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.carlettos.game.display.board.BoardDisplay;
import com.carlettos.game.display.board.EscaqueDisplay;
import com.carlettos.game.util.helper.DisplayHelper;
import com.carlettos.game.util.helper.LogHelper;

/**
 * Listener implementation class.
 *
 * @author Carlos
 */
public class MouseCard implements MouseListener {
    private static final MouseCard LISTENER = new MouseCard();

    private MouseCard() {}

    public static MouseCard get() {
        return LISTENER;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // doesn't need it
    }

    @Override
    public void mousePressed(MouseEvent e) {
        // doesn't need it
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        var card = DisplayHelper.getCardDisplay((Component) e.getSource());
        if (card == null) {
            LogHelper.LOG.info("null card display for: " + e);
            return;
        }
        var board = BoardDisplay.getInstance();
        var frame = board.getRootPane().getParent();
        var ev = DisplayHelper.getLastComponentAt(frame, e.getXOnScreen(), e.getYOnScreen());

        if (ev instanceof EscaqueDisplay escaque) {
            var clock = board.getClockDisplay().getClock();
            var can = card.getCard().canUse(escaque.getEscaque().getPos(), board.getBoard(),
                    clock.getPlayerOfColor(card.getColor()));
            if (can) {
                card.getCard().use(escaque.getEscaque().getPos(), board.getBoard(),
                        clock.getPlayerOfColor(card.getColor()));
                board.getHandDisplay().redo();
                board.repaint();
                MousePiece.get().setSelected(null);
                board.offAll();
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // doesn't need it
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // doesn't need it
    }
}
