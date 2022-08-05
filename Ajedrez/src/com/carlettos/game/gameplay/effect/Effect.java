package com.carlettos.game.gameplay.effect;

import java.util.Objects;

import com.carlettos.game.board.AbstractSquareBoard;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.IResourceKey;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.ResourceLocation;
import com.carlettos.game.util.enums.Action;

public abstract class Effect implements IResourceKey, Comparable<Effect> {
    // TODO: dataEffect
    private final String key;
    private final ResourceLocation resource;
    private final int maxTurns;
    private int turns;

    protected Effect(String key, int maxTurns) {
        this.key = key;
        this.resource = new ResourceLocation("effect.".concat(key));
        this.maxTurns = maxTurns;
        this.turns = 0;
    }

    public String getName() {
        return resource.getTranslated();
    }

    public abstract void onExpire(AbstractSquareBoard board, Point pos, Piece piece);

    public abstract void onTick(AbstractSquareBoard board, Point pos, Piece piece);

    public boolean canBe(Action action, AbstractSquareBoard board, Point pos, Piece piece) {
        return true;
    }

    public void onBe(Action action, AbstractSquareBoard board, Point pos, Piece piece) {}

    public boolean isExpired() {
        return this.turns >= this.maxTurns;
    }

    public void tick() {
        this.turns++;
    }
    
    @Override
    public int compareTo(Effect o) {
        return Integer.compare(this.turns, o.turns);
    }

    @Override
    public String getBaseKey() {
        return key;
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, maxTurns, resource, turns);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        Effect other = (Effect) obj;
        return Objects.equals(key, other.key);
    }
}
