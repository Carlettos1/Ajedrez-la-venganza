package com.carlettos.game.core.function;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.core.Point;

/**
 *
 * @author Carlos
 * @param <R> ClaseResultado.
 */
@FunctionalInterface
public interface QuadFunction<R> {
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
    
    public R apply(Action accion, Board tablero, Piece pieza, Point inicio, Point final_);
}
