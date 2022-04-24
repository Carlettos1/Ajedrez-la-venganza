package com.carlettos.game.gameplay.pattern.classic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;

/**
 *
 * @author Carlettos
 */
public interface PatternRook extends Pattern {

    @Override
    public default boolean match(AbstractBoard board, Point start, Point end) {
        if (end.x != start.x && end.y != start.y) {
            return false;
        }

        if (end.x != start.x) { //se mueve el x
            int dir = end.x > start.x ? 1 : -1;
            for (int plus = 1; plus < Math.abs(end.x - start.x); plus++) {
                if (board.getEscaque(start.x + plus * dir, start.y).hasPiece()) {
                    return false;
                }
            }
        } else if (end.y != start.y) { //se mueve en y
            int dir = end.y > start.y ? 1 : -1;
            for (int plus = 1; plus < Math.abs(end.y - start.y); plus++) {
                if (board.getEscaque(start.x, start.y + plus * dir).hasPiece()) {
                    return false;
                }
            }
        }
        return true;
    }
}
