package com.carlettos.game.board.piece.pattern.starting;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Patron;
import com.carlettos.game.board.piece.pattern.classic.PatronRey;

/**
 *
 * @author Carlettos
 */
public interface PatronArqueroMover extends Patron {
    public static Patron REY = new PatronRey() {};
    public static Patron HECHICERO = new PatronHechiceroMover() {};
    
    @Override
    public default boolean checkPatron(AbstractBoard tablero, Point inicio, Point final_) {
        return HECHICERO.checkPatron(tablero, inicio, final_) || REY.checkPatron(tablero, inicio, final_);
    }
}
