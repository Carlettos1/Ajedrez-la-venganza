package com.carlettos.game.tablero.pieza.patron.clasico;

import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.manager.TableroAbstract;
import com.carlettos.game.tablero.pieza.patron.Patron;

/**
 *
 * @author Carlettos
 */
public interface PatronCaballo extends Patron{

    @Override
    public default boolean checkPatron(TableroAbstract tablero, Point inicio, Point final_) {
        int deltaX = final_.x - inicio.x;
        int deltaY = final_.y - inicio.y;
        deltaX = Math.abs(deltaX);
        deltaY = Math.abs(deltaY);

        boolean can = (deltaX == 2 && deltaY == 1) || (deltaX == 1 && deltaY == 2);
        return can;
    }
}
