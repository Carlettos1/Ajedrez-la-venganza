package com.carlettos.game.gameplay.pattern.starting;

import com.carlettos.game.board.IBaseBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.util.Point;

/**
 *
 * @author Carlettos
 */
public interface PatternArcherMove extends Pattern {
    @Override
    public default boolean match(IBaseBoard board, Point start, Point end) {
        return Patterns.MAGICIAN_MOVE_PATTERN.match(board, start, end)
                || Patterns.KING_PATTERN.match(board, start, end);
    }
}
