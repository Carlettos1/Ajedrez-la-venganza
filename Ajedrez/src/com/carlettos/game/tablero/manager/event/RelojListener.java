package com.carlettos.game.tablero.manager.event;

import java.util.EventListener;

/**
 *
 * @author Carlettos
 */
public interface RelojListener extends EventListener{
    void turnoTerminado(RelojEvent e);
}
