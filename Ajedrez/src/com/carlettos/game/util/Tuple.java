package com.carlettos.game.util;

import java.util.Objects;

/**
 *
 * @author Carlos
 */
public class Tuple<X, Y> {

    public final X x;
    public final Y y;

    public Tuple(X x, Y y) {
        this.x = x;
        this.y = y;
    }

    public static <X, Y> Tuple<X, Y> of(X x, Y y) {
        return new Tuple<>(x, y);
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
        if (this == obj) { return true; }
        if ((obj == null) || (getClass() != obj.getClass())) { return false; }
        final Tuple<?, ?> other = (Tuple<?, ?>) obj;
        if (!Objects.equals(this.x, other.x)) { return false; }
        return Objects.equals(this.y, other.y);
    }

    @Override
    public String toString() {
        return "Par{" + "x=" + x + ", y=" + y + '}';
    }
}
