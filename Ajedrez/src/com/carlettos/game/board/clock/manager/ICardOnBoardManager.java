package com.carlettos.game.board.clock.manager;

import java.util.Arrays;
import java.util.List;

import com.carlettos.game.gameplay.card.Card;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Tuple;

public interface ICardOnBoardManager {

    List<Tuple<Player, Card>> getCardsOnBoard();

    /**
     * Adds a card to the board.
     */
    void addCardToBoard(Player from, Card card);

    /**
     * Checks if the board contains the card, just checking the color of the player
     * and the name of the card.
     */
    boolean boardContains(Player from, Card card);

    /**
     * Checks if the board contains the card, checking the color of the current
     * player playing and the name of the card.
     */
    boolean boardContains(Card card);

    /**
     * Adds all cards to the board.
     */
    default void addCardsToBoard(Player from, Card... cards) {
        this.addCardsToBoard(from, Arrays.asList(cards));
    }
    
    /**
     * Adds all cards to the board.
     */
    default void addCardsToBoard(Player from, List<Card> cards) {
        cards.forEach(c -> this.addCardToBoard(from, c));
    }

    /**
     * Checks if the board contains the exact card. Matching the player and the card
     * data.
     */
    default boolean boardContainsAny(Player from, Card... cards) {
        return this.boardContainsAny(from, Arrays.asList(cards));
    }

    /**
     * Checks if the board contains the exact card. Matching the player and the card
     * data.
     */
    default boolean boardContainsAny(Player from, List<Card> cards) {
        return cards.stream().anyMatch(c -> this.boardContains(from, c));
    }

    /**
     * Checks if the board contains the card, just checking the color of the player
     * and the name of the card.
     */
    default boolean boardContainsAll(Player from, Card... cards) {
        return this.boardContainsAll(from, Arrays.asList(cards));
    }

    /**
     * Checks if the board contains the card, just checking the color of the player
     * and the name of the card.
     */
    default boolean boardContainsAll(Player from, List<Card> cards) {
        return cards.stream().allMatch(c -> this.boardContains(from, c));
    }

    /**
     * Checks if the board contains the card, checking the color of the current
     * player playing and the name of the card.
     */
    default boolean boardContainsAny(Card... cards) {
        return this.boardContainsAny(Arrays.asList(cards));
    }

    /**
     * Checks if the board contains the card, checking the color of the current
     * player playing and the name of the card.
     */
    default boolean boardContainsAny(List<Card> cards) {
        return cards.stream().anyMatch(c -> this.boardContains(c));
    }

    /**
     * Checks if the board contains the card, checking the color of the current
     * player playing and the name of the card.
     */
    default boolean boardContainsAll(Card... cards) {
        return this.boardContainsAll(Arrays.asList(cards));
    }

    /**
     * Checks if the board contains the card, checking the color of the current
     * player playing and the name of the card.
     */
    default boolean boardContainsAll(List<Card> cards) {
        return cards.stream().allMatch(c -> this.boardContains(c));
    }
}
