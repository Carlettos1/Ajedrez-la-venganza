package com.carlettos.game.board.property.ability;

import com.carlettos.game.core.Tuple;

/**
 *
 * @author Carlettos
 */
public class InfoCompound<I1 extends Info<?>, I2 extends Info<?>> extends Info<Tuple<I1, I2>> {

    public InfoCompound(Tuple<I1, I2> valor) {
        super(valor);
    }

    public InfoCompound(I1 x, I2 y) {
        super(Tuple.of(x, y));
    }
    
    public I1 getX(){
        return value.x;
    }
    
    public I2 getY(){
        return value.y;
    }
}
