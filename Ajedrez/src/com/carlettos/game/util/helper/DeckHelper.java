package com.carlettos.game.util.helper;

import com.carlettos.game.board.Deck;
import com.carlettos.game.gameplay.card.Card;
import java.util.function.Supplier;

/**
 *
 * @author Carlettos
 */
public class DeckHelper {

    /**
     * Adds a certain ammount of the same card to the deck.
     *
     * @param deck deck to add the cards
     * @param supplier supplier of the card
     * @param times ammount of cards to add
     */
    public static final void addTo(Supplier<Card> supplier, int times, Deck deck) {
        for (int i = 0; i < times; i++) {
            deck.addCard(supplier.get());
        }
    }
    
    public static final void addToAll(Supplier<Card> supplier, int times, Deck... decks) {
        for (int i = 0; i < times; i++) {
            for (Deck deck : decks) {
                deck.addCard(supplier.get());
            }
        }
    }
}
