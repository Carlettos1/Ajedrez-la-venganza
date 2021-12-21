package com.carlettos.game.core;

import java.util.Objects;

/**
 * Clase de utilidad que sirve para poder retornar dos valores de un método, 
 * o mantener 2 valores al nombre de una variable.
 *
 * @author Carlos
 * @param <X> clase del primer parámetro del constructor
 * @param <Y> clase del segundo parámetro del constructor
 */
public final class Par<X, Y> {

    public final X x;
    public final Y y;

    /**
     * @param x el valor de clase X a mantener.
     * @param y el valor de clase Y a mantener.
     */
    public Par(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 43 * hash + Objects.hashCode(this.x);
        hash = 43 * hash + Objects.hashCode(this.y);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Par<?, ?> other = (Par<?, ?>) obj;
        if (!Objects.equals(this.x, other.x)) {
            return false;
        }
        return Objects.equals(this.y, other.y);
    }
    
    @Override
    public String toString() {
        return "Par{" + "x=" + x + ", y=" + y + '}';
    }
}
