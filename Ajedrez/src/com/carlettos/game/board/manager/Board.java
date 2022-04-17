package com.carlettos.game.board.manager;

import com.carlettos.game.board.manager.clock.Clock;
import com.carlettos.game.core.Action;
import com.carlettos.game.core.ActionResult;
import com.carlettos.game.board.Escaque;
import com.carlettos.game.core.Point;
import com.carlettos.game.board.piece.Piece;
import com.carlettos.game.board.property.Color;
import com.carlettos.game.board.property.ability.Info;
import java.util.ArrayList;
import java.util.List;

/**
 * Esta clase sólo controlará el trablero, cualquier otra funcionalidad que no
 * sea la de controlar a sus piezas, o proveer méthodos de utilidad sobre si
 * mismo, no es de su responsabilidad, por lo tanto, no tendrá conocimiento de
 * jugadores, relojes o barajas.
 *
 * @author Carlos
 *
 * @see Piece
 */
public class Board extends AbstractBoard {
    /**
     * Reloj vinculado a este tablero.
     */
    protected final Clock reloj;

    public Board(int columnas, int filas, Clock reloj) {
        super(columnas, filas);
        this.reloj = reloj;
    }

    /**
     * Come la pieza en la posición final_ con la pieza en la posición inicial.
     *
     * @param inicio posición de la pieza que come.
     * @param final_ posición de la pieza que es comida.
     * @return ActionResult diciendo el resultado.
     */
    public ActionResult intentarComerPieza(Point inicio, Point final_) {
        if(!canMoverPieza(getEscaque(inicio).getPiece())){
            return ActionResult.FAIL;
        }
        Escaque escaqueInicio = this.getEscaque(inicio);
        Escaque escaqueFinal = this.getEscaque(final_);

        boolean can = escaqueInicio.getPiece().can(Action.COMER, this, inicio, final_).isPositive();
        if (can) {
            escaqueFinal.setPiece(escaqueInicio.getPiece());
            escaqueInicio.quitarPieza();
            escaqueFinal.getPiece().postAccion(Action.COMER, this, inicio, final_);
            movement();
            return ActionResult.PASS;
        } else {
            return ActionResult.FAIL;
        }
    }

    /**
     * Mueve la pieza en la posición inicial a la posición final_.
     *
     * @param inicio posición de la pieza.
     * @param final_ posición al que la pieza se mueve.
     * @return ActionResult diciendo el resultado.
     */
    public ActionResult intentarMoverPieza(Point inicio, Point final_) {
        if(!canMoverPieza(getEscaque(inicio).getPiece())){
            return ActionResult.FAIL;
        }
        Escaque escaqueInicio = this.getEscaque(inicio);
        Escaque escaqueFinal = this.getEscaque(final_);

        boolean can = escaqueInicio.getPiece().can(Action.MOVER, this, inicio, final_).isPositive();
        if (can) {
            escaqueFinal.setPiece(escaqueInicio.getPiece());
            escaqueInicio.quitarPieza();
            escaqueFinal.getPiece().postAccion(Action.MOVER, this, inicio, final_);
            movement();
            return ActionResult.PASS;
        } else {
            return ActionResult.FAIL;
        }
    }

    /**
     * Ataca a la pieza en la posición final_.
     *
     * @param inicio posición de la pieza.
     * @param final_ posición al que la ataca.
     * @return ActionResult diciendo el resultado.
     */
    public ActionResult intentarAtacarPieza(Point inicio, Point final_) {
        if(!canMoverPieza(getEscaque(inicio).getPiece())){
            return ActionResult.FAIL;
        }
        Escaque escaqueInicio = this.getEscaque(inicio);
        Escaque escaqueFinal = this.getEscaque(final_);

        boolean can = escaqueInicio.getPiece().can(Action.ATACAR, this, inicio, final_).isPositive();
        if (can) {
            escaqueFinal.quitarPieza();
            escaqueFinal.getPiece().postAccion(Action.ATACAR, this, inicio, final_);
            movement();
            return ActionResult.PASS;
        } else {
            return ActionResult.FAIL;
        }
    }

    /**
     * Usa la habilidad de la pieza en el punto indicado, hacia el punto que
     * debe especificarse.
     *
     * @param inicio punto en el que la habilidad es lanzada.
     * @param final_ punto hacia donde la habilidad se lanza.
     * @param info String que contiene la información de la
     * habilidad, por ejemplo, hacia dónde se va a aplicar la habilidad de la
     * torre (NESW).
     *
     * @return un par que contiene un ActionResult y String a forma de ayuda
     * para dar más información.
     *
     * @see ActionResult
     */
    public ActionResult intentarHabilidadPieza(Point inicio, Info info) {
        if(!canMoverPieza(getEscaque(inicio).getPiece())){
            return ActionResult.FAIL;
        }
        Escaque escaque = getEscaque(inicio);
        ActionResult ar = escaque.getPiece().getHabilidad().canUse(this, escaque.getPiece(), inicio, info);
        if (ar.isPositive()) {
            escaque.getPiece().getHabilidad().use(this, escaque.getPiece(), inicio, info);
            movement();
        }
        return ar;
    }

    public void movement() {
        this.reloj.movement();
        if(getClock().getMovements() >= getClock().turnOf().getMaxMovements()){
            for (Escaque[] escaques : tableroAjedrez) {
                for (Escaque escaque : escaques) {
                    escaque.getPiece().setSeHaMovidoEsteTurno(false);
                }
            }
            getClock().endTurn();
        }
    }
    
    public boolean canMoverPieza(Piece pieza){
        return getClock().turnOf().getColor().equals(pieza.getColor()) && getClock().canPlay(getClock().turnOf());
    }
    
    public boolean canMoverColor(Color color){
        return getClock().turnOf().getColor().equals(color) && getClock().canPlay(getClock().turnOf());
    }

    public Clock getClock() {
        return reloj;
    }
    
    public List<Piece> getAllPiezasOfColor(Color color){
        List<Piece> piezas = new ArrayList<>();
        for (Escaque[] escaques : tableroAjedrez) {
            for (Escaque escaque : escaques) {
                if(escaque.isControladoPor(color)){
                    piezas.add(escaque.getPiece());
                }
            }
        }
        return piezas;
    }
}
