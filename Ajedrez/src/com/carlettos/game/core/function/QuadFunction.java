package com.carlettos.game.core.function;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.core.Point;

/**
 *
 * @author Carlos
 * @param <R> Result.
 */
@FunctionalInterface
public interface QuadFunction<R> {
    public static final QuadFunction<ActionResult> PASS = (action, board, piece, start, end) -> {
        return ActionResult.PASS;
    };
    public static final QuadFunction<ActionResult> FAIL = (action, board, piece, start, end) -> {
        return ActionResult.FAIL;
    };
    public static final QuadFunction<Boolean> TRUE = (action, board, piece, start, end) -> {
        return true;
    };
    public static final QuadFunction<Boolean> FALSE = (action, board, piece, start, end) -> {
        return false;
    };
    
    public R apply(Action action, Board board, Piece piece, Point start, Point end);
}
