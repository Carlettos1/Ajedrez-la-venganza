package com.carlettos.game.board.clock.manager;

import java.util.List;

import com.carlettos.game.board.deck.PlayerDeck;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.enums.Color;

public interface IPlayerDeckManager {
    /**
     * Gets a player of the given color.
     *
     * @param color color of the player.
     *
     * @return a player with that color, if exists.
     */
    public abstract Player getPlayerOfColor(Color color);

    /**
     * Returns all the players managed by this clock.
     */
    public abstract Player[] getPlayers();

    /**
     * Returns the decks of all the players.
     */
    public abstract List<PlayerDeck> getPlayerDecks();

    /**
     * Returns the deck of the player provided.
     *
     * @param player player.
     */
    public abstract PlayerDeck getDeckOf(Player player);
}
