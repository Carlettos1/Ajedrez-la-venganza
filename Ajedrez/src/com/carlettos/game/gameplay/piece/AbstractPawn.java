package com.carlettos.game.gameplay.piece;

import java.util.function.Function;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.action.ITake;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public abstract class AbstractPawn extends Piece implements IMove, ITake {
    protected final Pattern movePattern;
    protected final Pattern takePattern;

    protected AbstractPawn(Function<Color, ? extends Pattern> movePattern,
            Function<Color, ? extends Pattern> takePattern, String key, Ability<?> ability, Color color) {
        super(key, ability, color, IPieceType.BIOLOGIC, IPieceType.TRANSPORTABLE);
        this.movePattern = movePattern.apply(getColor());
        this.takePattern = takePattern.apply(getColor());
    }

    @Override
    public Pattern getMovePattern(AbstractBoard board, Point start) {
        return this.movePattern;
    }

    @Override
    public Pattern getTakePattern(AbstractBoard board, Point start) {
        return this.takePattern;
    }
}
