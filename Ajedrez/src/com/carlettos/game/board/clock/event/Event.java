package com.carlettos.game.board.clock.event;

import com.carlettos.game.board.clock.Clock;
import com.carlettos.game.util.function.Action;

/**
 * An event is something that will happen in n turns, with n >= 1.
 * Note: this class has a natural ordering that is inconsistent with equals.
 *
 * @author Carlos
 *
 * @see Clock
 */
public abstract class Event implements Comparable<Event> {

    public final EventInfo info;

    /**
     * General constructor.
     *
     * @param info the info of the event.
     */
    private Event(EventInfo info) {
        this.info = info;
    }

    /**
     * Its the action that will occurr when turns = 0.
     */
    public abstract void act();

    @Override
    public String toString() {
        return "Evento{" + "turns=" + info.turns + ", name=" + info.name + ", point=" + info.point + '}';
    }

    @Override
    public int compareTo(Event other) {
        return Integer.compare(this.info.turns, other.info.turns);
    }

    /**
     * It creates a new event using the info provided and the action.
     * 
     * @param info information of the event for display.
     * @param action action which will be executed.
     * 
     * @return new Event.
     */
    public static Event create(EventInfo info, Action action){
        if(info == null || action == null){
            throw new IllegalArgumentException("Info nor action can be null in event");
        }
        return new Event(info) {
            @Override
            public void act() {
                action.execute();
            }
        };
    }
}
