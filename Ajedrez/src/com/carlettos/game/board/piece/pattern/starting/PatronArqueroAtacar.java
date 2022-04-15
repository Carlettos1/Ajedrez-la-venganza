package com.carlettos.game.board.piece.pattern.starting;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Patron;

/**
 *
 * @author Carlettos
 */
public interface PatronArqueroAtacar extends Patron{

    @Override
    public default boolean checkPatron(AbstractBoard tablero, Point inicio, Point final_) {
        final int rango = 6;
        return inicio.getDistanceTo(final_) <= rango;
    }
}
