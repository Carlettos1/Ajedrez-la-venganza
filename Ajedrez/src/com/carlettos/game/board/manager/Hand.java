package com.carlettos.game.board.manager;

import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.card.Card;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * It's the hand.
 *
 * @author Carlos
 */
public class Hand {
    private final List<Card> cards;

    public Hand(Card... cards) {
        this.cards = new ArrayList<>(Arrays.asList(cards));
    }

    /**
     * Adds all the cards provided.
     *
     * @param cards cards to add.
     */
    public void addCards(Card... cards) {
        this.cards.addAll(Arrays.asList(cards));
    }
    
    public List<Card> getCards() {
        return Collections.unmodifiableList(cards);
    }
    
    /**
     * Removes a card from the hand.
     *
     * @param card the card to delete.
     * @return PASS if the card exists and has been removed from the hand,
     * FAIL other case.
     */
    public ActionResult removeCard(Card card) {
        if (cards.remove(card)) {
            return ActionResult.PASS;
        } else {
            return ActionResult.FAIL;
        }
    }

    /**
     * Removes the card at the given index. Accepts negative integers.
     *
     * @param index index of the card to delete, can be negative.
     * @return FAIL if the card cannot be removed (its an invalid index).
     * PASS if the card has been removed of this hand.
     */
    public ActionResult quitarCarta(int index) {
        if (index < cards.size() && index >= 0) {
            cards.remove(index);
            return ActionResult.PASS;
        } else if (index < 0) {
            int negIdx = cards.size() + index;
            if (negIdx < cards.size()) {
                cards.remove(negIdx);
                return ActionResult.PASS;
            }
        }
        return ActionResult.FAIL;
    }

    /**
     * Gets the card at the given index. Accepts negative integers.
     *
     * @param index index of the card to get, it can be negative.
     * @return null if the index isn't in the boundaries of this hand. If it's
     * a valid index, it returns the card (doesn't remove it).
     */
    public Card getCarta(int index) {
        if (index < cards.size() && index >= 0) {
            return cards.get(index);
        } else if (index < 0) {
            int negIdx = cards.size() + index;
            if (negIdx < cards.size()) {
                return cards.get(negIdx);
            }
        }
        return null;
    }

    public boolean hasCard(Card card) {
        return cards.contains(card);
    }

    public int getSize() {
        return cards.size();
    }

    @Override
    public String toString() {
        return "ManoManager{" + "cartas=" + cards + '}';
    }
}
