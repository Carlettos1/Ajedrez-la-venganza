package com.carlettos.game.board.manager;

import com.carlettos.game.board.card.Card;
import java.util.function.Consumer;

/**
 * It's the hand.
 *
 * @author Carlos
 */
public class Hand extends Deck {

    public Hand() {
    }

    public Hand(Card... cards) {
        super(cards);
    }
    
    public void forEach(Consumer<? super Card> action){
        this.cards.forEach(action);
    }
}
