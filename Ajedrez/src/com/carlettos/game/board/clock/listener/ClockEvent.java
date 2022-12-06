package com.carlettos.game.board.clock.listener;

import java.util.EventObject;

import com.carlettos.game.board.clock.AbstractClock;
import com.carlettos.game.util.enums.Color;

/**
 * Clock event. WIP. XXX XXX
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
    public ClockEvent(AbstractClock source) {
        super(source);
        this.color = source.getCurrentlyPlaying().getColor();
    }

    public Color getColor() {
        return color;
    }

    @Override
    public AbstractClock getSource() {
        return (AbstractClock) source;
    }
}
