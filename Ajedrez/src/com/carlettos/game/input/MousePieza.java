package com.carlettos.game.input;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Par;
import com.carlettos.game.tablero.Escaque;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.visual.EscaqueVisual;
import com.carlettos.game.visual.TableroVisual;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.List;

/**
 * Listener implementation class.
 * 
 * @author Carlos
 */
public class MousePieza implements MouseListener {
    private static final MousePieza LISTENER = new MousePieza();

    private MousePieza(){
    }
    
    public static MousePieza get(){
        return LISTENER;
    }
    
    /**
     * Sirve para marcar el último escaque seleccionado.
     */
    public EscaqueVisual seleccionado;

    @Override
    public void mouseClicked(MouseEvent e) {
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (seleccionado == null) {
            seleccionado = (EscaqueVisual) e.getSource();
            Escaque escaque = seleccionado.getEscaque();
            if (escaque.isEmpty()) {
                seleccionado = null;
                return;
            }
            TableroVisual tv = ListenerHelper.getTableroVisual(seleccionado);
            Tablero tablero = tv.getTablero();

            List<Par<Point, Accion>> allAcciones = seleccionado.getEscaque().getPieza().allAcciones(tablero, escaque.getLocalizacion());
            allAcciones.forEach((accion) -> {
                tv.getEscaqueVisual(accion.x).setHasAccion(true, accion.y.getColor());
            });
        } else {
            EscaqueVisual objetivo = (EscaqueVisual) e.getSource();
            Escaque escaqueSeleccionado = seleccionado.getEscaque();
            Escaque escaqueObjetivo = objetivo.getEscaque();
            TableroVisual tv = ListenerHelper.getTableroVisual(seleccionado);
            Tablero tablero = tv.getTablero();

            //TODO: quitar los strings que molestan
            Par<ActionResult, String> result = tablero.intentarMoverPieza(escaqueSeleccionado.getLocalizacion(), escaqueObjetivo.getLocalizacion());
            System.out.println(result.y);
            if (result.x.equals(ActionResult.FAIL)) {
                result = tablero.intentarComerPieza(escaqueSeleccionado.getLocalizacion(), escaqueObjetivo.getLocalizacion());
                System.out.println(result.y);
                if (result.x.equals(ActionResult.FAIL)){
                    if (escaqueSeleccionado.getPieza().getColor().equals(escaqueObjetivo.getPieza().getColor())) {
                        //si cambió de pieza
                        tv.offAll();
                        seleccionado = objetivo;
                        List<Par<Point, Accion>> allAcciones = seleccionado.getEscaque().getPieza().allAcciones(tablero, escaqueObjetivo.getLocalizacion());

                        allAcciones.forEach((accion) -> {
                            tv.getEscaqueVisual(accion.x).setHasAccion(true, accion.y.getColor());
                        });
                        return;
                    }
                }
            }
            seleccionado = null;
            tv.offAll();
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