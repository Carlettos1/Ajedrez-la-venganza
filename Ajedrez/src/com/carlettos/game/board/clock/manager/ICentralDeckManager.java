package com.carlettos.game.board.clock.manager;

import com.carlettos.game.board.deck.Deck;
import com.carlettos.game.board.deck.PlayerDeck;
import com.carlettos.game.gameplay.player.Player;

public interface ICentralDeckManager {

    /**
     * Moves a card from the central deck to a player deck.
     *
     * @param playerDeck deck to give a card.
     */
    public abstract void takeFromCentralDeck(PlayerDeck playerDeck);

    /**
     * Moves a card from the central deck to a player deck.
     *
     * @param player player to give a card.
     */
    public abstract void takeFromCentralDeck(Player player);

    /**
     * Returns the central deck.
     */
    public abstract Deck getCentralDeck();
}
