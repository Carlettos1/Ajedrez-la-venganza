package com.carlettos.game.core;

/**
 * Son los posibles resultados de una acción, sirve para reaccionar de acuerdo a
 * lo que pueda ocurrir en cualquier método.
 *
 * @author Carlos
 */
public enum ActionResult {
    PASS("Pass"),
    FAIL("Fail");

    private final String nombre;

    private ActionResult(String nombre) {
        this.nombre = nombre;
    }

    public String getNombre() {
        return nombre;
    }
}
