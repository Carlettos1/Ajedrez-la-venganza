package com.carlettos.game.gameplay.piece;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.PatternPawn;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.action.ITake;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.PieceType;

/**
 *
 * @author Carlettos
 */
public abstract class AbstractPawn<M extends PatternPawn, T extends PatternPawn> extends Piece implements IMove<M>, ITake<T> {
    protected final M movePattern;
    protected final T takePattern;

    protected AbstractPawn(M movePattern, T takePattern, String name, String notation, Ability ability, Color color) {
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
    
    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }
    
    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
