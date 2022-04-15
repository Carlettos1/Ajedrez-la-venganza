package com.carlettos.game.board.manager.event;

import java.util.EventListener;

/**
 *
 * @author Carlettos
 */
public interface ClockListener extends EventListener{
    void turnoTerminado(ClockEvent e);
}
