package com.carlettos.game.board.clock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.listener.ClockEvent;
import com.carlettos.game.board.clock.listener.ClockEventMulticaster;
import com.carlettos.game.board.clock.listener.ClockListener;
import com.carlettos.game.board.clock.listener.LapListener;
import com.carlettos.game.board.clock.listener.MovementListener;
import com.carlettos.game.board.clock.listener.TickListener;
import com.carlettos.game.board.clock.listener.TurnListener;
import com.carlettos.game.board.deck.Deck;
import com.carlettos.game.board.deck.PlayerDeck;
import com.carlettos.game.gameplay.card.Card;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.Tuple;

public class Clock extends AbstractClock {
    protected final Deck centralDeck;
    protected final List<Event> events;
    protected ClockListener clockListener;
    protected final List<Tuple<Player, Card>> cardsOnBoard;

    public static final int MOVEMENT_ENDED = 0;
    public static final int TURN_ENDED = 1;
    public static final int LAP_ENDED = 2;
    public static final int TICK_ENDED = 3;

    /**
     * Construct a new clock in turn 1 and 0 movements, with the players provided.
     *
     * @param players players that use this clock.
     */
    public Clock(Player... players) {
        super(players);
        this.centralDeck = new Deck();
        this.events = new ArrayList<>();
        this.cardsOnBoard = new ArrayList<>();
        this.initEvents();
    }
    
    protected void initEvents() {
        this.addClockListener(new MovementListener() {
            @Override
            public void movementEnded(ClockEvent e) {
                events.forEach(t -> t.tick(TimeSpan.MOVEMENT));
            }
        });
        this.addClockListener(new TurnListener() {
            @Override
            public void turnEnded(ClockEvent e) {
                events.forEach(t -> t.tick(TimeSpan.TURN));
            }
        });
        this.addClockListener(new LapListener() {
            @Override
            public void lapEnded(ClockEvent e) {
                events.forEach(t -> t.tick(TimeSpan.LAP));
            }
        });
        this.addClockListener(new TickListener() {
            @Override
            public void tickEnded(ClockEvent e) {
                events.stream().filter(Event::canExecute).forEach(Event::act);
                events.removeIf(Event::canExecute);
            }
        });
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
    public boolean boardContains(Player from, Card card) {
        return cardsOnBoard.stream().anyMatch(tuple -> tuple.x.getColor().equals(from.getColor())
                && tuple.y.getData().name().equals(card.getData().name()));
    }

    @Override
    public boolean boardContains(Card card) {
        return this.boardContains(getCurrentlyPlaying(), card);
    }

    @Override
    public boolean canPlay(Player player) {
        return getCurrentlyPlaying().equals(player) && player.getMaxMovements() > movements;
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
    public synchronized void tick(TimeSpan span) {
        this.processEvent(TICK_ENDED, new ClockEvent(this));
        switch (span) {
            case MOVEMENT -> this.processEvent(MOVEMENT_ENDED, new ClockEvent(this));
            case TURN -> this.processEvent(TURN_ENDED, new ClockEvent(this));
            case LAP -> this.processEvent(LAP_ENDED, new ClockEvent(this));
        }
        super.tick(span);
    }

    @Override
    public synchronized void tick() {
        super.tick();
        this.processEvent(TURN_ENDED, new ClockEvent(this));
    }

    @Override
    public void addClockListener(ClockListener l) {
        if (l == null) { return; }
        this.clockListener = ClockEventMulticaster.add(this.clockListener, l);
    }

    protected void processEvent(int id, ClockEvent event) {
        if (id == TICK_ENDED) {
            this.clockListener.movementEnded(event);
            this.clockListener.turnEnded(event);
            this.clockListener.lapEnded(event);
        }
        switch (id) {
            case MOVEMENT_ENDED -> this.clockListener.movementEnded(event);
            case TURN_ENDED -> this.clockListener.turnEnded(event);
            case LAP_ENDED -> this.clockListener.lapEnded(event);
        }
    }

    @Override
    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }

    @Override
    public List<Event> getOrderedEvents() {
        events.sort((o1, o2) -> o1.info.getTime().compareTo(o2.info.getTime(), this));
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
