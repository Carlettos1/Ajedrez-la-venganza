package com.carlettos.game.gameplay.pattern.classic;

import com.carlettos.game.board.IBaseBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.util.Point;

/**
 *
 * @author Carlettos
 */
public interface QueenPattern extends Pattern {

    @Override
    public default boolean match(IBaseBoard board, Point start, Point end) {
        return Patterns.BISHOP_PATTERN.match(board, start, end) || Patterns.ROOK_PATTERN.match(board, start, end);
    }
}
