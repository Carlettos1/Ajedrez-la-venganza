package com.carlettos.game.board.card;

import com.carlettos.game.core.Tuple;
import com.carlettos.game.board.player.Player;
import com.carlettos.game.board.manager.clock.Clock;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import java.util.Objects;

/**
 * It's the representation of a card.
 *
 * @author Carlos
 *
 * @see Player
 */
public abstract class Card {
    protected final String name;
    protected final String description;
    protected int manaCost;

    /**
     * General constructor.
     *
     * @param name name of the card.
     * @param description detailed description of the card.
     * @param manaCost the cost in mana.
     */
    public Card(String name, String description, int manaCost) {
        this.name = name;
        this.description = description;
        this.manaCost = manaCost;
    }

    /**
     * Verifies if the card can be used.
     *
     * @param point refference point.
     * @param board board in which occurs.
     * @param caster caster of the card.
     *
     * @return ActionResult.PASS if can be used, FAIL other case.
     */
    public abstract ActionResult canUse(Point point, Board board, Player caster);

    /**
     * Uses the card.
     *
     * @param point refference point.
     * @param board board in which occurs.
     * @param caster caster of the card.
     */
    public abstract void use(Point point, Board board, Player caster);

    /**
     * Adds the amount of mana to the cost of this card. Can be negative.
     *
     * @param mana mana to add.
     */
    public void changeManaCost(int mana) {
        if(this.manaCost + mana < 0){
            this.manaCost = 0;
        } else {
            this.manaCost += mana;
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getCost() {
        return manaCost;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 23 * hash + Objects.hashCode(this.name);
        hash = 23 * hash + this.manaCost;
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
        final Card other = (Card) obj;
        if (this.manaCost != other.manaCost) {
            return false;
        }
        return Objects.equals(this.name, other.name);
    }
}
