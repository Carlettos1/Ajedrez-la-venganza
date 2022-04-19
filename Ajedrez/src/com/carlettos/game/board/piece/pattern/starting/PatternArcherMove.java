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
    public static Pattern REY = new PatternKing() {};
    public static Pattern HECHICERO = new PatternMagicianMove() {};
    
    @Override
    public default boolean match(AbstractBoard tablero, Point inicio, Point final_) {
        return HECHICERO.match(tablero, inicio, final_) || REY.match(tablero, inicio, final_);
    }
}
