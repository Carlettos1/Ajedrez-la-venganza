package com.carlettos.game.gameplay.ability.starting;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.board.clock.event.Event;
import com.carlettos.game.board.clock.event.EventInfo;
import com.carlettos.game.gameplay.ability.AbilityNoInfo;
import com.carlettos.game.gameplay.effect.DeactivateEffect;
import com.carlettos.game.gameplay.pattern.Pattern;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.util.Point;

public class AbilityTeslaTower extends AbilityNoInfo {
    protected static final Pattern ABILITY_PATTERN = Patterns.CANNON_ATTACK_PATTERN;
    protected static final int EFFECT_DURATION = 6;

    public AbilityTeslaTower() {
        super("tesla_tower", Time.lap(10), 1);
    }

    @Override
    public void use(AbstractBoard board, Point start) {
        board.getClock()
                .addEvent(Event.create(EventInfo.of(board, 2, this.data.getName(), start),
                        () -> board.getAll(ABILITY_PATTERN, start).stream()
                                .filter(escaque -> escaque.getPiece().getTypeManager().isStructure())
                                .filter(escaque -> !escaque.getPieceColor().equals(board.getPiece(start).getColor()))
                                .forEach(escaque -> escaque.getPiece().getEffectManager()
                                        .addEffect(new DeactivateEffect(EFFECT_DURATION)))));
        this.commonUse(board, start);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start) {
        return true;
    }
}
