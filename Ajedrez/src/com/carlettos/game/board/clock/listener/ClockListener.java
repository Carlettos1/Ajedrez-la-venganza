package com.carlettos.game.board.clock.listener;

import java.util.EventListener;

/**
 * Interface that listens to clock events.
 *
 * @author Carlettos
 */
public interface ClockListener extends EventListener {

    /**
     * It excecutes at the end of every lap.
     *
     * @param e clock event.
     */
    void lapEnded(ClockEvent e);

    /**
     * It excecutes at the end of every turn.
     *
     * @param e clock event.
     */
    void turnEnded(ClockEvent e);

    /**
     * It excecutes at the end of every movement.
     *
     * @param e clock event.
     */
    void movementEnded(ClockEvent e);
}
