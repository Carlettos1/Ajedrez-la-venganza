package ajedrez.carlettos.src.estructura.propiedad;

/**
 * Todos los tipos de estructuras que hay, con su abreciación para motivos de
 * display.
 * 
 * @author Carlos
 */
public enum EnumTipoEstructura {
    INTRASPASABLE("intras");

    private final String id;

    private EnumTipoEstructura(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
