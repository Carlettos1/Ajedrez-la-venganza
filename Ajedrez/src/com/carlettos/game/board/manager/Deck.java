package com.carlettos.game.board.manager;

import com.carlettos.game.board.card.Card;
import com.carlettos.game.board.card.invocation.SummonWarlock;
import com.carlettos.game.board.card.utility.AddMovement;
import com.carlettos.game.core.helper.DeckHelper;
import com.carlettos.game.core.helper.MathHelper;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 *
 * @author Carlettos
 */
public class Deck {
    protected final List<Card> cards;
    
    public Deck(){
        this.cards = new ArrayList<>(30);
    }

    public Deck(Card... cards) {
        this.cards = new ArrayList<>(Arrays.asList(cards));
    }
    
    /**
     * Adds the provided card to the deck.
     *
     * @param card card to add.
     * @throws NullPointerException if the card is null.
     */
    public void addCard(Card card) {
        Objects.requireNonNull(card, "Cannot add null card to deck");
        this.cards.add(card);
    }
    
    /**
     * Adds the provided cards to the deck.
     *
     * @param cards cards to add
     * @throws NullPointerException if any card is null.
     */
    public void addAll(Card... cards) {
        for (Card card : cards) {
            Objects.requireNonNull(card);
        }
        this.cards.addAll(Arrays.asList(cards));
    }
    
    /**
     * Removes the provided card from this deck.
     *
     * @param card card to remove.
     * @throws NullPointerException if the card is null.
     * @throws IllegalArgumentException if the deck doesn't contain the card.
     */
    public void removeCard(Card card) {
        Objects.requireNonNull(card, "Cannot remove null card to deck");
        if (!this.cards.contains(card)) {
            throw new IllegalArgumentException("Deck doesn't contain card %s".formatted(cards));
        }
        this.cards.remove(card);
    }
    
    /**
     * Removes the card at the given index, can be negative.
     *
     * @param idx index to remove, can be negative.
     * @throws IllegalStateException if the deck is empty.
     * @throws IndexOutOfBoundsException if the index >= than the size or 
     * {@literal <} 0.
     */
    public void removeCard(int idx) {
        if (this.cards.isEmpty()) {
            throw new IllegalStateException("Trying to remove a card from an empty deck");
        }
        int index;
        if (idx >= 0) {
            index = idx;
        } else {
            index = this.cards.size() + idx;
        }
        MathHelper.requireInBounds(index, 0, this.cards.size());
        this.cards.remove(index);
    }
    
    /**
     * Removes and returns the last card of this deck.
     *
     * @return the last card of this deck.
     * @throws IllegalStateException if the deck is empty.
     */
    public Card takeCard() {
        if (this.cards.isEmpty()) {
            throw new IllegalStateException("Trying to take a card from an empty deck");
        }
        return this.cards.remove(cards.size() - 1);
    }
    
    public boolean hasCard(Card card) {
        return this.cards.contains(card);
    }
    
    public int getCardCount() {
        return this.cards.size();
    }
    
    public void shuffle() {
        Collections.shuffle(cards);
    }

    public static void defaultInit(Deck deck) {
        if (deck.getCardCount() != 0) {
            throw new IllegalStateException("Deck cannot be default initialized with cards on it");
        }
        DeckHelper.addTo(() -> new AddMovement(), 4, deck);
        DeckHelper.addTo(() -> new SummonWarlock(), 8, deck);
        deck.shuffle();
    }

    @Override
    public String toString() {
        return "Deck{" + "cards=" + cards + '}';
    }
}
