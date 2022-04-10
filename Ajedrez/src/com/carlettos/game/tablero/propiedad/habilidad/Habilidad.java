package com.carlettos.game.tablero.propiedad.habilidad;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.core.Point;

/**
 * Es la habilidad de la pieza, contiene toda la información acerca de la
 * habilidad, tales como costos y descripciones, además de los parámetros que
 * serán los que se deberán pasar como información extra.
 *
 * @author Carlos
 *
 * @param <P> Pieza que la habilidad afecta.
 *
 * @see Pieza
 */
public abstract class Habilidad<P extends Pieza> {

    /**
     * Nombre de la habilidad.
     */
    protected final String nombre;

    /**
     * Descripción de la habilidad; dice lo que precisamente hace la habilidad.
     */
    protected final String descripcion;

    /**
     * Turnos en los cuales no se puede volver a lanzar la habilidad.
     */
    protected final int cooldown;

    /**
     * Costo en maná de la habilidad.
     */
    protected final int costo;

    /**
     * Datos adicionales que se necesitan para usar la habilidad.
     */
    protected final String parametros;

    /**
     * Constructor general.
     *
     * @param nombre el nombre de la habilidad.
     * @param descripcion descripción de la habilidad.
     * @param cooldown cooldown.
     * @param costo coste de maná que cuesta lanzar la habilidad.
     * @param parametros valores que debe proporcionar el jugador.
     *
     * @see Pieza
     */
    public Habilidad(String nombre, String descripcion, int cooldown, int costo, String parametros) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cooldown = cooldown;
        this.costo = costo;
        this.parametros = parametros;
    }

    public abstract ActionResult canUsar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra);

    public abstract void usar(Tablero tablero, P pieza, Point inicio, Point final_, String informacionExtra);

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
