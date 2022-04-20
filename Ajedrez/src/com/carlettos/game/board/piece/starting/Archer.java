package com.carlettos.game.board.piece.starting;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.piece.Empty;
import com.carlettos.game.board.piece.pattern.action.IMove;
import com.carlettos.game.board.piece.pattern.action.IAttack;
import com.carlettos.game.board.piece.pattern.starting.PatternArcherAttack;
import com.carlettos.game.board.piece.pattern.starting.PatternArcherMove;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.PieceType;
import com.carlettos.game.board.property.ability.Info;

/**
 *
 * @author Carlettos
 */
public class Archer extends Piece implements IMove<PatternArcherMove>, IAttack<PatternArcherAttack> {
    protected final PatternArcherMove patronMover;
    protected final PatternArcherAttack patronAtacar;
    
    public Archer(Color color) {
        super("Arquero", "ARQ", Empty.NO_HABILIDAD, color, PieceType.BIOLOGIC, PieceType.TRANSPORTABLE);
        patronMover = new PatternArcherMove() {};
        patronAtacar = new PatternArcherAttack() {};
    }

    @Override
    public ActionResult can(Action accion, AbstractBoard tablero, Point inicio, Info info) {
        return switch(accion){ //TODO: que el ataque pueda fallar
            case MOVE -> this.canMover(tablero, inicio, info, patronMover);
            case ATTACK -> this.canAtacar(tablero, inicio, info, patronAtacar);
            default -> ActionResult.FAIL;
        };
    }
}
