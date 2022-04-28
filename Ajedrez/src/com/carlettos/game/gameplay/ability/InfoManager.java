package com.carlettos.game.gameplay.ability;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import com.carlettos.game.gameplay.ability.info.InfoDirection;
import com.carlettos.game.gameplay.ability.info.InfoInteger;
import com.carlettos.game.gameplay.ability.info.InfoNone;
import com.carlettos.game.gameplay.ability.info.InfoPiece;
import com.carlettos.game.gameplay.ability.info.InfoPoint;
import com.carlettos.game.gameplay.ability.info.InfoString;
import com.carlettos.game.gameplay.ability.info.InfoTuple;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.Direction;

/**
 *
 * @author Carlettos
 */
public class InfoManager {

    private static final Map<Class<?>, Function<Object, Info<?>>> map;
   
    static {
        map = new HashMap<>();
        registerInfo(Direction.class, (o) -> new InfoDirection((Direction)o));
        registerInfo(Integer.class, (o) -> new InfoInteger((Integer)o));
        registerInfo(Piece.class, (o) -> new InfoPiece((Piece)o));
        registerInfo(Point.class, (o) -> new InfoPoint((Point)o));
        registerInfo(String.class, (o) -> new InfoString((String)o));
        registerInfo(Tuple.class, (o) -> new InfoTuple<>((Tuple<?, ?>)o));
    }
    
    public static <V> void registerInfo(Class<V> value, Function<Object, Info<?>> info){
        map.put(value, info);
    }

    public static <V> Info<?> getInfo(V value) {
        if(value == null || value.equals("Usar")){
            return new InfoNone();
        }
        for (Map.Entry<Class<?>, Function<Object, Info<?>>> entry : map.entrySet()) {
            if (entry.getKey().isInstance(value)) {
                return entry.getValue().apply(entry.getKey().cast(value));
            }
        }
        throw new IllegalArgumentException("Valor no registrado en la Info");
    }
}
