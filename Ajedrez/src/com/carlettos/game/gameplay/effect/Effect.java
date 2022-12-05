package com.carlettos.game.gameplay.effect;

import java.awt.image.BufferedImage;
import java.util.Objects;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.resource.IImageable;
import com.carlettos.game.util.resource.ITranslatable;

public abstract class Effect implements ITranslatable, IImageable, Comparable<Effect> {
    private final EffectData data;
    private int turns;

    protected Effect(String key, int maxTurns) {
        this.data = new EffectData(key, maxTurns);
        this.turns = 0;
    }

    public abstract void onExpire(AbstractBoard board, Point piecePos);

    public abstract void onTick(AbstractBoard board, Point piecePos);

    public boolean canBe(Action action, AbstractBoard board, Point piecePos) {
        return true;
    }

    public void onBe(Action action, AbstractBoard board, Point piecePos) {}

    public boolean can(Action action, AbstractBoard board, Point piecePos) {
        return true;
    }

    public void on(Action action, AbstractBoard board, Point piecePos) {}

    public boolean isExpired() {
        return this.turns >= this.data.maxTurns();
    }

    public void tick() {
        this.turns++;
    }

    @Override
    public BufferedImage getImage() {
        return data.image().getImage();
    }

    @Override
    public String getTranslated() {
        return data.translation().getTranslated();
    }

    @Override
    public int compareTo(Effect o) {
        return Integer.compare(this.turns, o.turns);
    }

    @Override
    public int hashCode() {
        return data.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if ((obj == null) || (getClass() != obj.getClass()))
            return false;
        Effect other = (Effect) obj;
        return Objects.equals(data.name(), other.data.name());
    }
}
