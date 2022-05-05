package com.carlettos.game.gameplay.ability.classic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.carlettos.game.board.AbstractBoard;
import com.carlettos.game.board.Board;
import com.carlettos.game.board.Escaque;
import com.carlettos.game.gameplay.ability.Ability;
import com.carlettos.game.gameplay.ability.Info;
import com.carlettos.game.gameplay.piece.Piece;
import com.carlettos.game.gameplay.piece.classic.Rook;
import com.carlettos.game.util.Point;
import com.carlettos.game.util.enums.Action;
import com.carlettos.game.util.enums.ActionResult;
import com.carlettos.game.util.enums.Direction;
import com.carlettos.game.util.enums.Direction.Axis;

public class AbilityRook extends Ability {

    public AbilityRook() {
        super("rook", 10, 0);
    }

    @Override
    public ActionResult canUse(AbstractBoard board, Piece piece, Point start, Info info) {
        if (!this.commonCanUse(board, piece)) {
            return ActionResult.FAIL;
        }
        return ActionResult.fromBoolean(board instanceof Board && info.isType(Direction.class));
    }

    @Override
    public void use(AbstractBoard board, Piece piece, Point start, Info info) {
        var b = (Board) board;
        var dir = (Direction) info.getValue();
        List<Escaque> rooks = new ArrayList<>(getNearbyRookEscaques(board, piece, start));
        
        rooks.add(board.getEscaque(start));
        this.addAllRookEscaques(rooks, board, piece);
        this.orderEscaques(rooks, dir);
        this.throwRooks(rooks, b, dir);
        
        for (Escaque escaque : rooks) {
            this.commonUse(board, escaque.getPiece());
        }
    }
    
    protected void throwRooks(List<Escaque> rooks, Board board, Direction dir) {
        rooks.forEach(et -> throwTo(et, board, dir));
    }
    
    protected void throwTo(Escaque et, Board board, Direction dir) {
        var sign = dir.getSign();
        var isNegative = sign == -1;
        var isNS = dir.isAxis(Axis.NS);
        
        et.getPiece().setIsMoved(false); //por conveniencia
        
        if (isNS) {
            var to = isNegative ? 0 : board.columns - 1;
            while (to != et.getPos().y 
                    && !this.tryToGo(board, et, new Point(et.getPos().x, to))) {
                to += sign;
            }
        } else {
            var to = isNegative ? 0 : board.rows - 1;
            while (to != et.getPos().x 
                    && !this.tryToGo(board, et, new Point(to, et.getPos().y))) {
                to += sign;
            }
        }
    }
    
    protected boolean tryToGo(Board board, Escaque escaqueTorre, Point puntoFinal) {
        return board.tryTo(Action.TAKE, escaqueTorre.getPos(), puntoFinal.toInfo()).isPositive() 
                || board.tryTo(Action.MOVE, escaqueTorre.getPos(), puntoFinal.toInfo()).isPositive();
    }
    
    protected void addAllRookEscaques(List<Escaque> rooks, AbstractBoard board, Piece piece) {
        var rookFinded = true;
        while (rookFinded) {
            List<Escaque> tmp = new ArrayList<>();
            rookFinded = false;
            for (Escaque escaqueRook : rooks) {
                for (Escaque escaqueRookSide : getNearbyRookEscaques(board, piece, escaqueRook.getPos())) {
                    if (!(rooks.contains(escaqueRookSide) || tmp.contains(escaqueRookSide))) {
                        tmp.add(escaqueRookSide);
                        rookFinded = true;
                    }
                }
            }
            if (!tmp.isEmpty()) {
                rooks.addAll(tmp);
            }
        }
    }

    protected List<Escaque> getNearbyRookEscaques(AbstractBoard board, Piece piece, Point start) {
        return Arrays.<Escaque>asList(board.getNearbyEscaques(board.getEscaque(start)).stream().filter(escaque -> 
            escaque.getPiece() instanceof Rook && piece.getColor().equals(escaque.getPiece().getColor())
        ).toArray(Escaque[]::new));
    }

    protected void orderEscaques(List<Escaque> rooks, Direction direction) {
        switch (direction) {
            case N -> rooks.sort((e1, e2) -> Math.max(e1.getPos().y, e2.getPos().y));
            case S -> rooks.sort((e1, e2) -> Math.min(e1.getPos().y, e2.getPos().y));
            case E -> rooks.sort((e1, e2) -> Math.max(e1.getPos().x, e2.getPos().x));
            case W -> rooks.sort((e1, e2) -> Math.min(e1.getPos().x, e2.getPos().x));
        }
    }

    @Override
    public Direction[] getValues(AbstractBoard board, Point start) {
        return Direction.values();
    }
}
