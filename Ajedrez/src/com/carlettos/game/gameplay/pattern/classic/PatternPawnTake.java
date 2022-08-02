package com.carlettos.game.gameplay.pattern.classic;

import com.carlettos.game.board.IBaseBoard;
import com.carlettos.game.gameplay.pattern.PatternPawn;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public interface PatternPawnTake extends PatternPawn {

    @Override
    public default boolean match(IBaseBoard board, Point start, Point end) {
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
