package ajedrez.carlettos.proyecto.util;

import ajedrez.carlettos.proyecto.pieza.base.Pieza;

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
    MOVER,
    COMER,
    HABILIDAD;
}
