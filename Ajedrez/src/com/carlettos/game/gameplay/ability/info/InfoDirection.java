package com.carlettos.game.gameplay.ability.info;

import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.util.enums.Direction;

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
