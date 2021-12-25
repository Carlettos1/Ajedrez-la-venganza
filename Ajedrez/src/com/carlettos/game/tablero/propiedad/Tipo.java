package com.carlettos.game.tablero.propiedad;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.QuadFunction;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import java.awt.Point;

/**
 * Todos los tipos de piezas que hay.
 *
 * @author Carlos
 */
public enum Tipo {
    BIOLOGICA,
    ESTRUCTURA,
    INMUNE,
    DEMONIO,
    HEROICA,
    GIGANTE,
    PEQUEÑA,
    TRANSPORTABLE,
    IMPENETRABLE,
    INTRASPASABLE;
    
    private final QuadFunction<ActionResult> can;
    private final QuadFunction<ActionResult> on;
    private final QuadFunction<Boolean> canSer;
    private final QuadFunction<Boolean> onSer;

    private Tipo(QuadFunction can, QuadFunction on, QuadFunction canSer, QuadFunction onSer) {
        this.can = can;
        this.on = on;
        this.canSer = canSer;
        this.onSer = onSer;
    }
    
    public ActionResult canAccion(Accion accion, Tablero tablero, Pieza pieza, Point point){
        return can.apply(accion, tablero, pieza, point);
    }
    
    public ActionResult onAccion(Accion accion, Tablero tablero, Pieza pieza, Point point){
        return on.apply(accion, tablero, pieza, point);
    }
    
    public boolean canSerAccion(Accion accion, Tablero tablero, Pieza pieza, Point point){
        return canSer.apply(accion, tablero, pieza, point);
    }
    
    public boolean onSerAccion(Accion accion, Tablero tablero, Pieza pieza, Point point){
        return onSer.apply(accion, tablero, pieza, point);
    }
}
