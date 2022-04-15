package com.carlettos.game.board.piece.pattern.classic;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.PatronPeon;
import com.carlettos.game.board.property.Color;

/**
 *
 * @author Carlettos
 */
public interface PatronPeonMover extends PatronPeon{

    @Override
    public default boolean checkPatron(AbstractBoard tablero, Point inicio, Point final_) {
        if (getColor().equals(Color.BLANCO)) {
            Point puntoSiguiente = new Point(inicio.x, inicio.y + 1);
            Point puntoSubSiguiente = new Point(inicio.x, inicio.y + 2);
            
            if (final_.equals(puntoSiguiente)) {
                return true;
            } else if (final_.equals(puntoSubSiguiente)) {
                return !tablero.getEscaque(puntoSiguiente).hasPieza();
            }
        } else if (getColor().equals(Color.NEGRO)) {
            Point puntoAnterior = new Point(inicio.x, inicio.y - 1);
            Point puntoAnteAnterior = new Point(inicio.x, inicio.y - 2);
            
            if (final_.equals(puntoAnterior)) {
                return true;
            } else if (final_.equals(puntoAnteAnterior)) {
                return !tablero.getEscaque(puntoAnterior).hasPieza();
            }
        }
        return false;
    }
}
