package com.carlettos.game.gameplay.ability;

/**
 * @author Carlettos
 */
public abstract class Info<V> {
    protected final V value;

    public Info(V value) {
        this.value = value;
    }

    public V getValue() {
        return value;
    }

    @Override
    public String toString() {
        return "I:" + this.value.toString();
    }
}
