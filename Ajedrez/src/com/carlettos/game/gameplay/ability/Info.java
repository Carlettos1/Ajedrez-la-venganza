package com.carlettos.game.gameplay.ability;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.function.Supplier;
import java.util.logging.Level;

import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.Direction;
import com.carlettos.game.util.enums.Direction.SubDirection;
import com.carlettos.game.util.helper.LogHelper;

/**
 * @author Carlettos
 */
public final class Info {
    private static final Set<Class<? extends IInfo>> REGISTRY = new HashSet<>();
    static {
        register(Direction.class);
        register(SubDirection.class);
        register(Piece.class);
        register(Point.class);
        register(Tuple.class);
    }

    private final Class<? extends IInfo> clazz;
    private final Object value;

    private Info(Class<? extends IInfo> clazz, Object value) {
        Objects.requireNonNull(clazz);
        Objects.requireNonNull(value);
        if (!clazz.isInstance(value)) {
            throw new IllegalArgumentException(
                    "Value %s must be an instance of the provided class %s".formatted(value, clazz));
        }
        this.clazz = clazz;
        this.value = value;
    }

    public boolean isType(Class<? extends IInfo> check) {
        if (checkTypeExistence(check, Level.SEVERE,
                () -> "TRYING TO GET A TYPE THAT IT ISN'T IN THE REGISTRY %s".formatted(check))) {
            return false;
        }
        return this.clazz.isAssignableFrom(check);
    }

    public boolean isTupleType(Class<?> generic1, Class<?> generic2) {
        if (!this.isType(Tuple.class)) { return false; }
        Tuple<?, ?> tuple = (Tuple<?, ?>) this.getValue();

        String msg = "%s doesn't exist in the registry. But it's inside a tuple, so it should be fine. However, be aware";
        checkTypeExistence(generic1, Level.INFO, () -> msg.formatted(generic1));
        checkTypeExistence(generic2, Level.INFO, () -> msg.formatted(generic2));
        return generic1.isInstance(tuple.x) && generic2.isInstance(tuple.y);
    }

    public boolean isPointOrSubPoint() {
        if (this.isType(Point.class)) {
            return true;
        } else if (this.isType(Tuple.class)) {
            // TODO: FIXME: XXX: usar recursión at infinito?
            Tuple<?, ?> tuple = (Tuple<?, ?>) this.getValue();
            if (tuple.x instanceof Point || tuple.y instanceof Point) {
                return true;
            } else {
                return false;
            }
        } else {
            return false;
        }
    }

    public Point getPointOrSubPoint() {
        if (this.isType(Point.class)) {
            return (Point) this.getValue();
        } else {
            Tuple<?, ?> tuple = (Tuple<?, ?>) this.getValue();
            if (tuple.x instanceof Point) {
                return (Point) tuple.x;
            } else {
                return (Point) tuple.y;
            }
        }
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

    /**
     * Returns true if the class is not contained in the registry.
     */
    private static boolean checkTypeExistence(Class<?> clazz, Level level, Supplier<String> supplier) {
        if (!REGISTRY.contains(clazz)) {
            LogHelper.LOG.log(level, supplier);
            return true;
        }
        return false;
    }

    public static void register(Class<? extends IInfo> clazz) {
        Objects.requireNonNull(clazz);
        if (REGISTRY.contains(clazz)) {
            LogHelper.LOG.warning(() -> "TRYING TO REGISTER AN ALREADY REGISTERED INFO %s".formatted(clazz));
            return;
        }
        REGISTRY.add(clazz);
    }

    public static Info getInfo(Object value) {
        for (var class1 : REGISTRY) {
            if (class1.isInstance(value)) { return new Info(class1, value); }
        }
        throw new IllegalArgumentException(
                "Value not expected, it doesn't have a class registered. %s".formatted(value));
    }
}
