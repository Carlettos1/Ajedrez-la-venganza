package com.carlettos.game.board.clock.listener;

@FunctionalInterface
public interface TurnListener extends ClockListener {
    @Override
    default void lapEnded(ClockEvent e) {}
    @Override
    default void movementEnded(ClockEvent e) {}
}
