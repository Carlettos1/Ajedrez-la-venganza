package com.carlettos.game.board.clock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.carlettos.game.board.Deck;
import com.carlettos.game.board.PlayerDeck;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.listener.ClockEvent;
import com.carlettos.game.board.clock.listener.ClockListener;
import com.carlettos.game.gameplay.player.Player;
import com.carlettos.game.util.enums.Color;

/**
 * It represent the chess clock.
 *
 * @author Carlos
 */
public class Clock {

    private int turn;
    private int movements;
    private final Player[] players;
    private final PlayerDeck[] playerDecks;
    private final Deck deck;
    private final List<Event> events;
    private final List<ClockListener> listeners;

    /**
     * Construct a new clock in turn 1 and 0 movements, with the players 
     * provided.
     *
     * @param players players that use this clock.
     */
    public Clock(Player... players) {
        this.turn = 1;
        this.movements = 0;
        this.players = players;
        this.playerDecks = new PlayerDeck[players.length];
        for (int i = 0; i < players.length; i++) {
            this.playerDecks[i] = new PlayerDeck(this.players[i]);
        }
        this.deck = new Deck();
        this.events = new ArrayList<>();
        this.listeners = new ArrayList<>();
    }

    /**
     * Verifies if the player can play this turn.
     *
     * @param player the player.
     *
     * @return true if it can, false either case.
     */
    public boolean canPlay(Player player) {
        return turnOf().equals(player) && player.getMaxMovements() > movements;
    }

    /**
     * It has to execute everytime that a player makes a movement.
     */
    public void movement() {
        movements += 1;
        var event = new ClockEvent(this);
        this.listeners.forEach(l -> l.onEndMovement(event));
    }

    /**
     * Adds all events into the list of this clock.
     *
     * @param events events to add.
     */
    public void addEvents(Event... events) {
        this.events.addAll(Arrays.asList(events));
    }
    
    /**
     * Adds the event into the list of this clock.
     *
     * @param event event to add.
     */
    public void addEvent(Event event){
        this.events.add(event);
    }

    /**
     * It gets which player is currently playing.
     *
     * @return player playing.
     */
    public Player turnOf() {
        return players[(turn - 1) % players.length];
    }

    /**
     * Ends the turn and fire up events.
     */
    public void endTurn() {
        turn++;
        movements = 0;
        events.forEach(event -> event.info.reduceTurn());
        events.stream().filter(event -> event.info.getTurns() <= 0).forEach(Event::act);
        events.removeIf(event -> event.info.getTurns() <= 0);
        
        var event = new ClockEvent(this);
        this.listeners.forEach(l -> l.onEndTurn(event));
    }

    /**
     * Adds a listener to this clock.
     * 
     * @param l listener to add.
     */
    public void addListener(ClockListener l) {
        this.listeners.add(l);
    }

    /**
     * Get an unmodifiable list with all the events in it.
     * 
     * @return an unmodifiable list with all the events.
     */
    public List<Event> getEvents() {
        return Collections.unmodifiableList(events);
    }

    /**
     * Sort the list using the turns left to fire up the event, from the closest
     * to the latest.
     * 
     * @return an unmodifiable sorted list with all the events.
     */
    public List<Event> getEventosOrdenados() {
        events.sort(Event::compareTo);
        return getEvents();
    }

    /**
     * Gets a player of the given color.
     *
     * @param color color of the player.
     * 
     * @return a player with that color, if exists.
     */
    public Player getPlayerOfColor(Color color) {
        for (Player player : players) {
            if (player.getColor().equals(color)) {
                return player;
            }
        }
        throw new IllegalArgumentException("No existe jugador de color " + color);
    }

    public Player[] getPlayers() {
        return players;
    }

    public int getTurn() {
        return turn;
    }

    public int getMovements() {
        return movements;
    }
    
    public List<PlayerDeck> getPlayerDecks() {
    	return List.of(playerDecks);
    }
    
    public PlayerDeck getDeckOf(Player player) {
        for (PlayerDeck playerDeck : playerDecks) {
            if (playerDeck.getOwner().equals(player)) {
                return playerDeck;
            }
        }
        throw new IllegalArgumentException("player %s doesn't have a deck".formatted(player));
    }
    
    public void takeFromDeck(PlayerDeck playerDeck) {
        playerDeck.addCard(getDeck().takeCard());
    }
    
    public void takeFromDeck(Player player) {
        takeFromDeck(getDeckOf(player));
    }

    public Deck getDeck() {
        return deck;
    }

    @Override
    public String toString() {
        return events.toString();
    }
}
