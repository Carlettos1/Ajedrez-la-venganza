package com.carlettos.game.board.piece.pattern.starting;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Pattern;
import com.carlettos.game.board.piece.pattern.classic.PatternKing;

/**
 *
 * @author Carlettos
 */
public interface PatternArcherMove extends Pattern {
    public static Pattern KING = new PatternKing() {};
    public static Pattern MAGICIAN = new PatternMagicianMove() {};
    
    @Override
    public default boolean match(AbstractBoard board, Point start, Point end) {
        return MAGICIAN.match(board, start, end) || KING.match(board, start, end);
    }
}
