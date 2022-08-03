package com.carlettos.game.board.clock.manager;

import com.carlettos.game.board.clock.listener.ClockListener;

public interface IClockListenerManager {

    /**
     * Adds a listener to this clock.
     *
     * @param l listener to add.
     */
    public abstract void addListener(ClockListener l);
}
