package com.carlettos.game.board.property.ability;

/**
 * Sistema utilizado en la habilidad para darle la información que necesite.
 * @author Carlettos
 */
public abstract class Info<V> {
    protected final V valor;

    public Info(V valor) {
        this.valor = valor;
    }

    public V getValor() {
        return valor;
    }

    @Override
    public String toString() {
        return "I:" + this.valor.toString();
    }
}
