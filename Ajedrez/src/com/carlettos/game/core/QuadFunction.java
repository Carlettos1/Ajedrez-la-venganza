package com.carlettos.game.core;

import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import java.awt.Point;

/**
 *
 * @author Carlos
 */
@FunctionalInterface
public interface QuadFunction<R> {
    public R apply(Accion accion, Tablero tablero, Pieza pieza, Point point);
}
