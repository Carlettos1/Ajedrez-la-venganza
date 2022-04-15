package com.carlettos.game.board.piece.pattern;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;

/**
 * Se define como patrón el "patrón" de acción de una pieza. Básicamente es para
 * abstraer el "A se mueve como B y come como C", de la forma: B implementa el
 * patrón 1, C el patrón 2, y A implementa ambos, utilizando el patrón 1 para 
 * mover y el 2 para comer.
 * Por extensión se pueden usar los patrones para mover, comer, atacar e incluso 
 * para habilidades.
 *
 * @author Carlettos
 */
public interface Patron {

    /**
     * Revisa si el punto de inicio y el punto final corresponden al patrón
     * representado.
     *
     * @param tablero tablero en el que se quiere comprobar el patrón.
     * @param inicio donde está la pieza.
     * @param final_ donde se quiere comprobar si cumple el patrón.
     * @return Si cumple con el patrón {@code true}, sino, {@code false}.
     */
    public boolean checkPatron(AbstractBoard tablero, Point inicio, Point final_);
}
