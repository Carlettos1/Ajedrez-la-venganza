package com.carlettos.game.util.enums;

import com.carlettos.game.gameplay.ability.info.InfoDirection;

/**
 *
 * @author Carlettos
 */
public enum Direction {
    N(1), E(1), S(-1), W(-1);
    private final int sign;

    private Direction(int sign) {
        this.sign = sign;
    }

    public int getSign() {
        return sign;
    }
    
    public Axis getAxis(){
        return switch(this){
            case N, S -> Axis.NS;
            case E, W -> Axis.EW;
        };
    }
    
    public boolean isAxis(Axis axis){
        return this.getAxis().equals(axis);
    }
    
    public InfoDirection toInfo(){
        return new InfoDirection(this);
    }
    
    public static enum Axis {
        NS, EW;
    }
}
