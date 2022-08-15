package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.starting.PatternStructureMove;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public class Catapult extends Piece implements IMove<PatternStructureMove> {
    protected final PatternStructureMove movePattern;

    public Catapult(Color color) {
        super("catapult", Abilities.CATAPULT_ABILITY, color, IPieceType.STRUCTURE);
        movePattern = Patterns.STRUCTURE_MOVE_PATTERN;
    }

    @Override
    public boolean can(Action action, AbstractBoard board, Point start, Info info) {
        return switch (action) {
            case MOVE -> this.canMove(board, start, info, movePattern);
            case ABILITY -> this.getAbility().canUse(board, this, start, info);
            default -> false;
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
