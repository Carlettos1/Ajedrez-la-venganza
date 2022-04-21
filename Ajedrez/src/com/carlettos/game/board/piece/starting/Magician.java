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
public class Magician extends Piece implements IMove<PatternMagicianMove> {
    protected final PatternMagicianMove movePattern;
    public Magician(Color color) { //TODO: hacer lo de la habilidad
        super("Hechicero", "HE", Empty.NO_ABILITY, color, PieceType.BIOLOGIC, PieceType.HEROIC, PieceType.IMMUNE, PieceType.TRANSPORTABLE);
        movePattern = new PatternMagicianMove() {};
    }

    @Override
    public ActionResult can(Action action, AbstractBoard board, Point start, Info info) {
        return switch(action) {
            case MOVE -> this.canMove(board, start, info, this.movePattern);
            default -> ActionResult.FAIL;
        };
    }
}
