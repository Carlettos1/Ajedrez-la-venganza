package com.carlettos.game.gameplay.piece;

import java.util.function.Function;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.PatternPawn;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.action.ITake;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public abstract class AbstractPawn<M extends PatternPawn, T extends PatternPawn> extends Piece
        implements IMove<M>, ITake<T> {
    protected final M movePattern;
    protected final T takePattern;

    protected AbstractPawn(Function<Color, M> movePattern, Function<Color, T> takePattern, String key, Ability ability,
            Color color) {
        super(key, ability, color, IPieceType.BIOLOGIC, IPieceType.TRANSPORTABLE);
        this.movePattern = movePattern.apply(getColor());
        this.takePattern = takePattern.apply(getColor());
    }

    @Override
    public boolean can(Action action, AbstractBoard board, Point start, Info info) {
        return switch (action) {
            case MOVE -> this.canMove(board, start, info, movePattern);
            case TAKE -> this.canTake(board, start, info, takePattern);
            default -> false;
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
