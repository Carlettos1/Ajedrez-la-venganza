package com.carlettos.game.gameplay.pattern.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.classic.PatternKing;
import com.carlettos.game.util.Point;

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
