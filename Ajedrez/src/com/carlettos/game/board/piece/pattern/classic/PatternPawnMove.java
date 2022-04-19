package com.carlettos.game.board.piece.pattern.classic;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.PatternPawn;
import com.carlettos.game.board.property.Color;

/**
 *
 * @author Carlettos
 */
public interface PatternPawnMove extends PatternPawn{

    @Override
    public default boolean match(AbstractBoard tablero, Point inicio, Point final_) {
        if (getColor().equals(Color.WHITE)) {
            Point puntoSiguiente = new Point(inicio.x, inicio.y + 1);
            Point puntoSubSiguiente = new Point(inicio.x, inicio.y + 2);
            
            if (final_.equals(puntoSiguiente)) {
                return true;
            } else if (final_.equals(puntoSubSiguiente)) {
                return !tablero.getEscaque(puntoSiguiente).hasPiece();
            }
        } else if (getColor().equals(Color.BLACK)) {
            Point puntoAnterior = new Point(inicio.x, inicio.y - 1);
            Point puntoAnteAnterior = new Point(inicio.x, inicio.y - 2);
            
            if (final_.equals(puntoAnterior)) {
                return true;
            } else if (final_.equals(puntoAnteAnterior)) {
                return !tablero.getEscaque(puntoAnterior).hasPiece();
            }
        }
        return false;
    }
}