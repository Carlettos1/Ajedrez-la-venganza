package com.carlettos.game.tablero.propiedad;


/**
 * Es la habilidad de la pieza o estructura, contiene toda la información acerca
 * de la habilidad, tales como costos y descripciones, además de los parámetros
 * que serán los que se deberán pasar como información extra
 *
 * @author Carlos
 *
 * @see Pieza
 * @see Estructura
 */
public class Habilidad {

    /**
     * Place-holder de Habilidad, para evitar que haya algún null.
     *
     * @see Vacia
     * @see Inexistente
     */
    public final static Habilidad NO_HABILIDAD = new Habilidad("Habilidad nula", "No hace nada", 0, 0, "Ninguno");

    //TODO: revisar nombres y detalles de las habilidades de ejemplo.
    /**
     * Nombre de la habilidad, como, por ejemplo, "Coronar", del peón, o "Muro
     * de Berlín", de la torre.
     */
    protected final String nombre;

    /**
     * Descripción de la habilidad, dice lo que precisamente hace la habilidad,
     * por ejemplo, "Al llegar al final del tablero, puede transformarse en
     * cualquier pieza", o, "Mueve esta torre y todas las torres conectadas en
     * una dirección elegida. Se mueven hasta que no se puedan mover más o hasta
     * que comen una pieza. No puede comer piezas aliadas."
     */
    protected final String descripcion;

    /**
     * Turnos en los cuales no se puede volver a lanzar la habilidad, por
     * ejemplo, el peón tiene 0, ya que una vez corona se pierde ese peón,
     * mientras que, el muro de berlín tiene que esperar 10 turnos desde que lo
     * usó por última vez.
     */
    protected final int cooldown;

    /**
     * Costo en maná de la habilidad, o sea, cuánto maná debería quitarse del
     * jugador una vez lanzada la habilidad.
     */
    protected final int costo;

    /**
     * Datos adicionales que se necesitan para usar la habilidad. Por ejemplo,
     * el peón requiere que se le diga en qué pieza va a coronar, mientras que,
     * la torre, necesita solo una de las 4 direcciones para poder usar la
     * habilidad.
     */
    protected final String parametros;

    /**
     * Crea la habilidad. para que la habilidad tenga efecto y ocurran sus
     * efectos en el tablero, debe sobreescribirse el método habilidad de la
     * clase pieza, ya que esta clase es de solo utilidad.
     *
     * @param nombre el nombre de la habilidad, como, por ejemplo, "Coronar".
     * @param descripcion descripción de la habilidad, o sea, decir
     * explícitamente lo que hace la habilidad.
     * @param cooldown tiempo, en turnos, en que la habilidad no puede volver a
     * ser lanzada.
     * @param costo coste de maná que cuesta lanzar la habilidad.
     * @param parametros valores que debe proporcionar el jugador, por ejemplo
     * "Pieza a coronar (Caballo, Alfil, Torre o Dama)", del peón en el ajedrez
     * normal.
     * 
     * @see Pieza
     * @see Estructura
     */
    public Habilidad(String nombre, String descripcion, int cooldown, int costo, String parametros) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cooldown = cooldown;
        this.costo = costo;
        this.parametros = parametros;
    }

    public String getNombre() {
        return nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getCosto() {
        return costo;
    }

    public String getParametros() {
        return parametros;
    }
}
