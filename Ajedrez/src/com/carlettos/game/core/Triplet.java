package com.carlettos.game.core;

import java.util.Objects;

/**
 *
 * @author Carlettos
 */
public class Triplet<X, Y, Z> {
    
    public final X x;
    public final Y y;
    public final Z z;

    public Triplet(X x, Y y, Z z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
    
    public static <X, Y, Z> Triplet<X, Y, Z> of(X x, Y y, Z z){
        return new Triplet<>(x, y, z);
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
        final Triplet<?, ?, ?> other = (Triplet<?, ?, ?>) obj;
        if (!Objects.equals(this.x, other.x)) {
            return false;
        }
        if (!Objects.equals(this.y, other.y)) {
            return false;
        }
        return Objects.equals(this.z, other.z);
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 97 * hash + Objects.hashCode(this.x);
        hash = 97 * hash + Objects.hashCode(this.y);
        hash = 97 * hash + Objects.hashCode(this.z);
        return hash;
    }

    @Override
    public String toString() {
        return "Trio{" + "x=" + x + ", y=" + y + ", z=" + z + '}';
    }
}
