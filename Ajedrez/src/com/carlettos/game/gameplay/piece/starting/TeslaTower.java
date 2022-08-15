package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.action.ITake;
import com.carlettos.game.gameplay.pattern.starting.PatternMagicianMove;
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
public class TeslaTower extends Piece implements IMove<PatternMagicianMove>, ITake<PatternStructureMove> {
    protected final PatternMagicianMove movePattern;
    protected final PatternStructureMove takePattern;

    public TeslaTower(Color color) {
        super("tesla_tower", Abilities.TESLA_TOWER_ABILITY, color, IPieceType.STRUCTURE);
        this.movePattern = new PatternMagicianMove() {};
        this.takePattern = new PatternStructureMove() {};
    }

    @Override
    public boolean can(Action action, AbstractBoard board, Point start, Info info) {
        return switch (action) {
            case MOVE -> this.canMove(board, start, info, movePattern);
            case TAKE -> this.canTake(board, start, info, takePattern);
            case ABILITY -> this.getAbility().canUse(board, start, info);
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
