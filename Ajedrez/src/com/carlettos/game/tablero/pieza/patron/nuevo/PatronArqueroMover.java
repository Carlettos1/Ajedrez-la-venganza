package com.carlettos.game.tablero.pieza.patron.nuevo;

import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.TableroAbstract;
import com.carlettos.game.tablero.pieza.patron.Patron;
import com.carlettos.game.tablero.pieza.patron.clasico.PatronRey;

/**
 *
 * @author Carlettos
 */
public interface PatronArqueroMover extends Patron {
    public static Patron REY = new PatronRey() {};
    public static Patron HECHICERO = new PatronHechiceroMover() {};
    
    @Override
    public default boolean checkPatron(TableroAbstract tablero, Point inicio, Point final_) {
        return HECHICERO.checkPatron(tablero, inicio, final_) || REY.checkPatron(tablero, inicio, final_);
    }
}
