package com.carlettos.game.core;

import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.tablero.propiedad.Color;

/**
 * Sirve para decir qué accion se hace, pero es mejor usar métodos determinados
 * para cada acción en vez de uno global. Aún así, al momento de usar el
 * getAllAcciones de la clase Pieza, es útil al momento de graficar.
 *
 * @author Carlos
 * 
 * @see Pieza
 * @see ActionResult
 */
public enum Accion {
    MOVER(Color.CIAN),
    ATACAR(Color.ROJO),
    COMER(Color.ROJO),
    HABILIDAD(Color.MAGENTA);
    
    private final Color color;

    private Accion(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
