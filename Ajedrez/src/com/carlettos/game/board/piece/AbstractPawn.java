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
 * @author Carlettos <M extends PatronPeon, C extends PatronPeon> 
 */
public abstract class AbstractPawn<M extends PatternPawn, C extends PatternPawn> extends Piece implements IMove<M>, ITake<C> {
    protected final C patronComer;
    protected final M patronMover;

    public AbstractPawn(C patronComer, M patronMover, String nombre, String abreviacion, Ability habilidad, Color color) {
        super(nombre, abreviacion, habilidad, color, PieceType.BIOLOGIC, PieceType.TRANSPORTABLE);
        this.patronComer = patronComer;
        this.patronMover = patronMover;
    }

    @Override
    public ActionResult can(Action accion, AbstractBoard tablero, Point inicio, Info info) {
        return switch(accion){
            case MOVE -> this.canMove(tablero, inicio, info, patronMover);
            case TAKE -> this.canTake(tablero, inicio, info, patronComer);
            default -> ActionResult.FAIL;
        };
    }
}
