package com.carlettos.game.gameplay.ability;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.Direction;
import com.carlettos.game.util.helper.LogHelper;

/**
 * @author Carlettos
 */
public class Info {
    private static final Set<Class<?>> REGISTRY = new HashSet<>();
    static {
        REGISTRY.add(Direction.class);
        REGISTRY.add(Integer.class);
        REGISTRY.add(Piece.class);
        REGISTRY.add(Point.class);
        REGISTRY.add(String.class);
        REGISTRY.add(Tuple.class);
    }

    protected final Class<?> clazz;
    protected final Object value;

    private Info(Class<?> clazz, Object value) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(value);
        if (!clazz.isInstance(value)) {
            throw new IllegalArgumentException("Value must be an instance of the provided class");
        }
        this.clazz = clazz;
        this.value = value;
    }
    
    public boolean isType(Class<?> check) {
        if (!REGISTRY.contains(check)) {
            LogHelper.LOG.warning(() -> "TRYING TO GET A TYPE THAT IT ISN'T IN THE REGISTRY");
            return false;
        }
        return this.clazz.isAssignableFrom(check);
    }
    
    public boolean isTupleType(Class<?> generic1, Class<?> generic2) {
        if (!this.isType(Tuple.class)) {
            return false;
        }
        Tuple<?, ?> tuple = (Tuple<?, ?>) this.getValue();
        return generic1.isInstance(tuple.x) && generic2.isInstance(tuple.y);
    }

    public Object getValue() {
        return value;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    @Override
    public String toString() {
        return "Info:" + this.value.toString();
    }

    public static Info getInfo(Object value) {
        for (Class<?> class1 : REGISTRY) {
            if (class1.isInstance(value)) { return new Info(class1, value); }
        }
        throw new IllegalArgumentException("Value not expected, it doesn't have a class registered");
    }
}
