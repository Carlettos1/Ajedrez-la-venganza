package com.carlettos.game.board.piece.pattern.classic;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Pattern;

/**
 *
 * @author Carlettos
 */
public interface PatternBishop extends Pattern {

    @Override
    public default boolean match(AbstractBoard board, Point start, Point end) {
        int deltaX = end.x - start.x;
        int deltaY = end.y - start.y;

        if (Math.abs(deltaY) != Math.abs(deltaX)) {
            return false;
        }

        int signX = deltaX > 0 ? 1 : -1;
        int signY = deltaY > 0 ? 1 : -1;

        for (int escaque = 1; escaque < Math.abs(deltaX); escaque++) {
            if (board.getEscaque(start.x + escaque * signX,
                    start.y + escaque * signY).hasPiece()) {
                return false;
            }
        }
        return true;
    }
}
