package com.carlettos.game.board.clock.listener;

@FunctionalInterface
public interface LapListener extends ClockListener {
    @Override
    default void turnEnded(ClockEvent e) {}
    @Override
    default void movementEnded(ClockEvent e) {}
}
