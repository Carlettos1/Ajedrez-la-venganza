package com.carlettos.game.board.clock;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.listener.ClockListener;
import com.carlettos.game.board.deck.Deck;
import com.carlettos.game.board.deck.PlayerDeck;
import com.carlettos.game.gameplay.card.Card;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Tuple;

/**
 * It's a dummy clock, used for boards that doesn't have a complicated clock.
 */
public class FalseClock extends AbstractClock {
    private static final List<Event> events = new ArrayList<>();
    private static final Deck deck = new Deck();
    private static final List<Tuple<Player, Card>> board = new ArrayList<>();

    public FalseClock(Player... players) {
        super(players);
    }

    public FalseClock(AbstractClock other) {
        super(other.players);
    }

    @Override
    public List<Tuple<Player, Card>> getCardsOnBoard() {
        return board;
    }

    @Override
    public void takeFromCentralDeck(PlayerDeck playerDeck) {}

    @Override
    public void takeFromCentralDeck(Player player) {}

    @Override
    public Deck getCentralDeck() {
        return deck;
    }

    @Override
    public void addEvents(Event... events) {}

    @Override
    public void addEvent(Event event) {}

    @Override
    public List<Event> getEvents() {
        return events;
    }

    @Override
    public List<Event> getOrderedEvents() {
        return events;
    }

    @Override
    public void addCardToBoard(Player from, Card card) {}

    @Override
    public boolean containsExactCardOnBoard(Player from, Card card) {
        return false;
    }

    @Override
    public boolean containsCardOnBoard(Player from, Card card) {
        return false;
    }

    @Override
    public boolean containsCardOnBoard(Card card) {
        return false;
    }

    @Override
    public void addListener(ClockListener l) {}
}
