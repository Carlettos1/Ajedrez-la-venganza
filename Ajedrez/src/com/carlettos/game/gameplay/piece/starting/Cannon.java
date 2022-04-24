package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.ability.InfoUse.AbilityNone;
import com.carlettos.game.gameplay.pattern.action.IAttack;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.starting.PatternCannonAttack;
import com.carlettos.game.gameplay.pattern.starting.PatternStructureMove;
import com.carlettos.game.gameplay.piece.Empty;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;
import com.carlettos.game.util.enums.PieceType;

/**
 *
 * @author Carlettos
 */
public class Cannon extends Piece implements IAttack<PatternCannonAttack>, IMove<PatternStructureMove>, AbilityNone {

    protected final PatternCannonAttack attackPattern;
    protected final PatternStructureMove movePattern;

    public Cannon(Color color) {
        super("Cannon", "CAÑ", Empty.NO_ABILITY, color, PieceType.STRUCTURE);
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
