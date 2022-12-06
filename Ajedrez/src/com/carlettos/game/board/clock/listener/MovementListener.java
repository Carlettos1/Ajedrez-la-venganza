package com.carlettos.game.board.clock.listener;

@FunctionalInterface
public interface MovementListener extends ClockListener {
    @Override
    default void lapEnded(ClockEvent e) {}
    @Override
    default void turnEnded(ClockEvent e) {}
}
