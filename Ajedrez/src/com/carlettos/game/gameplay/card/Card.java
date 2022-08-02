package com.carlettos.game.gameplay.card;

import java.util.Objects;

import com.carlettos.game.board.SquareBoard;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.IResourceKey;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.ResourceLocation;
import com.carlettos.game.util.enums.ActionResult;

/**
 * It's the representation of a card.
 *
 * @author Carlos
 *
 * @see Player
 */
public abstract class Card implements IResourceKey {
    // todo: record cardData
    protected final String key;
    protected final ResourceLocation name;
    protected final ResourceLocation description;
    protected int manaCost;

    /**
     * General constructor.
     *
     * @param name        name of the card.
     * @param description detailed description of the card.
     * @param manaCost    the cost in mana.
     */
    protected Card(String key, int manaCost) {
        this.key = key;
        this.name = new ResourceLocation("card.name.".concat(key));
        this.description = new ResourceLocation("card.description.".concat(key));
        this.manaCost = manaCost;
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
    public abstract ActionResult canUse(Point point, SquareBoard board, Player caster);

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
    public ActionResult commonCanUse(Point point, SquareBoard board, Player caster) {
        if (caster.getMana() < this.manaCost) { return ActionResult.FAIL; }
        if (board.getClock().turnOf().getHand().hasCard(this) && board.getClock().turnOf().equals(caster)) {
            return ActionResult.PASS;
        }
        return ActionResult.FAIL;
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
        caster.changeMana(-getCost());
        board.movement();
    }

    /**
     * Adds the amount of mana to the cost of this card. Can be negative.
     *
     * @param mana mana to add.
     */
    public void changeManaCost(int mana) {
        if (this.manaCost + mana < 0) {
            this.manaCost = 0;
        } else {
            this.manaCost += mana;
        }
    }

    @Override
    public String getBaseKey() {
        return key;
    }

    public String getName() {
        return name.getTranslated();
    }

    public String getDescription() {
        return description.getTranslated();
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
    public String toString() {
        return "Card{" + "name=" + name + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) { return true; }
        if (obj == null) { return false; }
        if (getClass() != obj.getClass()) { return false; }
        final Card other = (Card) obj;
        if (this.manaCost != other.manaCost) { return false; }
        return Objects.equals(this.name, other.name);
    }
}
