package com.carlettos.game.board.clock.event;

import java.util.Objects;

import com.carlettos.game.board.clock.AbstractClock;
import com.carlettos.game.board.clock.TimeSpan;
import com.carlettos.game.util.function.Action;

/**
 * An event is something that will happen in n turns, with n >= 1.
 *
 * @author Carlos
 *
 * @see AbstractClock
 */
public abstract class Event {

    public final EventInfo info;

    /**
     * General constructor.
     *
     * @param info the info of the event.
     */
    protected Event(EventInfo info) {
        this.info = info;
    }

    /**
     * Its the action that will occurr when turns = 0.
     */
    public abstract void act();

    public void tick(TimeSpan span) {
        this.info.tick(span);
    }

    public boolean canExecute() {
        return this.info.getTime().isZero();
    }

    @Override
    public String toString() {
        return "Evento{" + "time=" + info.time + ", name=" + info.name + ", point=" + info.point + '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Event other = (Event) obj;
        return Objects.equals(info, other.info);
    }

    @Override
    public int hashCode() {
        return Objects.hash(info);
    }

    /**
     * It creates a new event using the info provided and the action.
     *
     * @param info   information of the event for display.
     * @param action action which will be executed.
     *
     * @return new Event.
     */
    public static Event create(EventInfo info, Action action) {
        if (info == null || action == null) {
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
