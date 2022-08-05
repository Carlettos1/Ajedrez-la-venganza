package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.action.ITake;
import com.carlettos.game.gameplay.pattern.classic.PatternKing;
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
public class Ship extends Piece implements IMove<PatternMagicianMove>, ITake<PatternKing> {
    protected final PatternMagicianMove movePattern;
    protected final PatternKing takePattern;

    public Ship(Color color) {
        super("ship", Abilities.SHIP_ABILITY, color, IPieceType.STRUCTURE);
        this.movePattern = Patterns.MAGICIAN_MOVE_PATTERN;
        this.takePattern = Patterns.KING_PATTERN;
    }

    @Override
    public boolean can(Action action, AbstractSquareBoard board, Point start, Info info) {
        return switch (action) {
            case MOVE -> this.canMove(board, start, info, movePattern);
            case TAKE -> this.canTake(board, start, info, takePattern);
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
