package ajedrez.carlettos.src.util;

import ajedrez.carlettos.src.pieza.base.Pieza;
import java.awt.Color;

/**
 * Sirve para decir qué accion se hace, pero es mejor usar métodos determinados
 * para cada acción en vez de uno global. Aún así, al momento de usar el
 * getAllAcciones de la clase Pieza, es útil al momento de graficar.
 *
 * @author Carlos
 * 
 * @see Pieza
 */
public enum Accion {
    MOVER(Color.CYAN),
    COMER(Color.RED),
    HABILIDAD(Color.MAGENTA);
    
    private final Color color;

    private Accion(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
