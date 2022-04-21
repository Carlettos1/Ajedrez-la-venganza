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
    protected final PatternQueen pattern;

    public Paladin(Color color) { //TODO: Habilidad
        super("Paladin", "PA", Empty.NO_ABILITY, color, PieceType.HEROIC, PieceType.IMMUNE);
        pattern = new PatternQueen(){};
    }

    @Override
    public ActionResult can(Action action, AbstractBoard board, Point start, Info info) {
        return switch(action){
            case MOVE -> this.canMove(board, start, info, pattern);
            case TAKE -> this.canTake(board, start, info, pattern);
            default -> ActionResult.FAIL;
        };
    }
}
