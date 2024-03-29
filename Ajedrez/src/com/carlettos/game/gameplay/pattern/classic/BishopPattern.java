package com.carlettos.game.gameplay.pattern.classic;

import com.carlettos.game.board.IBaseBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;

/**
 *
 * @author Carlettos
 */
public interface BishopPattern extends Pattern {

    @Override
    public default boolean match(IBaseBoard board, Point start, Point end) {
        int deltaX = end.x - start.x;
        int deltaY = end.y - start.y;

        if (Math.abs(deltaY) != Math.abs(deltaX)) { return false; }

        int signX = deltaX > 0 ? 1 : -1;
        int signY = deltaY > 0 ? 1 : -1;

        for (int escaque = 1; escaque < Math.abs(deltaX); escaque++) {
            if (board.get(start.add(escaque * signX, escaque * signY)).hasPiece()) { return false; }
        }
        return true;
    }
}
