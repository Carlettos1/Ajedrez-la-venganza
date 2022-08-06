package com.carlettos.game.gameplay.card;

import java.util.Objects;

import com.carlettos.game.board.SquareBoard;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Point;

/**
 * It's the representation of a card.
 *
 * @author Carlos
 *
 * @see Player
 */
public abstract class Card {
    protected final CardData data;

    /**
     * General constructor.
     *
     * @param name        name of the card.
     * @param description detailed description of the card.
     * @param manaCost    the cost in mana.
     */
    protected Card(String key, int manaCost) {
        this.data = new CardData(key, manaCost);
    }

    /**
     * Verifies if the card can be used.
     *
     * @param point  refference point.
     * @param board  board in which occurs.
     * @param caster caster of the card.
     *
     * @return ActionResult.PASS if can be used, FAIL other case.
     */
    public abstract boolean canUse(Point point, SquareBoard board, Player caster);

    /**
     * Uses the card.
     *
     * @param point  refference point.
     * @param board  board in which occurs.
     * @param caster caster of the card.
     */
    public abstract void use(Point point, SquareBoard board, Player caster);

    /**
     * Utility method, checks if the player has enough mana and if the player using
     * the card is the owner of this card and is currently playing.
     *
     * @param point  refference point.
     * @param board  board in which occurs.
     * @param caster caster of the card.
     *
     * @return ActionResult.PASS if can be used, FAIL other case.
     */
    public boolean commonCanUse(Point point, SquareBoard board, Player caster) {
        if (caster.getMana() < this.data.manaCost()) { return false; }
        if (board.getClock().turnOf().getHand().hasCard(this) && board.getClock().turnOf().equals(caster)) {
            return true;
        }
        return false;
    }

    /**
     * Utility method, it substracts mana from the caster, remove this card from the
     * caster and notifies the board of a movement.
     *
     * @param point  refference point.
     * @param board  board in which occurs.
     * @param caster caster of the card.
     */
    public void commonUse(Point point, SquareBoard board, Player caster) {
        caster.getHand().removeCard(this);
        caster.changeMana(-data.manaCost());
        board.movement();
    }

    public CardData getData() {
        return data;
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public String toString() {
        return "Card{" + "name=" + data.name() + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if ((obj == null) || (getClass() != obj.getClass())) { return false; }
        final Card other = (Card) obj;
        if (this.data.manaCost() != other.data.manaCost()) { return false; }
        return Objects.equals(this.data.name(), other.data.name());
    }
}
