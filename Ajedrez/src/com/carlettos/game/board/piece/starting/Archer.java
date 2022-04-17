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

/**
 *
 * @author Carlettos
 */
public class Archer extends Piece implements IMove<PatternArcherMove>, IAttack<PatternArcherAttack> {
    protected final PatternArcherMove patronMover;
    protected final PatternArcherAttack patronAtacar;
    
    public Archer(Color color) {
        super("Arquero", "ARQ", Empty.NO_HABILIDAD, color, PieceType.BIOLOGICA, PieceType.TRANSPORTABLE);
        patronMover = new PatternArcherMove() {};
        patronAtacar = new PatternArcherAttack() {};
    }

    @Override
    public ActionResult can(Action accion, AbstractBoard tablero, Point inicio, Point final_) {
        return switch(accion){ //TODO: que el ataque pueda fallar
            case MOVER -> this.canMover(tablero, inicio, final_, patronMover);
            case ATACAR -> this.canAtacar(tablero, inicio, final_, patronAtacar);
            default -> ActionResult.FAIL;
        };
    }
}
