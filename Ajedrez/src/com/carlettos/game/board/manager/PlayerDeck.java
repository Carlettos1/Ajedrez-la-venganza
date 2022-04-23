package com.carlettos.game.board.manager;

import com.carlettos.game.board.card.Card;
import com.carlettos.game.board.player.Player;

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
