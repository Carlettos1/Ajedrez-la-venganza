package com.carlettos.game.gameplay.effect;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.IResourceKey;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.ResourceLocation;

public abstract class Effect implements IResourceKey {
    //todo: dataEffect
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

    public abstract void onExpire(AbstractBoard board, Point start, Piece piece);
    public abstract void onTick(AbstractBoard board, Point start, Piece piece);
    
    public boolean isExpired() {
        return this.turns >= this.maxTurns;
    }
    
    public void tick() {
        this.turns++;
    }

    @Override
    public String getBaseKey() {
        return key;
    }
}