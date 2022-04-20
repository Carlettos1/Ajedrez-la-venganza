package com.carlettos.game.board.property;

import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.core.function.QuadFunction;
import com.carlettos.game.board.manager.Board;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.core.Point;

/**
 * Tipos de piezas que hay. TODO: javadoc
 *
 * @author Carlos
 */
public enum PieceType {

    /**
     * 
     */
    BIOLOGIC(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),

    /**
     * 
     */
    STRUCTURE(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),

    /**
     * Las piezas inmunes no reciben daño de ciertos ataques
     */
    IMMUNE(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),

    /**
     * Las piezas heroicas
     */
    HEROIC(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),

    /**
     * Las Piezas transportables pueden ser utilizadas en la catapulta.
     */
    TRANSPORTABLE(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE),

    /**
     * Las Piezas transportables pueden ser utilizadas en la catapulta.
     */
    DEMONIC(QuadFunction.PASS, QuadFunction.PASS, QuadFunction.TRUE, QuadFunction.TRUE);
    
    private final QuadFunction<ActionResult> can;
    private final QuadFunction<ActionResult> on;
    private final QuadFunction<Boolean> canSer;
    private final QuadFunction<Boolean> onSer;

    private PieceType(QuadFunction<ActionResult> can, QuadFunction<ActionResult> on, QuadFunction<Boolean> canSer, QuadFunction<Boolean> onSer) {
        this.can = can;
        this.on = on;
        this.canSer = canSer;
        this.onSer = onSer;
    }
    
    public ActionResult canAccion(Action accion, Board tablero, Piece pieza, Point inicio, Point final_){
        return can.apply(accion, tablero, pieza, inicio, final_);
    }
    
    public ActionResult onAccion(Action accion, Board tablero, Piece pieza, Point inicio, Point final_){
        return on.apply(accion, tablero, pieza, inicio, final_);
    }
    
    public boolean canSerAccion(Action accion, Board tablero, Piece pieza, Point inicio, Point final_){
        return canSer.apply(accion, tablero, pieza, inicio, final_);
    }
    
    public boolean onSerAccion(Action accion, Board tablero, Piece pieza, Point inicio, Point final_){
        return onSer.apply(accion, tablero, pieza, inicio, final_);
    }
}
