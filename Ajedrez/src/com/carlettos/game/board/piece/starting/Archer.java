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
    protected final PatternArcherMove movePattern;
    protected final PatternArcherAttack attackPattern;
    
    public Archer(Color color) {
        super("Arquero", "ARQ", Empty.NO_ABILITY, color, PieceType.BIOLOGIC, PieceType.TRANSPORTABLE);
        movePattern = new PatternArcherMove() {};
        attackPattern = new PatternArcherAttack() {};
    }

    @Override
    public ActionResult can(Action action, AbstractBoard board, Point start, Info info) {
        return switch(action){ //TODO: que el ataque pueda fallar
            case MOVE -> this.canMove(board, start, info, movePattern);
            case ATTACK -> this.canAttack(board, start, info, attackPattern);
            default -> ActionResult.FAIL;
        };
    }
}
