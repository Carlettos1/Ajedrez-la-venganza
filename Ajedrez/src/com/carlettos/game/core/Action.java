package com.carlettos.game.core;

import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.property.Color;

/**
 * Sirve para decir qué accion se hace, pero es mejor usar métodos determinados
 * para cada acción en vez de uno global. Aún así, al momento de usar el
 * getAllAcciones de la clase Pieza, es útil al momento de graficar.
 *
 * @author Carlos
 * 
 * @see Piece
 * @see ActionResult
 */
public enum Action {
    MOVE(Color.CYAN),
    ATTACK(Color.ORANGE),
    TAKE(Color.RED),
    ABILITY(Color.MAGENTA);
    
    private final Color color;

    private Action(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
