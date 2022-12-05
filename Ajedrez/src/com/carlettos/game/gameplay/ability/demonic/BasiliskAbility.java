package com.carlettos.game.gameplay.ability.demonic;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.clock.Time;
import com.carlettos.game.gameplay.ability.NoInfoAbility;
import com.carlettos.game.gameplay.effect.DeactivateEffect;
import com.carlettos.game.gameplay.pattern.Patterns;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;

public class BasiliskAbility extends NoInfoAbility {

    public BasiliskAbility() {
        super("basilisk", Time.lap(20), 0);
    }

    @Override
    public boolean reducedCanUse(AbstractBoard board, Point start) {
        return true;
    }

    @Override
    public void use(AbstractBoard board, Point start) {
        board.getAll(Patterns.BISHOP_PATTERN, start).stream()
                .filter(e -> board.getPiece(start).canAction(Action.ABILITY, board, start, e.getPos().toInfo()))
                .forEach(e -> e.getPiece().getEffectManager().addEffect(new DeactivateEffect(4)));
    }
}
