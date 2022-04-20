package com.carlettos.game.board.property.ability;

/**
 * Sistema utilizado en la habilidad para darle la información que necesite.
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
