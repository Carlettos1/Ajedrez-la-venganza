package com.carlettos.game.gameplay.ability.info;

import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.util.Tuple;

/**
 *
 * @author Carlettos
 */
public class InfoTuple<A, B> extends Info<Tuple<A, B>> {

    public InfoTuple(Tuple<A, B> valor) {
        super(valor);
    }

    public InfoTuple(A a, B b) {
        super(Tuple.of(a, b));
    }
    
    public A getA(){
        return value.x;
    }
    
    public B getB(){
        return value.y;
    }
}
