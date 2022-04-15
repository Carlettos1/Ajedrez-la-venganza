package com.carlettos.game.board.property.ability;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Direction;
import com.carlettos.game.board.piece.Pieza;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.manager.AbstractBoard;
import java.util.ArrayList;
import java.util.List;

/**
 * Es la habilidad de la pieza, contiene toda la información acerca de la
 * habilidad, tales como costos y descripciones, además de los parámetros que
 * serán los que se deberán pasar como información extra.
 *
 * @author Carlos
 *
 * @param <P> Pieza que la habilidad afecta.
 * @param <V> Valor que utiliza para información extra.
 * @param <I> Tipo de información extra que usa.
 *
 * @see Pieza
 */
public abstract non-sealed class Habilidad<P extends Pieza, V, I extends Info<V>> implements InfoGetter<V>{

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

    public abstract ActionResult canUsar(AbstractBoard tablero, P pieza, Point inicio, I informacionExtra);

    public abstract void usar(AbstractBoard tablero, P pieza, Point inicio, I informacionExtra);
    
    public boolean commonCanUsar(AbstractBoard tablero, Pieza pieza){
        boolean nomana = pieza.getCdActual() <= 0 && !pieza.seHaMovidoEsteTurno();
        if(tablero instanceof Board t){
            return nomana && t.getReloj().turnoDe().getMana() >= this.costo;
        }
        return nomana;
    }
    
    public void commonUsar(AbstractBoard tablero, Pieza pieza){
        pieza.setSeHaMovidoEsteTurno(true);
        pieza.cambiarCD(this.cooldown);
        if (tablero instanceof Board t) {
            t.getReloj().turnoDe().cambiarMana(-this.costo);
        }
    }
    
    public List<V> getOpciones(AbstractBoard tablero, Point inicio){
        //Da las opciones disponibles para la habilidad, es un all acciones
        //XXX: aquí hay mucho casteo y abstracción, 100% que falla 10 veces
        //por minuto, lo mejor es que cada clase rehaga este método para evitar errores
        Info<V> info = this.getInfoHabilidad();
        V[] valores = this.getAllValoresPosibles(tablero, inicio);
        List<V> lista = new ArrayList<>(tablero.columnas * tablero.filas);
        
        if(info instanceof InfoNinguna){
            return List.of();
        } else if (info instanceof InfoInteger) {
            for (V valor : valores) {
                if(this.canUsar(tablero, (P) tablero.getEscaque(inicio).getPieza(), inicio, (I) new InfoInteger((Integer) valor)).isPositive()){
                    lista.add(valor);
                }
            }
        } else if (info instanceof InfoNESW) {
            for (V valor : valores) {
                if(this.canUsar(tablero, (P) tablero.getEscaque(inicio).getPieza(), inicio, (I) new InfoNESW((Direction) valor)).isPositive()){
                    lista.add(valor);
                }
            }
        } else if (info instanceof InfoPieza) {
            for (V valor : valores) {
                if(this.canUsar(tablero, (P) tablero.getEscaque(inicio).getPieza(), inicio, (I) new InfoPieza((Pieza) valor)).isPositive()){
                    lista.add(valor);
                }
            }
        } else if (info instanceof InfoPoint) {
            for (V valor : valores) {
                if(this.canUsar(tablero, (P) tablero.getEscaque(inicio).getPieza(), inicio, (I) new InfoPoint((Point) valor)).isPositive()){
                    lista.add(valor);
                }
            }
        } else if (info instanceof InfoString) {
            for (V valor : valores) {
                if(this.canUsar(tablero, (P) tablero.getEscaque(inicio).getPieza(), inicio, (I) new InfoString((String) valor)).isPositive()){
                    lista.add(valor);
                }
            }
        } //TODO: info compuesta
        return lista;
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
