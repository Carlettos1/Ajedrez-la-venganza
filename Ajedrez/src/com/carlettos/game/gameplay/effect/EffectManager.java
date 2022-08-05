package com.carlettos.game.gameplay.effect;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;

public class EffectManager {
    protected final List<Effect> effects;
    protected final Piece piece;

    public EffectManager(Piece piece) {
        this.effects = new ArrayList<>();
        this.piece = piece;
    }

    /**
     * Executed at the end of every turn.
     */
    public void tick(AbstractSquareBoard board, Point pos) {
        effects.forEach(Effect::tick);
        effects.forEach(effect -> effect.onTick(board, pos, this.piece));
        effects.stream().filter(Effect::isExpired).forEach(effect -> effect.onExpire(board, pos, this.piece));
        effects.removeIf(Effect::isExpired);
    }

    public void addEffect(Effect effect) {
        if (!effects.contains(effect)) {
            this.effects.add(effect);
        }
        // TODO: que hacer si hay otro efecto igual
    }

    public boolean hasEffect(Effect effect) {
        return effects.contains(effect);
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public boolean canBe(Action action, AbstractSquareBoard board, Point start) {
        boolean combined = true;
        for (Effect effect : effects) {
            combined &= (effect.canBe(action, board, start, piece));
        }
        return combined;
    }

    public void onBe(Action action, AbstractSquareBoard board, Point start) {
        for (Effect effect : effects) {
            effect.onBe(action, board, start, piece);
        }
    }
}
