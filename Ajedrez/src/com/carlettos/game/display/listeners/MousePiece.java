package com.carlettos.game.display.listeners;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.Escaque;
import com.carlettos.game.display.board.BoardDisplay;
import com.carlettos.game.display.board.EscaqueDisplay;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;

/**
 * Listener implementation class.
 *
 * @author Carlos
 */
public class MousePiece implements MouseListener {

    private static final MousePiece LISTENER = new MousePiece();

    private MousePiece() {
    }

    public static MousePiece get() {
        return LISTENER;
    }

    private EscaqueDisplay selected;
    
    public EscaqueDisplay getSelected() {
        return selected;
    }
    
    public void setSelected(EscaqueDisplay selected) {
        this.selected = selected;
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        // doesn't need it
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (selected == null) {
            selected = (EscaqueDisplay) e.getSource();
            Escaque escaque = selected.getEscaque();
            if (!escaque.hasPiece()) {
                selected = null;
                return;
            }
            var tv = BoardDisplay.getInstance();
            var board = tv.getBoard();

            if (!board.canPlay(selected.getEscaque().getPiece())) {
                selected = null;
                return;
            }

            markActions(tv, board, escaque.getPos());
        } else {
            var objetive = (EscaqueDisplay) e.getSource();
            var eSelected = selected.getEscaque();
            var eObjetive = objetive.getEscaque();
            var tv = BoardDisplay.getInstance();
            var board = tv.getBoard();
            
            //the order matters, first tries to move, then to attack, then to take.
            if (board.tryTo(Action.MOVE, eSelected.getPos(), eObjetive.getPos().toInfo()).isPositive()
                    || board.tryTo(Action.ATTACK, eSelected.getPos(), eObjetive.getPos().toInfo()).isPositive()
                    || board.tryTo(Action.TAKE, eSelected.getPos(), eObjetive.getPos().toInfo()).isPositive()) {
                selected = null;
                tv.offAll();
                tv.getClockDisplay().repaint();
            } else if (eSelected.getPiece().getColor().equals(eObjetive.getPiece().getColor())) { //it change piece?
                tv.offAll();
                selected = objetive;
                markActions(tv, board, eObjetive.getPos());
            }
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // doesn't need it
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        // doesn't need it
    }

    @Override
    public void mouseExited(MouseEvent e) {
        // doesn't need it
    }
    
    private void markActions(BoardDisplay display, AbstractBoard board, Point point) {
        selected.getEscaque().getPiece().getAllActions(board, point).forEach(action -> {
            if (action.y.getValue() instanceof Point p) {
                display.getEscaqueVisual(p).setAction(action.x);
            }
        });
    }
}
