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
    protected final PatternMagicianMove patronMover;
    public Magician(Color color) { //TODO: hacer lo de la habilidad
        super("Hechicero", "HE", Empty.NO_HABILIDAD, color, PieceType.BIOLOGIC, PieceType.HEROIC, PieceType.IMMUNE, PieceType.TRANSPORTABLE);
        patronMover = new PatternMagicianMove() {};
    }

    @Override
    public ActionResult can(Action accion, AbstractBoard tablero, Point inicio, Info info) {
        return switch(accion) {
            case MOVE -> this.canMover(tablero, inicio, info, this.patronMover);
            default -> ActionResult.FAIL;
        };
    }
}
