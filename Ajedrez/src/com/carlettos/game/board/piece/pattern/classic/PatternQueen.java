package com.carlettos.game.board.piece.pattern.classic;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Pattern;

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
