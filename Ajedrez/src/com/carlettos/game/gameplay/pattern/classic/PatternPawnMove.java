package com.carlettos.game.gameplay.pattern.classic;

import com.carlettos.game.board.IBaseBoard;
import com.carlettos.game.gameplay.pattern.PatternPawn;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public interface PatternPawnMove extends PatternPawn {

    @Override
    public default boolean match(IBaseBoard board, Point start, Point end) {
        if (getColor().equals(Color.WHITE)) {
            Point nextPoint = new Point(start.x, start.y + 1);
            Point nextNextPoint = new Point(start.x, start.y + 2);

            if (end.equals(nextPoint)) {
                return true;
            } else if (end.equals(nextNextPoint)) { return !board.getEscaque(nextPoint).hasPiece(); }
        } else if (getColor().equals(Color.BLACK)) {
            Point prevPoint = new Point(start.x, start.y - 1);
            Point prevPrevPoint = new Point(start.x, start.y - 2);

            if (end.equals(prevPoint)) {
                return true;
            } else if (end.equals(prevPrevPoint)) { return !board.getEscaque(prevPoint).hasPiece(); }
        }
        return false;
    }
}
