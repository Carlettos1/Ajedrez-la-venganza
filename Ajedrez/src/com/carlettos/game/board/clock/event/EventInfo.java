package com.carlettos.game.board.clock.event;

import java.util.Objects;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.board.clock.TimeSpan;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.resource.TranslateResource;

/**
 * The info of an event.
 *
 * @author Carlettos
 */
public class EventInfo {
    public static final TranslateResource GENERIC_EVENT = new TranslateResource("generic_event.name");
    protected Time time;
    protected final String name;
    protected Point point;
    protected final AbstractBoard board;

    /**
     * It creates all the info for the event to work.
     *
     * @param turns time in turns to the event to happen.
     * @param name  name of the event.
     * @param point point of refference.
     * @param board board in which will happen.
     */
    public EventInfo(Time time, String name, Point point, AbstractBoard board) {
        if (time.isZero()) {
            throw new IllegalArgumentException("The time of an event can't be zero");
        }
        this.time = time;
        this.name = name;
        this.board = board;
        this.point = point;
    }

    @Override
    public int hashCode() {
        return (7 * name.hashCode() + point.hashCode() << 15) * 7;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        EventInfo other = (EventInfo) obj;
        return Objects.equals(name, other.name) && time.equals(other.time);
    }

    /**
     * Reduces the given {@link TimeSpan} by 1 unit.
     * 
     * @param spam unit of time to reduce
     * @see TimeSpan
     */
    public void tick(TimeSpan span) {
        switch (span) {
            case LAP -> this.time.substract(Time.lap(1));
            case TURN -> this.time.substract(Time.turn(1));
            case MOVEMENT -> this.time.substract(Time.movement(1));
        }
    }

    public Time getTime() {
        return time;
    }

    public String getName() {
        return name;
    }

    public Point getPoint() {
        return point;
    }

    public AbstractBoard getBoard() {
        return board;
    }

    public static EventInfo of(AbstractBoard board, Time time, String name, Point point) {
        return new EventInfo(time, name, point, board);
    }

    public static EventInfo of(AbstractBoard board, Time time, String name) {
        return of(board, time, name, new Point(0, 0));
    }

    public static EventInfo of(AbstractBoard board, Time time, Point point) {
        return of(board, time, GENERIC_EVENT.getTranslated(), point);
    }

    public static EventInfo of(AbstractBoard board, String name, Point point) {
        return of(board, Time.A_TURN, name, point);
    }

    public static EventInfo of(AbstractBoard board, Time time) {
        return of(board, time, GENERIC_EVENT.getTranslated(), new Point(0, 0));
    }

    public static EventInfo of(AbstractBoard board, String name) {
        return of(board, Time.A_TURN, name, new Point(0, 0));
    }

    public static EventInfo of(AbstractBoard board, Point point) {
        return of(board, Time.A_TURN, GENERIC_EVENT.getTranslated(), point);
    }

    public static EventInfo of(AbstractBoard board) {
        return of(board, Time.A_TURN, GENERIC_EVENT.getTranslated(), new Point(0, 0));
    }
}
