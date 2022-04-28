package com.carlettos.game.gameplay.player;

import java.util.Objects;
import java.util.Random;

import com.carlettos.game.board.Hand;
import com.carlettos.game.board.clock.Clock;
import com.carlettos.game.util.enums.Color;

/**
 * It's a player.
 *
 * @author Carlos
 */
public class Player {

    private int movements;
    private int mana;
    private final String id;
    private final Color color;
    private final Hand hand;

    /**
     * General constructor.
     *
     * @param movements number of movements per turn.
     * @param mana mana of the player
     * @param id id of the player.
     * @param color color of the pieces of the player.
     * @param hand hand of the player.
     *
     * @see Hand
     */
    public Player(int movements, int mana, String id, Color color, Hand hand) {
        this.color = color;
        this.movements = movements;
        this.id = id;
        this.mana = mana;
        this.hand = hand;
    }

    /**
     * Constructs a Player with 1 movement per turn and 0 mana.
     *
     * @param id id of the player.
     * @param color color of the player.
     * @param hand hand of the player.
     *
     * @see Hand
     */
    public Player(String id, Hand hand, Color color) {
        this(1, 0, id, color, hand);
    }

    /**
     * Construct a Player with 1 movement per turn, 0 mana, a random id, and a
     * new hand with 0 cards.
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
        return movements;
    }

    public int getMana() {
        return mana;
    }

    public Hand getHand() {
        return hand;
    }

    /**
     * Adds the provided number to the mana. Can be negative.
     *
     * @param mana mana to add. Can be negative.
     */
    public void changeMana(int mana) {
        if (this.mana + mana < 0) {
            this.mana = 0;
        } else {
            this.mana += mana;
        }
    }

    /**
     * Adds the provided number to the movements. Can be negative.
     *
     * @param movements movements to add. Can be negative.
     */
    public void changeMovements(int movements) {
        if (this.movements + movements < 1) {
            this.movements = 1;
        } else {
            this.movements += movements;
        }
    }
    
    public void takeCard(Clock clock) {
        this.hand.addCard(clock.getDeckOf(this).takeCard());
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
