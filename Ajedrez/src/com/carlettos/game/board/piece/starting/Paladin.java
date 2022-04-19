package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.Empty;
import com.carlettos.game.board.piece.pattern.action.IMove;
import com.carlettos.game.board.piece.pattern.action.ITake;
import com.carlettos.game.board.piece.pattern.classic.PatternQueen;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.board.property.ability.Info;

/**
 *
 * @author Carlettos
 */
public class Paladin extends Piece implements IMove<PatternQueen>, ITake<PatternQueen> {
    protected final PatternQueen patron;

    public Paladin(Color color) { //TODO: Habilidad
        super("Paladin", "PA", Empty.NO_HABILIDAD, color, PieceType.HEROICA, PieceType.INMUNE);
        patron = new PatternQueen(){};
    }

    @Override
    public ActionResult can(Action accion, AbstractBoard tablero, Point inicio, Info info) {
        return switch(accion){
            case MOVER -> this.canMover(tablero, inicio, info, patron);
            case COMER -> this.canComer(tablero, inicio, info, patron);
            default -> ActionResult.FAIL;
        };
    }
}
