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

    protected final PatternCannonAttack attackPattern;
    protected final PatternStructureMove movePattern;

    public Cannon(Color color) {
        super("Cañón", "CAÑ", Empty.NO_ABILITY, color, PieceType.STRUCTURE);
        attackPattern = new PatternCannonAttack() {};
        movePattern = new PatternStructureMove() {};
    }

    @Override
    public ActionResult can(Action action, AbstractBoard board, Point start, Info info) {
        return switch (action) {
            case ATTACK -> this.canAttack(board, start, info, attackPattern);
            case MOVE -> this.canMove(board, start, info, movePattern);
            default -> ActionResult.FAIL;
        };
    }
}
