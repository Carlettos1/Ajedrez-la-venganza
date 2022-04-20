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
        Escaque startEsq = getEscaque(start);
        if (!canPlay(startEsq.getPiece())) {
            return ActionResult.FAIL;
        }
        Escaque endEsq = null;
        if(action == Action.ATTACK || action == Action.MOVE || action == Action.TAKE){
            try {
                endEsq = getEscaque((Point) info.getValue());
            } catch (ClassCastException e) {
                throw new IllegalArgumentException("Info no es Info<Point> para " + action + ", es: " + info.getClass());
            }
        }
        ActionResult can = getEscaque(start).getPiece().can(action, this, start, info);
        if(can.isPositive()) {
            switch (action) {
                case ATTACK -> {
                    endEsq.removePiece();
                }
                case MOVE, TAKE -> {
                    endEsq.setPiece(startEsq.getPiece());
                    startEsq.removePiece();
                }
                case ABILITY -> {
                    startEsq.getPiece().getAbility().use(this, startEsq.getPiece(), start, info);
                }
            }
            endEsq.getPiece().postAction(action, this, start, info);
            movement();
        }
        return can;
    }

    /**
     * It notifies the clock that a movement had happen.
     */
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
    
    /**
     * Checks if the given piece can play, doesn't check if the piece has moved.
     *
     * @param piece piece to check.
     * @return true if the color of the piece can play, false other case.
     */
    public boolean canPlay(Piece piece){
        return canPlay(piece.getColor());
    }
    
    /**
     * Checks if the given color can play.
     *
     * @param color color to check.
     * @return true if can play, false other case.
     */
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
