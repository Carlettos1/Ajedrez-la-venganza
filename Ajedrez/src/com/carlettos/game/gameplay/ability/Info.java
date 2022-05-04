package com.carlettos.game.gameplay.ability;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.Tuple;
import com.carlettos.game.util.enums.Direction;

/**
 * @author Carlettos
 */
public class Info {
	private static final Set<Class<?>> classes = new HashSet<>();
	static {
        classes.add(Direction.class);
        classes.add(Integer.class);
        classes.add(Piece.class);
        classes.add(Point.class);
        classes.add(String.class);
        classes.add(Tuple.class);
	}
	
	protected final Class<?> clazz;
    protected final Object value;

    private Info(Class<?> clazz, Object value) {
    	if (!clazz.isInstance(value)) {
    	    throw new IllegalArgumentException("Value must be an instance of the provided class");
    	}
    	Objects.requireNonNull(clazz);
    	Objects.requireNonNull(value);
    	this.clazz = clazz;
        this.value = value;
    }
    
    public boolean isType(Class<?> check) {
        return this.clazz.isAssignableFrom(check);
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
        for (Class<?> class1 : classes) {
            if (class1.isInstance(value)) {
                return new Info(class1, value);
            }
        }
        throw new IllegalArgumentException("Value not expected, it doesn't have a class registered");
    }
}
