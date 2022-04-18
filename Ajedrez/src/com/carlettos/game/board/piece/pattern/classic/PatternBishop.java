package com.carlettos.game.board.piece.pattern.classic;

import com.carlettos.game.core.Point;
import com.carlettos.game.board.manager.AbstractBoard;
import com.carlettos.game.board.piece.pattern.Pattern;

/**
 * 
 * @author Carlettos
 */
public interface PatternBishop extends Pattern{

    @Override
    public default boolean match(AbstractBoard tablero, Point inicio, Point final_) {
        int deltaX = final_.x - inicio.x;
        int deltaY = final_.y - inicio.y;

        if (Math.abs(deltaY) != Math.abs(deltaX)) {
            return false;
        }

        int signoX = deltaX > 0 ? 1 : -1;
        int signoY = deltaY > 0 ? 1 : -1;

        for (int escaque = 1; escaque < Math.abs(deltaX); escaque++) {
            if (tablero.getEscaque(inicio.x + escaque * signoX,
                    inicio.y + escaque * signoY).hasPiece()) {
                return false;
            }
        }
        return true;
    }
}
