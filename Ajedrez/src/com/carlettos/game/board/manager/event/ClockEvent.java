package com.carlettos.game.board.manager.event;

import com.carlettos.game.board.manager.Clock;
import com.carlettos.game.board.property.Color;
import java.util.EventObject;

/**
 *
 * @author Carlettos
 */
public class ClockEvent extends EventObject{
    private final Color color;
    public ClockEvent(Clock source) {
        super(source);
        this.color = source.turnoDe().getColor();
    }

    public Color getColor() {
        return color;
    }

    @Override
    public Clock getSource() {
        return (Clock) source;
    }
}
