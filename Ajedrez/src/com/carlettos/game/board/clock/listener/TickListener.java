package com.carlettos.game.board.clock.listener;

@FunctionalInterface
public interface TickListener extends ClockListener {
    @Override
    default void lapEnded(ClockEvent e) {}
    @Override
    default void turnEnded(ClockEvent e) {}
    @Override
    default void movementEnded(ClockEvent e) {}
    
    void tickEnded(ClockEvent e);
}
