package com.carlettos.game.board.clock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.listener.ClockEvent;
import com.carlettos.game.board.clock.listener.ClockListener;
import com.carlettos.game.board.deck.Deck;
import com.carlettos.game.board.deck.PlayerDeck;
import com.carlettos.game.gameplay.card.Card;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Tuple;

public class Clock extends AbstractClock {
    protected final Deck centralDeck;
    protected final List<Event> events;
    // TODO: eventQueue, in other thread
    protected final List<ClockListener> listeners;
    protected final List<Tuple<Player, Card>> cardsOnBoard;

    /**
     * Construct a new clock in turn 1 and 0 movements, with the players provided.
     *
     * @param players players that use this clock.
     */
    public Clock(Player... players) {
        super(players);
        this.centralDeck = new Deck();
        this.events = new ArrayList<>();
        this.listeners = new ArrayList<>();
        this.cardsOnBoard = new ArrayList<>();
    }

    @Override
    public List<Tuple<Player, Card>> getCardsOnBoard() {
        return this.cardsOnBoard;
    }

    @Override
    public void addCardToBoard(Player from, Card card) {
        cardsOnBoard.add(Tuple.of(from, card));
    }

    @Override
    public boolean containsExactCardOnBoard(Player from, Card card) {
        return cardsOnBoard.contains(Tuple.of(from, card));
    }

    @Override
    public boolean containsCardOnBoard(Player from, Card card) {
        return cardsOnBoard.stream().anyMatch(
                tuple -> tuple.x.getColor().equals(from.getColor()) && tuple.y.getData().name().equals(card.getData().name()));
    }

    @Override
    public boolean containsCardOnBoard(Card card) {
        return this.containsCardOnBoard(turnOf(), card);
    }

    @Override
    public boolean canPlay(Player player) {
        return turnOf().equals(player) && player.getMaxMovements() > movements;
    }

    @Override
    public void movement() {
        super.movement();
        var event = new ClockEvent(this);
        this.listeners.forEach(l -> l.onEndMovement(event));
    }

    @Override
    public void addEvents(Event... events) {
        this.events.addAll(Arrays.asList(events));
    }

    @Override
    public void addEvent(Event event) {
        this.events.add(event);
    }

    @Override
    public void tick() {
        super.tick();
        events.forEach(Event::tick);
        events.stream().filter(Event::isReady).forEach(Event::act);
        events.removeIf(Event::isReady);

        var event = new ClockEvent(this);
        this.listeners.forEach(l -> l.onEndTurn(event));
    }

    @Override
    public void addListener(ClockListener l) {
        this.listeners.add(l);
    }

    @Override
    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }

    @Override
    public List<Event> getOrderedEvents() {
        events.sort(Event::compareTo);
        return getEvents();
    }

    @Override
    public void takeFromCentralDeck(PlayerDeck playerDeck) {
        playerDeck.addCard(getCentralDeck().takeCard());
    }

    @Override
    public void takeFromCentralDeck(Player player) {
        takeFromCentralDeck(getDeckOf(player));
    }

    @Override
    public Deck getCentralDeck() {
        return centralDeck;
    }

    @Override
    public String toString() {
        return events.toString();
    }
}
