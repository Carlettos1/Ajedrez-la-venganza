package com.carlettos.game.board.property.ability.info;

import com.carlettos.game.board.property.ability.Info;
import com.carlettos.game.core.Direction;

/**
 *
 * @author Carlettos
 */
public class InfoDirection extends Info<Direction>{

    public InfoDirection(Direction valor) {
        super(valor);
    }
    
    public int getSign(){
        return value.getSign();
    }
    
    public Direction.Axis getAxis(){
        return value.getAxis();
    }
    
    public boolean isAxis(Direction.Axis axis){
        return value.isAxis(axis);
    }
}
