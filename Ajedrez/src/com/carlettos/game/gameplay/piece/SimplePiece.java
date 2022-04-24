package com.carlettos.game.gameplay.piece;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.action.ITake;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.PieceType;

/**
 *
 * @author Carlos
 */
public abstract class SimplePiece<P extends Pattern> extends Piece implements ITake<P>, IMove<P>{
    protected final P patron;

    public SimplePiece(String name, String notation, Ability ability, Color color, P pattern, PieceType... types) {
        super(name, notation, ability, color, types);
        this.patron = pattern;
    }

    /**
     * @@inheritDoc
     */
    @Override
    public final ActionResult can(Action action, AbstractBoard board, Point start, Info info) {
        return switch (action) {
            case TAKE -> this.canTake(board, start, info, patron);
            case MOVE -> this.canMove(board, start, info, patron);
            case ABILITY -> this.getAbility().canUse(board, this, start, info);
            default -> ActionResult.FAIL;
        };
    }
}
