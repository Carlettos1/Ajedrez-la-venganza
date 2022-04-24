package com.carlettos.game.gameplay.pattern.classic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.util.Point;

/**
 * 
 * @author Carlettos
 */
public interface PatternQueen extends Pattern {
    public static Pattern BISHOP = new PatternBishop() {};
    public static Pattern ROOK = new PatternRook() {};

    @Override
    public default boolean match(AbstractBoard board, Point start, Point end) {
        return BISHOP.match(board, start, end) || ROOK.match(board, start, end);
    }
}
