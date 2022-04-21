package com.carlettos.game.core;

import com.carlettos.game.board.property.Color;

/**
 *
 * @author Carlos
 */
public enum Action {
    MOVE(Color.CYAN),
    ATTACK(Color.ORANGE),
    TAKE(Color.RED),
    ABILITY(Color.MAGENTA);
    
    private final Color color;

    private Action(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
}
