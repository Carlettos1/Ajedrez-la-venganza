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
    public default boolean match(AbstractBoard board, Point start, Point end) {
        if (this.getColor().equals(Color.WHITE)) {
            Point p1 = new Point(start.x + 1, start.y + 1);
            Point p2 = new Point(start.x - 1, start.y + 1);
            return end.equals(p1) || end.equals(p2);
        } else if (getColor().equals(Color.BLACK)) {
            Point p1 = new Point(start.x + 1, start.y - 1);
            Point p2 = new Point(start.x - 1, start.y - 1);
            return end.equals(p1) || end.equals(p2);
        }
        return false;
    }
}
