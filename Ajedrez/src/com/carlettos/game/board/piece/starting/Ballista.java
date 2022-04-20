package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.Empty;
import com.carlettos.game.board.piece.pattern.action.IMove;
import com.carlettos.game.board.piece.pattern.action.IAttack;
import com.carlettos.game.board.piece.pattern.starting.PatternBallistaAttack;
import com.carlettos.game.board.piece.pattern.starting.PatternStructureMove;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.board.property.ability.Info;

/**
 *
 * @author Carlettos
 */
public class Ballista extends Piece implements IMove<PatternStructureMove>, IAttack<PatternBallistaAttack> {
    protected final PatternStructureMove patronMover;
    protected final PatternBallistaAttack patronAtacar;

    public Ballista(Color color) {
        super("Ballesta", "BA", Empty.NO_HABILIDAD, color, PieceType.STRUCTURE);
        patronMover = new PatternStructureMove() {};
        patronAtacar = new PatternBallistaAttack() {};
    }

    @Override
    public ActionResult can(Action accion, AbstractBoard tablero, Point inicio, Info info) {
        return switch(accion){
            case MOVE -> this.canMover(tablero, inicio, info, patronMover);
            case ATTACK -> this.canAtacar(tablero, inicio, info, patronAtacar);
            default -> ActionResult.FAIL;
        };
    }
}
