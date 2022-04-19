package com.carlettos.game.board.piece.pattern.classic;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Pattern;

/**
 *
 * @author Carlettos
 */
public interface PatternRook extends Pattern {

    @Override
    public default boolean match(AbstractBoard tablero, Point inicio, Point final_) {
        if (final_.x != inicio.x && final_.y != inicio.y) {
            return false;
        }

        if (final_.x != inicio.x) { //se mueve el x
            int direccion = final_.x > inicio.x ? 1 : -1;
            for (int puntero = 1; puntero < Math.abs(final_.x - inicio.x); puntero++) {
                if (tablero.getEscaque(inicio.x + puntero * direccion, inicio.y).hasPiece()) {
                    return false;
                }
            }
        } else if (final_.y != inicio.y) { //se mueve en y
            int direccion = final_.y > inicio.y ? 1 : -1;
            for (int puntero = 1; puntero < Math.abs(final_.y - inicio.y); puntero++) {
                if (tablero.getEscaque(inicio.x, inicio.y + puntero * direccion).hasPiece()) {
                    return false;
                }
            }
        }
        return true;
    }
}
