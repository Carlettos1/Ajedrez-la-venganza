package com.carlettos.game.board.piece;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.PatternPawn;
import com.carlettos.game.board.piece.pattern.action.IMove;
import com.carlettos.game.board.piece.pattern.action.ITake;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.board.property.ability.Info;

/**
 *
 * @author Carlettos
 */
public abstract class AbstractPawn<M extends PatternPawn, T extends PatternPawn> extends Piece implements IMove<M>, ITake<T> {
    protected final M movePattern;
    protected final T takePattern;

    public AbstractPawn(M movePattern, T takePattern, String name, String notation, Ability ability, Color color) {
        super(name, notation, ability, color, PieceType.BIOLOGIC, PieceType.TRANSPORTABLE);
        this.movePattern = movePattern;
        this.takePattern = takePattern;
    }

    @Override
    public ActionResult can(Action action, AbstractBoard board, Point start, Info info) {
        return switch(action){
            case MOVE -> this.canMove(board, start, info, movePattern);
            case TAKE -> this.canTake(board, start, info, takePattern);
            default -> ActionResult.FAIL;
        };
    }
}
