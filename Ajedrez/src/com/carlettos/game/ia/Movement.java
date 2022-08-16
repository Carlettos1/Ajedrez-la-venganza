package com.carlettos.game.ia;

import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;

public class Movement {
    protected final Action action;
    protected final Point from;
    protected final Info info;
    
    public Movement(Action action, Point from, Info info) {
        this.action = action;
        this.from = from;
        this.info = info;
    }

    public Action getAction() {
        return action;
    }

    public Point getFrom() {
        return from;
    }

    public Info getInfo() {
        return info;
    }
}
