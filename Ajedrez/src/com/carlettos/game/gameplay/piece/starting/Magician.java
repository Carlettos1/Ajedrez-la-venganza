package com.carlettos.game.gameplay.piece.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.ability.Abilities;
import com.carlettos.game.gameplay.card.onBoard.CardsOnBoard;
import com.carlettos.game.gameplay.effect.FireEffect;
import com.carlettos.game.gameplay.effect.IceEffect;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.pattern.action.IMove;
import com.carlettos.game.gameplay.pattern.starting.PatternRange;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Color;

/**
 *
 * @author Carlettos
 */
public class Magician extends Piece implements IMove {
    protected static final PatternRange TICK_PATTERN = Patterns.ARCHER_ATTACK_PATTERN;

    public Magician(Color color) {
        super("magician", Abilities.MAGICIAN_ABILITY, color, IPieceType.BIOLOGIC, IPieceType.HEROIC, IPieceType.IMMUNE,
                IPieceType.TRANSPORTABLE);
    }

    @Override
    public Pattern getMovePattern(AbstractBoard board, Point start) {
        return Patterns.MAGICIAN_MOVE_PATTERN;
    }

    @Override
    protected void innerTick(AbstractBoard board, Point pos) {
        var hasIce = board.getClock().boardContains(CardsOnBoard.ICE);
        var hasFire = board.getClock().boardContains(CardsOnBoard.FIRE);
        var pieces = board.getAll(TICK_PATTERN, pos);
        pieces.removeIf(e -> !e.hasPiece());
        pieces.removeIf(e -> e.getPieceColor() != this.getColor());

        if (hasFire) {
            pieces.forEach(e -> e.getPiece().getEffectManager().removeEffect(new IceEffect(0)));
        }
        if (hasIce) {
            pieces.forEach(e -> e.getPiece().getEffectManager().removeEffect(new FireEffect(0)));
        }
    }
}
