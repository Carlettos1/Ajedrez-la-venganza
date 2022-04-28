package com.carlettos.game.board.clock.listener;

import java.util.EventObject;

import com.carlettos.game.board.clock.Clock;
import com.carlettos.game.util.enums.Color;

/**
 * Clock event. WIP.
 *
 * @author Carlettos
 */
public class ClockEvent extends EventObject {
	private static final long serialVersionUID = 1621816868141513013L;
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
