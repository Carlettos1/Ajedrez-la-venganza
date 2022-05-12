package com.carlettos.game.board.cards;

import com.carlettos.game.gameplay.card.Card;
import com.carlettos.game.gameplay.player.Player;

/**
 *
 * @author Carlettos
 */
public class PlayerDeck extends Deck {
    protected final Player owner;

    public PlayerDeck(Player owner) {
        this.owner = owner;
    }

    public PlayerDeck(Player owner, Card... cards) {
        super(cards);
        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }
}
