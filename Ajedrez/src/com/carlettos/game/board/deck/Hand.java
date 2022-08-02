package com.carlettos.game.board.deck;

import java.util.function.Consumer;

import com.carlettos.game.gameplay.card.Card;

/**
 * It's the hand.
 *
 * @author Carlos
 */
public class Hand extends Deck {

    public Hand() {}

    public Hand(Card... cards) {
        super(cards);
    }

    public void forEach(Consumer<? super Card> action) {
        this.cards.forEach(action);
    }
}
