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
    protected final String name;

    /**
     * Descripción de la habilidad; dice lo que precisamente hace la habilidad.
     */
    protected final String description;

    /**
     * Turnos en los cuales no se puede volver a lanzar la habilidad.
     */
    protected final int cooldown;

    /**
     * Costo en maná de la habilidad.
     */
    protected final int manaCost;

    /**
     * Datos adicionales que se necesitan para usar la habilidad.
     */
    protected final String params;

    /**
     * Constructor general.
     *
     * @param name el nombre de la habilidad.
     * @param description descripción de la habilidad.
     * @param cooldown cooldown.
     * @param manaCost coste de maná que cuesta lanzar la habilidad.
     * @param params valores que debe proporcionar el jugador.
     *
     * @see Piece
     */
    public Ability(String name, String description, int cooldown, int manaCost, String params) {
        this.name = name;
        this.description = description;
        this.cooldown = cooldown;
        this.manaCost = manaCost;
        this.params = params;
    }

    public abstract ActionResult canUse(AbstractBoard tablero, P pieza, Point inicio, I informacionExtra);

    public abstract void use(AbstractBoard tablero, P pieza, Point inicio, I informacionExtra);
    
    public boolean commonCanUse(AbstractBoard tablero, Piece pieza){
        boolean nomana = pieza.getCD() <= 0 && !pieza.isMoved();
        if(tablero instanceof Board t){
            return nomana && t.getClock().turnOf().getMana() >= this.manaCost;
        }
        return nomana;
    }
    
    public void commonUse(AbstractBoard tablero, Piece pieza){
        pieza.setIsMoved(true);
        pieza.changeCD(this.cooldown);
        if (tablero instanceof Board t) {
            t.getClock().turnOf().changeMana(-this.manaCost);
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCooldown() {
        return cooldown;
    }

    public int getManaCost() {
        return manaCost;
    }

    public String getParams() {
        return params;
    }
}
