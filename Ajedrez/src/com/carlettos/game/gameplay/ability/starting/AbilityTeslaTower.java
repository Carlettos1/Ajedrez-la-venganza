package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.event.EventInfo;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.effect.DeactivateEffect;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.type.IPieceType;
import com.carlettos.game.util.Point;

public class AbilityTeslaTower extends AbilityNoInfo {
    protected static final Pattern ABILITY_PATTERN = Patterns.CANNON_ATTACK_PATTERN;
    protected static final int EFFECT_DURATION = 6;

    public AbilityTeslaTower() {
        super("tesla_tower", 20, 1);
    }

    @Override
    public boolean canUse(AbstractSquareBoard board, Piece piece, Point start) {
        return (this.commonCanUse(board, piece));
    }

    @Override
    public void use(AbstractSquareBoard board, Piece piece, Point start) {
        board.getClock().addEvent(Event.create(EventInfo.of(board, 2, this.data.getName(), start),
                () -> board.getMatchingEscaques(ABILITY_PATTERN, start).stream()
                        .filter(escaque -> escaque.getPiece().getTypeManager().isType(IPieceType.STRUCTURE))
                        .filter(escaque -> !escaque.getPieceColor().equals(piece.getColor())).forEach(escaque -> escaque
                                .getPiece().getEffectManager().addEffect(new DeactivateEffect(EFFECT_DURATION)))));
        this.commonUse(board, piece);
    }
}
