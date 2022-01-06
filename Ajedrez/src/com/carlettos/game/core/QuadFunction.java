package com.carlettos.game.core;

import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import java.awt.Point;

/**
 *
 * @author Carlos
 */
@FunctionalInterface
public interface QuadFunction<R> { //todo: quinfunction
    public static final QuadFunction<ActionResult> PASS = (accion, tablero, pieza, inicio, final_) -> {
        return ActionResult.PASS;
    };
    public static final QuadFunction<ActionResult> FAIL = (accion, tablero, pieza, inicio, final_) -> {
        return ActionResult.FAIL;
    };
    public static final QuadFunction<Boolean> TRUE = (accion, tablero, pieza, inicio, final_) -> {
        return true;
    };
    public static final QuadFunction<Boolean> FALSE = (accion, tablero, pieza, inicio, final_) -> {
        return false;
    };
    
    public R apply(Accion accion, Tablero tablero, Pieza pieza, Point inicio, Point final_);
}
