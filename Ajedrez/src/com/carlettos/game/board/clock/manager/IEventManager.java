package com.carlettos.game.board.clock.manager;

import java.util.List;

import com.carlettos.game.board.clock.event.Event;

public interface IEventManager {

    /**
     * Adds all events into the list of this clock.
     *
     * @param events events to add.
     */
    public abstract void addEvents(Event... events);

    /**
     * Adds the event into the list of this clock.
     *
     * @param event event to add.
     */
    public abstract void addEvent(Event event);

    /**
     * Get an unmodifiable list with all the events in it.
     * 
     * @return an unmodifiable list with all the events.
     */
    public abstract List<Event> getEvents();

    /**
     * Sort the list using the turns left to fire up the event, from the closest to
     * the latest.
     * 
     * @return an unmodifiable sorted list with all the events.
     */
    public abstract List<Event> getOrderedEvents();
}
