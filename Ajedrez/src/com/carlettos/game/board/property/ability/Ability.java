package com.carlettos.game.board.property.ability;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Direction;
import com.carlettos.game.board.piece.Piece;
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
 * @see Piece
 */
public abstract non-sealed class Ability<P extends Piece, V, I extends Info<V>> implements InfoGetter<V>{

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
     * @see Piece
     */
    public Ability(String nombre, String descripcion, int cooldown, int costo, String parametros) {
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.cooldown = cooldown;
        this.costo = costo;
        this.parametros = parametros;
    }

    public abstract ActionResult canUse(AbstractBoard tablero, P pieza, Point inicio, I informacionExtra);

    public abstract void use(AbstractBoard tablero, P pieza, Point inicio, I informacionExtra);
    
    public boolean commonCanUse(AbstractBoard tablero, Piece pieza){
        boolean nomana = pieza.getCD() <= 0 && !pieza.isMoved();
        if(tablero instanceof Board t){
            return nomana && t.getClock().turnOf().getMana() >= this.costo;
        }
        return nomana;
    }
    
    public void commonUse(AbstractBoard tablero, Piece pieza){
        pieza.setIsMoved(true);
        pieza.changeCD(this.cooldown);
        if (tablero instanceof Board t) {
            t.getClock().turnOf().changeMana(-this.costo);
        }
    }
    
    public List<V> getOpciones(AbstractBoard tablero, Point inicio){
        //Da las opciones disponibles para la habilidad, es un all acciones
        //XXX: aquí hay mucho casteo y abstracción, 100% que falla 10 veces
        //por minuto, lo mejor es que cada clase rehaga este método para evitar errores
        Info<V> info = this.getInfoHabilidad();
        V[] valores = this.getAllValoresPosibles(tablero, inicio);
        List<V> lista = new ArrayList<>(tablero.columns * tablero.rows);
        
        if(info instanceof InfoNone){
            return List.of();
        } else if (info instanceof InfoInteger) {
            for (V valor : valores) {
                if(this.canUse(tablero, (P) tablero.getEscaque(inicio).getPiece(), inicio, (I) new InfoInteger((Integer) valor)).isPositive()){
                    lista.add(valor);
                }
            }
        } else if (info instanceof InfoNESW) {
            for (V valor : valores) {
                if(this.canUse(tablero, (P) tablero.getEscaque(inicio).getPiece(), inicio, (I) new InfoNESW((Direction) valor)).isPositive()){
                    lista.add(valor);
                }
            }
        } else if (info instanceof InfoPiece) {
            for (V valor : valores) {
                if(this.canUse(tablero, (P) tablero.getEscaque(inicio).getPiece(), inicio, (I) new InfoPiece((Piece) valor)).isPositive()){
                    lista.add(valor);
                }
            }
        } else if (info instanceof InfoPoint) {
            for (V valor : valores) {
                if(this.canUse(tablero, (P) tablero.getEscaque(inicio).getPiece(), inicio, (I) new InfoPoint((Point) valor)).isPositive()){
                    lista.add(valor);
                }
            }
        } else if (info instanceof InfoString) {
            for (V valor : valores) {
                if(this.canUse(tablero, (P) tablero.getEscaque(inicio).getPiece(), inicio, (I) new InfoString((String) valor)).isPositive()){
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
