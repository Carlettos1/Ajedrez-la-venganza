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
 * It's the board.
 *
 * @author Carlos
 */
public class Board extends AbstractBoard {
    protected final Clock clock;

    public Board(int columns, int rows, Clock clock) {
        super(columns, rows);
        this.clock = clock;
    }
    
    /**
     * Tries to do an {@code Action}. In case of an action that needs other 
     * point, use Point::toInfo.
     *
     * @param action action to do.
     * @param start start point.
     * @param info information about the action.
     * @return FAIL if it didn't do the action, PASS if the action has been 
     * done.
     */
    public ActionResult tryTo(Action action, Point start, Info info){
        Escaque s = getEscaque(start);
        if (!canPlay(s.getPiece())) {
            return ActionResult.FAIL;
        }
        Escaque f = null;
        if(action == Action.ATACAR || action == Action.MOVER || action == Action.COMER){
            try {
                f = getEscaque((Point) info.getValor());
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("Info no es Info<Point> para " + action + ", es: " + info.getClass());
            }
        }
        ActionResult can = getEscaque(start).getPiece().can(action, this, start, info);
        if(can.isPositive()) {
            switch (action) {
                case ATACAR -> {
                    f.removePiece();
                    f.getPiece().postAccion(action, this, start, info);
                }
                case MOVER, COMER -> {
                    f.setPiece(s.getPiece());
                    s.removePiece();
                    f.getPiece().postAccion(action, this, start, info);
                }
                case HABILIDAD -> {
                    s.getPiece().getAbility().use(this, s.getPiece(), start, info);
                }
            }
            movement();
        }
        return can;
    }

    public void movement() {
        this.clock.movement();
        if(getClock().getMovements() >= getClock().turnOf().getMaxMovements()){
            for (Escaque[] escaques : chessBoard) {
                for (Escaque escaque : escaques) {
                    escaque.getPiece().setIsMoved(false);
                }
            }
            getClock().endTurn();
        }
    }
    
    public boolean canPlay(Piece pieza){
        return getClock().turnOf().getColor().equals(pieza.getColor()) && getClock().canPlay(getClock().turnOf());
    }
    
    public boolean canPlay(Color color){
        return getClock().turnOf().getColor().equals(color) && getClock().canPlay(getClock().turnOf());
    }

    public Clock getClock() {
        return clock;
    }
    
    /**
     * It gets all the pieces of the given color.
     *
     * @param color color of the pieces to get.
     * @return a List with all the pieces of the given color.
     */
    public List<Piece> getPiecesOf(Color color){
        List<Piece> piezas = new ArrayList<>();
        for (Escaque[] escaques : chessBoard) {
            for (Escaque escaque : escaques) {
                if(escaque.isControladoPor(color)) {
                    piezas.add(escaque.getPiece());
                }
            }
        }
        return piezas;
    }
}
