package com.carlettos.game.gameplay.effect;

import java.util.ArrayList;
import java.util.List;

import com.carlettos.game.board.AbstractBoard;
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
    public void tick(AbstractBoard board, Point pos) {
        effects.forEach(Effect::tick);
        effects.forEach(effect -> effect.onTick(board, pos));
        effects.stream().filter(Effect::isExpired).forEach(effect -> effect.onExpire(board, pos));
        effects.removeIf(Effect::isExpired);
    }

    public void addEffect(Effect effect) {
        if (!effects.contains(effect)) {
            this.effects.add(effect);
        }
        // TODO: que hacer si hay otro efecto igual
    }

    public void removeEffect(Effect effect) {
        if (effects.contains(effect)) {
            this.effects.remove(effect);
        }
    }

    public boolean hasEffect(Effect effect) {
        return effects.contains(effect);
    }

    public List<Effect> getEffects() {
        return effects;
    }

    public boolean can(Action action, AbstractBoard board, Point piecePos) {
        boolean combined = true;
        for (Effect effect : effects) {
            combined &= (effect.can(action, board, piecePos));
        }
        return combined;
    }

    public void on(Action action, AbstractBoard board, Point piecePos) {
        for (Effect effect : effects) {
            effect.on(action, board, piecePos);
        }
    }

    public boolean canBe(Action action, AbstractBoard board, Point piecePos) {
        boolean combined = true;
        for (Effect effect : effects) {
            combined &= (effect.canBe(action, board, piecePos));
        }
        return combined;
    }

    public void onBe(Action action, AbstractBoard board, Point piecePos) {
        for (Effect effect : effects) {
            effect.onBe(action, board, piecePos);
        }
    }
}
