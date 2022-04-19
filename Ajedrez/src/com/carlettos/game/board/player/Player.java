package com.carlettos.game.board.player;

import com.carlettos.game.board.manager.Hand;
import com.carlettos.game.board.property.Color;
import java.util.Objects;
import java.util.Random;

/**
 * Es la clase que representa a un jugador.
 *
 * @author Carlos
 */
public class Player {

    private int movimientosPorTurnos;
    private int mana;
    private final String id;
    private final Color color;
    private final Hand mano;

    /**
     * Constructor general.
     *
     * @param movimientosPorTurnos cantidad de movimientos que tiene el jugador
     * por cada turno, este número es 1 en el ajedrez normal.
     * @param mana cantidad de maná que tiene el jugador, este número es 0 en el
     * ajedrez normal.
     * @param id id del jugador.
     * @param color color del jugador.
     * @param mano Mano del jugador, contiene todas las cartas del jugador, este
     * objeto no tiene cartas en el ajedrez normal.
     *
     * @see Hand
     */
    public Player(int movimientosPorTurnos, int mana, String id, Color color, Hand mano) {
        this.color = color;
        this.movimientosPorTurnos = movimientosPorTurnos;
        this.id = id;
        this.mana = mana;
        this.mano = mano;
    }

    /**
     * Constructor básico, inicializa con 1 movimiento por turno y 0 de maná.
     * Para mayor personalización usar el constructor general o usar los métodos
     * correspondientes.
     *
     * @param id id del jugador.
     * @param color color del jugador.
     * @param mano Mano del jugador.
     *
     * @see Hand
     */
    public Player(String id, Hand mano, Color color) {
        this(1, 0, id, color, mano);
    }

    /**
     * Este constructor da como resultado un jugador del ajedrez normal.
     *
     * <p>
     * Constructor de ayuda, se puede usar pero no es lo recomendable,
     * inicializa con un ManoManager completamente nuevo, 0 de mana y 1
     * movimiento por turno, para personalizar mejor al jugador, usar el
     * constructor general o usar los métodos correspondientes.
     *
     * @param color color del jugador.
     *
     * @see Hand
     */
    public Player(Color color) {
        this(1, 0, color.toString() + " - " + (new Random().nextInt()), color, new Hand());
    }

    public Color getColor() {
        return color;
    }

    public int getMaxMovements() {
        return movimientosPorTurnos;
    }

    public int getMana() {
        return mana;
    }

    public Hand getHand() {
        return mano;
    }

    /**
     * Cambia la cantidad de mana del jugador sumándole el valor entregado.
     *
     * @param mana Mana a sumar, puede ser negativo;
     */
    public void changeMana(int mana) {
        if (this.mana + mana < 0) {
            this.mana = 0;
        } else {
            this.mana += mana;
        }
    }

    /**
     * Cambia la cantidad de movimientos por turno del jugador, sumándole el
     * valor entregado.
     *
     * @param movimientos Movimientos a sumar, puede ser negativo;
     */
    public void cambiarMovimientos(int movimientos) {
        if (this.movimientosPorTurnos + movimientos < 1) {
            this.movimientosPorTurnos = 1;
        } else {
            movimientosPorTurnos += movimientos;
        }
    }
    
    public void robarCarta(){
        //TODO: robar carta
    }

    @Override
    public String toString() {
        return id;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Player other = (Player) obj;
        return Objects.equals(this.id, other.id);
    }
}
