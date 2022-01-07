package com.carlettos.game.tablero.propiedad;

import com.carlettos.game.core.Accion;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.function.QuadFunction;
import com.carlettos.game.tablero.manager.Tablero;
import com.carlettos.game.tablero.pieza.Pieza;
import com.carlettos.game.core.Point;

/**
 * Tipos de piezas que hay. TODO: javadoc
 *
 * @author Carlos
 */
public enum Tipo {

    /**
     *
     */
    BIOLOGICA(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),

    /**
     *
     */
    ESTRUCTURA(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),

    /**
     *
     */
    INMUNE(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),

    /**
     *
     */
    DEMONIO(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),

    /**
     *
     */
    HEROICA(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),

    /**
     *
     */
    GIGANTE(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),

    /**
     *
     */
    PEQUEÑA(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),

    /**
     *
     */
    TRANSPORTABLE(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),

    /**
     *
     */
    IMPENETRABLE(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),

    /**
     *
     */
    INTRASPASABLE(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE);
    
    private final QuadFunction<ActionResult> can;
    private final QuadFunction<ActionResult> on;
    private final QuadFunction<Boolean> canSer;
    private final QuadFunction<Boolean> onSer;

    private Tipo(QuadFunction<ActionResult> can, QuadFunction<ActionResult> on, QuadFunction<Boolean> canSer, QuadFunction<Boolean> onSer) {
        this.can = can;
        this.on = on;
        this.canSer = canSer;
        this.onSer = onSer;
    }
    
    public ActionResult canAccion(Accion accion, Tablero tablero, Pieza pieza, Point inicio, Point final_){
        return can.apply(accion, tablero, pieza, inicio, final_);
    }
    
    public ActionResult onAccion(Accion accion, Tablero tablero, Pieza pieza, Point inicio, Point final_){
        return on.apply(accion, tablero, pieza, inicio, final_);
    }
    
    public boolean canSerAccion(Accion accion, Tablero tablero, Pieza pieza, Point inicio, Point final_){
        return canSer.apply(accion, tablero, pieza, inicio, final_);
    }
    
    public boolean onSerAccion(Accion accion, Tablero tablero, Pieza pieza, Point inicio, Point final_){
        return onSer.apply(accion, tablero, pieza, inicio, final_);
    }
}
