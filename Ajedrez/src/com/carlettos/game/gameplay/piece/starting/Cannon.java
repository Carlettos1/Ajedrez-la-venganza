package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.action.IAttack;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.starting.PatternCannonAttack;
import com.carlettos.game.gameplay.pattern.starting.PatternStructureMove;
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
public class Cannon extends Piece implements IAttack<PatternCannonAttack>, IMove<PatternStructureMove> {
    protected final PatternCannonAttack attackPattern;
    protected final PatternStructureMove movePattern;

    public Cannon(Color color) {
        super("cannon", Abilities.ABILITY_NONE, color, PieceType.STRUCTURE);
        attackPattern = Patterns.CANNON_ATTACK_PATTERN;
        movePattern = Patterns.STRUCTURE_MOVE_PATTERN;
    }

    @Override
    public ActionResult can(Action action, AbstractSquareBoard board, Point start, Info info) {
        return switch (action) {
            case ATTACK -> this.canAttack(board, start, info, attackPattern);
            case MOVE -> this.canMove(board, start, info, movePattern);
            default -> ActionResult.FAIL;
        };
    }

    @Override
    public boolean equals(Object obj) {
        return super.equals(obj);
    }

    @Override
    public int hashCode() {
        return super.hashCode();
    }
}
