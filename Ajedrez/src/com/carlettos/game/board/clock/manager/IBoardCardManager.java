package com.carlettos.game.board.clock.manager;

import com.carlettos.game.gameplay.card.Card;
import com.carlettos.game.gameplay.player.Player;

public interface IBoardCardManager {

    /**
     * Adds a card to the board.
     */
    public abstract void addCardToBoard(Player from, Card card);

    /**
     * Checks if the board contains the exact card. Matching the player and the card
     * data.
     */
    public abstract boolean boardContainsExactCard(Player from, Card card);

    /**
     * Checks if the board contains the card, just checking the color of the player
     * and the name of the card.
     */
    public abstract boolean boardContainsCard(Player from, Card card);

    /**
     * Checks if the board contains the card, checking the color of the current
     * player playing and the name of the card.
     */
    public abstract boolean boardContainsCard(Card card);
}
