package com.carlettos.game.board.piece.pattern.starting;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Pattern;

/**
 *
 * @author Carlettos
 */
public interface PatternArcherAttack extends Pattern{

    @Override
    public default boolean match(AbstractBoard tablero, Point inicio, Point final_) {
        final int rango = 6;
        return inicio.getDistanceTo(final_) <= rango;
    }
}
