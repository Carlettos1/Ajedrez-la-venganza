package com.carlettos.game.board.piece.pattern.classic;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.PatternPawn;
import com.carlettos.game.board.property.Color;

/**
 *
 * @author Carlettos
 */
public interface PatternPawnTake extends PatternPawn{

    @Override
    public default boolean match(AbstractBoard tablero, Point inicio, Point final_) {
        if (this.getColor().equals(Color.WHITE)) {
            Point punto1 = new Point(inicio.x + 1, inicio.y + 1);
            Point punto2 = new Point(inicio.x - 1, inicio.y + 1);
            return final_.equals(punto1) || final_.equals(punto2);
        } else if (getColor().equals(Color.BLACK)) {
            Point punto1 = new Point(inicio.x + 1, inicio.y - 1);
            Point punto2 = new Point(inicio.x - 1, inicio.y - 1);
            return final_.equals(punto1) || final_.equals(punto2);
        }
        return false;
    }
}