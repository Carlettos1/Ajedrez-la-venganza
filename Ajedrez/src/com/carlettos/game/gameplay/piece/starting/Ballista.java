package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.action.IAttack;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.starting.PatternBallistaAttack;
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
public class Ballista extends Piece implements IMove<PatternStructureMove>, IAttack<PatternBallistaAttack> {
    protected final PatternStructureMove movePattern;
    protected final PatternBallistaAttack attackPattern;

    public Ballista(Color color) {
        super("ballista", "BA", Empty.NO_ABILITY, color, PieceType.STRUCTURE);
        movePattern = new PatternStructureMove() {};
        attackPattern = new PatternBallistaAttack() {};
    }

    @Override
    public ActionResult can(Action action, AbstractBoard board, Point start, Info info) {
        return switch(action){
            case MOVE -> this.canMove(board, start, info, movePattern);
            case ATTACK -> this.canAttack(board, start, info, attackPattern);
            default -> ActionResult.FAIL;
        };
    }
}
