package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.Empty;
import com.carlettos.game.board.piece.pattern.action.IAttack;
import com.carlettos.game.board.piece.pattern.action.IMove;
import com.carlettos.game.board.piece.pattern.starting.PatternCannonAttack;
import com.carlettos.game.board.piece.pattern.starting.PatternStructureMove;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.board.property.ability.Info;
import com.carlettos.game.board.property.ability.InfoGetter.AbilityNone;

/**
 *
 * @author Carlettos
 */
public class Cannon extends Piece implements IAttack<PatternCannonAttack>, IMove<PatternStructureMove>, AbilityNone {

    protected final PatternCannonAttack patronAtacar;
    protected final PatternStructureMove patronMover;

    public Cannon(Color color) {
        super("Cañón", "CAÑ", Empty.NO_HABILIDAD, color, PieceType.STRUCTURE);
        patronAtacar = new PatternCannonAttack() {};
        patronMover = new PatternStructureMove() {};
    }

    @Override
    public ActionResult can(Action accion, AbstractBoard tablero, Point inicio, Info info) {
        return switch (accion) {
            case ATTACK -> this.canAttack(tablero, inicio, info, patronAtacar);
            case MOVE -> this.canMove(tablero, inicio, info, patronMover);
            default -> ActionResult.FAIL;
        };
    }
}
