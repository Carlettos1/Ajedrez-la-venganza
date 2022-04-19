package com.carlettos.game.board.piece.pattern.classic;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Pattern;

/**
 * 
 * @author Carlettos
 */
public interface PatternQueen extends Pattern {
    public static Pattern ALFIL = new PatternBishop() {};
    public static Pattern TORRE = new PatternRook() {};

    @Override
    public default boolean match(AbstractBoard tablero, Point inicio, Point final_) {
        return ALFIL.match(tablero, inicio, final_) || TORRE.match(tablero, inicio, final_);
    }
}
