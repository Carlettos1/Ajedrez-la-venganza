package com.carlettos.game.tablero.propiedad;

//TODO: hacer la clase más útil.
/**
 * Todos los tipos de piezas que hay, con su abreciación para motivos de
 * display.
 *
 * @author Carlos
 */
public enum Tipo {
    BIOLOGICA("bio"),
    ESTRUCTURA("estr"),
    INMUNE("inm"),
    DEMONIO("dem"),
    HEROICA("her"),
    GIGANTE("gig"),
    PEQUEÑA("peq"),
    TRANSPORTABLE("tp"),
    IMPENETRABLE("imp"),
    INTRASPASABLE("intras");

    private final String id;

    private Tipo(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
