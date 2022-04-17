package com.carlettos.game.board.manager.clock.listener;

import java.util.EventListener;

/**
 *
 * @author Carlettos
 */
public interface ClockListener extends EventListener{
    void onEndTurn(ClockEvent e);
}
