package com.carlettos.game.util.enums;

/**
 *
 * @author Carlos
 */
public enum Action {
    MOVE(Color.CYAN), ATTACK(Color.ORANGE), TAKE(Color.RED), ABILITY(Color.MAGENTA);

    private final Color color;

    private Action(Color color) {
        this.color = color;
    }

    public Color getColor() {
        return color;
    }
    
    public boolean needsInfoPoint() {
        return this != ABILITY;
    }
}
