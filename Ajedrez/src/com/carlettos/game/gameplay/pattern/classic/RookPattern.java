package com.carlettos.game.gameplay.pattern.classic;

import com.carlettos.game.board.IBaseBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;

/**
 *
 * @author Carlettos
 */
public interface RookPattern extends Pattern {

    @Override
    public default boolean match(IBaseBoard board, Point start, Point end) {
        boolean movX = end.x != start.x;
        boolean movY = end.y != start.y;
        if (movX && movY) { return false; }
        int dirX = Integer.signum(end.x - start.x);
        int dirY = Integer.signum(end.y - start.y);

        if (movX) { // se mueve el x
            for (int plus = 1; plus < Math.abs(end.x - start.x); plus++) {
                if (board.get(start.add(plus * dirX, 0)).hasPiece()) { return false; }
            }
        } else if (movY) { // se mueve en y
            for (int plus = 1; plus < Math.abs(end.y - start.y); plus++) {
                if (board.get(start.add(0, plus * dirY)).hasPiece()) { return false; }
            }
        }
        return true;
    }
}
