package com.carlettos.game.board.clock.listener;

import java.util.EventListener;

public class ClockEventMulticaster implements ClockListener {
    protected final EventListener a;
    protected final EventListener b;

    protected ClockEventMulticaster(EventListener a, EventListener b) {
        this.a = a;
        this.b = b;
    }

    @Override
    public void lapEnded(ClockEvent e) {
        ((ClockListener) a).lapEnded(e);
        ((ClockListener) b).lapEnded(e);
    }

    @Override
    public void turnEnded(ClockEvent e) {
        ((ClockListener) a).turnEnded(e);
        ((ClockListener) b).turnEnded(e);
    }

    @Override
    public void movementEnded(ClockEvent e) {
        ((ClockListener) a).movementEnded(e);
        ((ClockListener) b).movementEnded(e);
    }

    protected static EventListener addInternal(EventListener a, EventListener b) {
        if (a == null)
            return b;
        if (b == null)
            return a;
        return new ClockEventMulticaster(a, b);
    }

    public static ClockListener add(ClockListener a, ClockListener b) {
        return (ClockListener) addInternal(a, b);
    }
}
