package ajedrez.carlettos.proyecto.pieza.propiedad;

/**
 * Todos los tipos de piezas que hay, con su abreciación para motivos de
 * display.
 *
 * @author Carlos
 */
public enum EnumTipoPieza {
    BIOLOGICA("bio"),
    ESTRUCTURA("estr"),
    INMUNE("inm"),
    DEMONIO("dem"),
    HEROICA("her"),
    GIGANTE("gig"),
    PEQUEÑA("peq"),
    TRANSPORTABLE("tp"),
    IMPENETRABLE("imp");

    private final String id;

    private EnumTipoPieza(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
