package com.carlettos.game.input;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Tuple;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.Escaque;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.visual.EscaqueDisplay;
import com.carlettos.game.visual.BoardDisplay;
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

    /**
     * Sirve para marcar el último escaque seleccionado.
     */
    public EscaqueDisplay seleccionado;

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (seleccionado == null) {
            seleccionado = (EscaqueDisplay) e.getSource();
            Escaque escaque = seleccionado.getEscaque();
            if (escaque.isEmpty()) {
                seleccionado = null;
                return;
            }
            BoardDisplay tv = DisplayHelper.getBoardDisplay(seleccionado);
            Board tablero = tv.getBoard();

            if (!tablero.canMoverPieza(seleccionado.getEscaque().getPiece())) {
                seleccionado = null;
                return;
            }
            
            seleccionado.getEscaque().getPiece().allAcciones(tablero, escaque.getPos()).forEach((accion) -> {
                tv.getEscaqueVisual(accion.x).setAccion(accion.y);
            });
        } else {
            EscaqueDisplay objetivo = (EscaqueDisplay) e.getSource();
            Escaque escaqueSeleccionado = seleccionado.getEscaque();
            Escaque escaqueObjetivo = objetivo.getEscaque();
            BoardDisplay tv = DisplayHelper.getBoardDisplay(seleccionado);
            Board tablero = tv.getBoard();

            if (tablero.intentarMoverPieza(escaqueSeleccionado.getPos(), escaqueObjetivo.getPos()).equals(ActionResult.FAIL)) {
                if (tablero.intentarComerPieza(escaqueSeleccionado.getPos(), escaqueObjetivo.getPos()).equals(ActionResult.FAIL)) {
                    if (tablero.intentarAtacarPieza(escaqueSeleccionado.getPos(), escaqueObjetivo.getPos()).equals(ActionResult.FAIL)) {
                        if (escaqueSeleccionado.getPiece().getColor().equals(escaqueObjetivo.getPiece().getColor())) {
                            //si cambió de pieza
                            tv.offAll();
                            seleccionado = objetivo;
                            seleccionado.getEscaque().getPiece().allAcciones(tablero, escaqueObjetivo.getPos()).forEach((accion) -> {
                                tv.getEscaqueVisual(accion.x).setAccion(accion.y);
                            });
                            return;
                        }
                    }
                }
            }
            seleccionado = null;
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
