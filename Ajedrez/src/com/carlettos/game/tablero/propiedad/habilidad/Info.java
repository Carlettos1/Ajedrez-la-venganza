package com.carlettos.game.tablero.propiedad.habilidad;

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
    
    /*public abstract Info<V> fromString(String str);
    @Override
    public abstract String toString();
    public abstract boolean CheckString();
    public abstract List<V> getAllValoresPosibles();*/
}
