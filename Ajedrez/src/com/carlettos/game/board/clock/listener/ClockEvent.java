package com.carlettos.game.board.clock.listener;

import com.carlettos.game.board.clock.Clock;
import com.carlettos.game.util.enums.Color;
import java.util.EventObject;

/**
 * Clock event. WIP.
 *
 * @author Carlettos
 */
public class ClockEvent extends EventObject {

    private final Color color;

    /**
     * General constructor.
     *
     * @param source the clock source of this event.
     */
    public ClockEvent(Clock source) {
        super(source);
        this.color = source.turnOf().getColor();
    }

    public Color getColor() {
        return color;
    }

    @Override
    public Clock getSource() {
        return (Clock) source;
    }
}
