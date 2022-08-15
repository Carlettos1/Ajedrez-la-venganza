package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.starting.PatternMagicianMove;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public class Warlock extends Piece implements IMove<PatternMagicianMove> {
    protected final PatternMagicianMove movePattern;

    public Warlock(Color color) {
        super("warlock", Abilities.WARLOCK_ABILITY, color, IPieceType.TRANSPORTABLE, IPieceType.DEMONIC,
                IPieceType.IMMUNE);
        this.movePattern = Patterns.MAGICIAN_MOVE_PATTERN;
    }

    @Override
    public boolean can(Action action, AbstractBoard board, Point start, Info info) {
        return switch (action) {
            case MOVE -> this.canMove(board, start, info, this.movePattern);
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
