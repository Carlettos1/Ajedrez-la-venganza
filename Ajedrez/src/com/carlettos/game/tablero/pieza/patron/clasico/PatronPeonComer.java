package com.carlettos.game.tablero.pieza.patron.clasico;

import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.patron.PatronPeon;
import com.carlettos.game.tablero.propiedad.Color;

/**
 *
 * @author Carlettos
 */
public interface PatronPeonComer extends PatronPeon{

    @Override
    public default boolean checkPatron(Tablero tablero, Point inicio, Point final_) {
        if (this.getColor().equals(Color.BLANCO)) {
            Point punto1 = new Point(inicio.x + 1, inicio.y + 1);
            Point punto2 = new Point(inicio.x - 1, inicio.y + 1);
            return final_.equals(punto1) || final_.equals(punto2);
        } else if (getColor().equals(Color.NEGRO)) {
            Point punto1 = new Point(inicio.x + 1, inicio.y - 1);
            Point punto2 = new Point(inicio.x - 1, inicio.y - 1);
            return final_.equals(punto1) || final_.equals(punto2);
        }
        return false;
    }
}
