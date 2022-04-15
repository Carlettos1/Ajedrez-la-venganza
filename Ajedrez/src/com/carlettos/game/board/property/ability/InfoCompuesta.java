package com.carlettos.game.board.property.ability;

import com.carlettos.game.core.Par;

/**
 *
 * @author Carlettos
 */
public class InfoCompuesta<I1 extends Info<?>, I2 extends Info<?>> extends Info<Par<I1, I2>> {

    public InfoCompuesta(Par<I1, I2> valor) {
        super(valor);
    }

    public InfoCompuesta(I1 x, I2 y) {
        super(Par.of(x, y));
    }
    
    public I1 getX(){
        return valor.x;
    }
    
    public I2 getY(){
        return valor.y;
    }
}
