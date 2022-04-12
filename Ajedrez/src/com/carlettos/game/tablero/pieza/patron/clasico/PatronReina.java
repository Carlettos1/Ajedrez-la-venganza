package com.carlettos.game.tablero.pieza.patron.clasico;

import com.carlettos.game.core.Point;
import com.carlettos.game.tablero.manager.TableroAbstract;
import com.carlettos.game.tablero.pieza.patron.Patron;

/**
 * 
 * @author Carlettos
 */
public interface PatronReina extends Patron {
    public static Patron ALFIL = new PatronAlfil() {};
    public static Patron TORRE = new PatronTorre() {};

    @Override
    public default boolean checkPatron(TableroAbstract tablero, Point inicio, Point final_) {
        return ALFIL.checkPatron(tablero, inicio, final_) || TORRE.checkPatron(tablero, inicio, final_);
    }
}
