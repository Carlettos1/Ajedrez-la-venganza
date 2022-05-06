package com.carlettos.game.util.function;

import com.carlettos.game.board.Board;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.ActionResult;

/**
 *
 * @author Carlos
 * @param <R> Result.
 */
@FunctionalInterface
public interface QuadFunction<R> {
    public static final QuadFunction<ActionResult> PASS = (action, board, piece, start, end) -> ActionResult.PASS;
    public static final QuadFunction<ActionResult> FAIL = (action, board, piece, start, end) -> ActionResult.FAIL;
    public static final QuadFunction<Boolean> TRUE = (action, board, piece, start, end) -> true;
    public static final QuadFunction<Boolean> FALSE = (action, board, piece, start, end) -> false;
    
    public R apply(Action action, Board board, Piece piece, Point start, Point end);
}
