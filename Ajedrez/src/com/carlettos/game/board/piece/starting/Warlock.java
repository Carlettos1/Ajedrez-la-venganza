package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.Empty;
import com.carlettos.game.board.piece.pattern.action.IMove;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.board.piece.pattern.starting.PatternMagicianMove;
import com.carlettos.game.board.property.ability.Info;

/**
 *
 * @author Carlettos
 */
public class Warlock extends Piece implements IMove<PatternMagicianMove> {
    protected final PatternMagicianMove movePattern;

    public Warlock(Color color) { //TODO: habilidad
        super("Brujo", "B", Empty.NO_ABILITY, color, PieceType.TRANSPORTABLE, PieceType.DEMONIC, PieceType.IMMUNE);
        this.movePattern = new PatternMagicianMove() {};
    }

    @Override
    public ActionResult can(Action action, AbstractBoard board, Point start, Info info) {
        return switch(action){
            case MOVE -> this.canMove(board, start, info, this.movePattern);
            default -> ActionResult.FAIL;
        };
    }
}
