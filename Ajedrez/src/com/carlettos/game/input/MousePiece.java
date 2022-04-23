package com.carlettos.game.input;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.Escaque;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.core.Point;
import com.carlettos.game.display.board.EscaqueDisplay;
import com.carlettos.game.display.board.BoardDisplay;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

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

    public EscaqueDisplay selected;

    @Override
    public void mouseClicked(MouseEvent e) {
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
            var tv = DisplayHelper.getBoardDisplay(selected);
            var board = tv.getBoard();

            if (!board.canPlay(selected.getEscaque().getPiece())) {
                selected = null;
                return;
            }
            
            selected.getEscaque().getPiece().getAllActions(board, escaque.getPos()).forEach((accion) -> {
                if (accion.y.getValue() instanceof Point p) {
                    tv.getEscaqueVisual(p).setAction(accion.x);
                }
            });
        } else {
            var objetive = (EscaqueDisplay) e.getSource();
            var eSelected = selected.getEscaque();
            var eObjetive = objetive.getEscaque();
            var tv = DisplayHelper.getBoardDisplay(selected);
            var board1 = tv.getBoard();

            if (board1.tryTo(Action.MOVE, eSelected.getPos(), eObjetive.getPos().toInfo()).equals(ActionResult.FAIL)) {
                if (board1.tryTo(Action.TAKE, eSelected.getPos(), eObjetive.getPos().toInfo()).equals(ActionResult.FAIL)) {
                    if (board1.tryTo(Action.ATTACK, eSelected.getPos(), eObjetive.getPos().toInfo()).equals(ActionResult.FAIL)) {
                        if (eSelected.getPiece().getColor().equals(eObjetive.getPiece().getColor())) {
                            //si cambió de pieza
                            tv.offAll();
                            selected = objetive;
                            selected.getEscaque().getPiece().getAllActions(board1, eObjetive.getPos()).forEach((accion) -> {
                                if (accion.y.getValue() instanceof Point p) {
                                    tv.getEscaqueVisual(p).setAction(accion.x);
                                }
                            });
                            return;
                        }
                    }
                }
            }
            selected = null;
            tv.offAll();
            tv.getClockDisplay().repaint();
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}
