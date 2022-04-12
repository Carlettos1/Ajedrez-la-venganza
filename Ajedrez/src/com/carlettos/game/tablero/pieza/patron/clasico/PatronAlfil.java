package com.carlettos.game.tablero.pieza.patron.clasico;

import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.AbstractTablero;
import com.carlettos.game.tablero.pieza.patron.Patron;

/**
 * 
 * @author Carlettos
 */
public interface PatronAlfil extends Patron{

    @Override
    public default boolean checkPatron(AbstractTablero tablero, Point inicio, Point final_) {
        int deltaX = final_.x - inicio.x;
        int deltaY = final_.y - inicio.y;

        if (Math.abs(deltaY) != Math.abs(deltaX)) {
            return false;
        }

        int signoX = deltaX > 0 ? 1 : -1;
        int signoY = deltaY > 0 ? 1 : -1;

        for (int escaque = 1; escaque < Math.abs(deltaX); escaque++) {
            if (tablero.getEscaque(inicio.x + escaque * signoX,
                    inicio.y + escaque * signoY).hasPieza()) {
                return false;
            }
        }
        return true;
    }
}
