package com.carlettos.game.board.piece.pattern.classic;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Patron;

/**
 * 
 * @author Carlettos
 */
public interface PatronReina extends Patron {
    public static Patron ALFIL = new PatronAlfil() {};
    public static Patron TORRE = new PatronTorre() {};

    @Override
    public default boolean checkPatron(AbstractBoard tablero, Point inicio, Point final_) {
        return ALFIL.checkPatron(tablero, inicio, final_) || TORRE.checkPatron(tablero, inicio, final_);
    }
}
