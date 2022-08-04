package com.carlettos.game.board.clock.manager;

import java.util.List;

import com.carlettos.game.gameplay.card.Card;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Tuple;

public interface ICardOnBoardManager {

    List<Tuple<Player, Card>> getCardsOnBoard();

    /**
     * Adds a card to the board.
     */
    public abstract void addCardToBoard(Player from, Card card);

    /**
     * Checks if the board contains the exact card. Matching the player and the card
     * data.
     */
    public abstract boolean containsExactCardOnBoard(Player from, Card card);

    /**
     * Checks if the board contains the card, just checking the color of the player
     * and the name of the card.
     */
    public abstract boolean containsCardOnBoard(Player from, Card card);

    /**
     * Checks if the board contains the card, checking the color of the current
     * player playing and the name of the card.
     */
    public abstract boolean containsCardOnBoard(Card card);
}
