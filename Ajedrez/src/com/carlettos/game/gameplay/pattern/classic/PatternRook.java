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
        boolean movX = end.x != start.x;
        boolean movY = end.y != start.y;
        if (movX && movY) {
            return false;
        }
        int dirX = Integer.signum(end.x - start.x);
        int dirY = Integer.signum(end.y - start.y);

        if (movX) { //se mueve el x
            for (int plus = 1; plus < Math.abs(end.x - start.x); plus++) {
                if (board.getEscaque(start.x + plus * dirX, start.y).hasPiece()) {
                    return false;
                }
            }
        } else if (movY) { //se mueve en y
            for (int plus = 1; plus < Math.abs(end.y - start.y); plus++) {
                if (board.getEscaque(start.x, start.y + plus * dirY).hasPiece()) {
                    return false;
                }
            }
        }
        return true;
    }
}
