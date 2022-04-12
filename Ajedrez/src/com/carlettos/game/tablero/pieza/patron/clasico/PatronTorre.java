package com.carlettos.game.tablero.pieza.patron.clasico;

import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.TableroAbstract;
import com.carlettos.game.tablero.pieza.patron.Patron;

/**
 *
 * @author Carlettos
 */
public interface PatronTorre extends Patron {

    @Override
    public default boolean checkPatron(TableroAbstract tablero, Point inicio, Point final_) {
        if (final_.x != inicio.x && final_.y != inicio.y) {
            return false;
        }

        if (final_.x != inicio.x) { //se mueve el x
            int direccion = final_.x > inicio.x ? 1 : -1;
            for (int puntero = 1; puntero < Math.abs(final_.x - inicio.x); puntero++) {
                if (tablero.getEscaque(inicio.x + puntero * direccion, inicio.y).hasPieza()) {
                    return false;
                }
            }
        } else if (final_.y != inicio.y) { //se mueve en y
            int direccion = final_.y > inicio.y ? 1 : -1;
            for (int puntero = 1; puntero < Math.abs(final_.y - inicio.y); puntero++) {
                if (tablero.getEscaque(inicio.x, inicio.y + puntero * direccion).hasPieza()) {
                    return false;
                }
            }
        }
        return true;
    }
}
