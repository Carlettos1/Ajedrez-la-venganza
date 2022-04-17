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
import com.carlettos.game.board.property.ability.InfoGetter.HabilidadSinInfo;

/**
 *
 * @author Carlettos
 */
public class Cannon extends Piece implements IAttack<PatternCannonAttack>, IMove<PatternStructureMove>, HabilidadSinInfo {

    protected final PatternCannonAttack patronAtacar;
    protected final PatternStructureMove patronMover;

    public Cannon(Color color) {
        super("Cañón", "CAÑ", Empty.NO_HABILIDAD, color, PieceType.ESTRUCTURA);
        patronAtacar = new PatternCannonAttack() {};
        patronMover = new PatternStructureMove() {};
    }

    @Override
    public ActionResult can(Action accion, AbstractBoard tablero, Point inicio, Point final_) {
        return switch (accion) {
            case ATACAR -> this.canAtacar(tablero, inicio, final_, patronAtacar);
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            default -> ActionResult.FAIL;
        };
    }
}
