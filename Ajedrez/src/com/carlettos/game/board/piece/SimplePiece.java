package com.carlettos.game.board.piece;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Pattern;
import com.carlettos.game.board.piece.pattern.action.ITake;
import com.carlettos.game.board.piece.pattern.action.IMove;
import com.carlettos.game.board.property.ability.Ability;
import com.carlettos.game.board.property.ability.Info;

/**
 *
 * @author Carlos
 */
public abstract class SimplePiece<P extends Pattern> extends Piece implements ITake<P>, IMove<P>{
    protected final P patron;

    public SimplePiece(String nombre, String abreviacion, Ability habilidad, Color color, P patron, PieceType... tipos) {
        super(nombre, abreviacion, habilidad, color, tipos);
        this.patron = patron;
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
