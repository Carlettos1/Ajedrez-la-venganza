package com.carlettos.game.core;

import com.carlettos.game.tablero.propiedad.habilidad.InfoNESW;

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
    
    public InfoNESW toInfo(){
        return new InfoNESW(this);
    }
    
    public static enum Axis{
        NS, EW;
    }
}
