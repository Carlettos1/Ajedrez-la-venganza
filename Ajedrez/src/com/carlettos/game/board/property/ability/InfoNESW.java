package com.carlettos.game.board.property.ability;

import com.carlettos.game.core.Direction;

/**
 *
 * @author Carlettos
 */
public class InfoNESW extends Info<Direction>{

    public InfoNESW(Direction valor) {
        super(valor);
    }
    
    public int getSign(){
        return valor.getSign();
    }
    
    public Direction.Axis getAxis(){
        return valor.getAxis();
    }
    
    public boolean isAxis(Direction.Axis axis){
        return valor.isAxis(axis);
    }
}
