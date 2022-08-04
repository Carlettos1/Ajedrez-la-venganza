package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.action.IAttack;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.starting.PatternArcherAttack;
import com.carlettos.game.gameplay.pattern.starting.PatternArcherMove;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public class Archer extends Piece implements IMove<PatternArcherMove>, IAttack<PatternArcherAttack> {
    protected final PatternArcherMove movePattern;
    protected final PatternArcherAttack attackPattern;

    public Archer(Color color) {
        super("archer", Abilities.NO_ABILITY, color, IPieceType.BIOLOGIC, IPieceType.TRANSPORTABLE);
        movePattern = Patterns.ARCHER_MOVE_PATTERN;
        attackPattern = Patterns.ARCHER_ATTACK_PATTERN;
    }

    @Override
    public ActionResult can(Action action, AbstractSquareBoard board, Point start, Info info) {
        return switch (action) { // TODO: que el ataque pueda fallar
            case MOVE -> this.canMove(board, start, info, movePattern);
            case ATTACK -> this.canAttack(board, start, info, attackPattern);
            default -> ActionResult.FAIL;
        };
    }
}
