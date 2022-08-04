package com.carlettos.game.gameplay.piece;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.action.ITake;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlos
 */
public abstract class SimplePiece<P extends Pattern> extends Piece implements ITake<P>, IMove<P> {
    protected final P patron;

    protected SimplePiece(String key, Ability ability, Color color, P pattern, IPieceType... types) {
        super(key, ability, color, types);
        this.patron = pattern;
    }

    /**
     * @@inheritDoc
     */
    @Override
    public final ActionResult can(Action action, AbstractSquareBoard board, Point start, Info info) {
        return switch (action) {
            case TAKE -> this.canTake(board, start, info, patron);
            case MOVE -> this.canMove(board, start, info, patron);
            case ABILITY -> this.getAbility().canUse(board, this, start, info);
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
